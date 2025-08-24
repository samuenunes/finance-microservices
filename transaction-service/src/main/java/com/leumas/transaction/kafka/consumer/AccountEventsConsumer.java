package com.leumas.transaction.kafka.consumer;

import com.leumas.transaction.kafka.event.TransactionProcessedEvent;
import com.leumas.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventsConsumer {
    private static final Logger log = LoggerFactory.getLogger(AccountEventsConsumer.class);
    private final TransactionService transactionService;

    @KafkaListener(topics = "account-events", groupId = "transaction-service-group")
    public void consume(TransactionProcessedEvent event) {
        log.info("Received transaction processed event: {}", event.transactionId());
        transactionService.handleTransactionProcessed(event);
    }
}
