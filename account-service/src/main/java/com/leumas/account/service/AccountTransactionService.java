package com.leumas.account.service;

import com.leumas.account.kafka.event.TransactionPayload;
import com.leumas.account.kafka.producer.AccountEventsProducer;
import com.leumas.account.model.Account;
import com.leumas.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class AccountTransactionService {
    private final AccountRepository accountRepository;
    private final AccountEventsProducer accountEventsProducer;
    private final Set<UUID> processedTransactions = ConcurrentHashMap.newKeySet();

    private static final String DEPOSIT = "DEPOSIT";
    private static final String WITHDRAWAL = "WITHDRAWAL";
    private static final String TRANSFER = "TRANSFER";

    @Transactional
    public void processTransaction(TransactionPayload payload) {

        if(!processedTransactions.add(payload.transactionId())) return;

        try{
            switch (payload.type()){
                case DEPOSIT -> handleDeposit(payload);
                case WITHDRAWAL -> handleWithdrawal(payload);
                case TRANSFER -> handleTransfer(payload);
                default -> throw new IllegalArgumentException("Invalid transaction type: " + payload.type());
            }
            //melhorar depois
            //se chegou aqui foi sucessso
            accountEventsProducer.sendTransactionCompleted(payload);
        }catch (Exception e){
            accountEventsProducer.sendTransactionFailed(payload, e.getMessage());
        }
    }

    private void handleDeposit(TransactionPayload payload) {
        Account account = findAccountById(payload.accountId());
        account.setBalance(account.getBalance().add(payload.amount()));
        accountRepository.save(account);
    }

    private void handleWithdrawal(TransactionPayload payload) {
        Account account = findAccountById(payload.accountId());
        validateSufficientFunds(account, payload.amount());
        account.setBalance(account.getBalance().subtract(payload.amount()));
        accountRepository.save(account);
    }

    private void handleTransfer(TransactionPayload payload) {
        if(payload.relatedAccountId() == null){
            throw new IllegalArgumentException("Transfer must specify related account id");
        }
        Account origin = findAccountById(payload.accountId());
        Account destination = findAccountById(payload.relatedAccountId());
        validateSufficientFunds(origin, payload.amount());

        origin.setBalance(origin.getBalance().subtract(payload.amount()));
        accountRepository.save(origin);

        destination.setBalance(destination.getBalance().add(payload.amount()));
        accountRepository.save(destination);
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(()-> new IllegalArgumentException("Account not found: " + accountId));
    }

    private void validateSufficientFunds(Account account, BigDecimal amount) {
        if(account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for account: " + account.getId());
        }
    }
}
