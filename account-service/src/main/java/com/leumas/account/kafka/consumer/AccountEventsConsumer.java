package com.leumas.account.kafka.consumer;

import com.leumas.account.kafka.event.TransactionCreatedEvent;
import com.leumas.account.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventsConsumer {
    private final AccountTransactionService accountService;

    @KafkaListener(topics = "transaction-events", groupId = "account-service-group")
    public void consume(TransactionCreatedEvent transactionEvent) {
        //try-catch depois para devolvvedor erro no evento posteriomente
        log.info("Received transaction: {}", transactionEvent);
        accountService.processTransaction(transactionEvent.transaction());
    }

}
