package com.leumas.account.service;

import com.leumas.account.client.CustomerClient;
import com.leumas.account.client.dto.CustomerResponse;
import com.leumas.account.model.Account;
import com.leumas.account.repository.AccountRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;
    private final AccountNumberService accountNumberService;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Account save(Account account) {
        findCustomer(account.getCustomer());
        account.setAccount(accountNumberService.generateAccountNumber());
        return accountRepository.save(account);
    }

    public Optional<Account> update(Account account, Long id) {
        return accountRepository.findById(id)
                .map(old -> {
                   account.setId(old.getId());
                   account.setAccount(old.getAccount()); //para nao atualizar enquanto crio os dtos na refatoracao
                   account.setCustomer(old.getCustomer());
                   return accountRepository.save(account);
                });
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    private Optional<CustomerResponse> findCustomer(String id) {
        try{
            return Optional.of(customerClient.getCustomer(id));
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("Customer not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
