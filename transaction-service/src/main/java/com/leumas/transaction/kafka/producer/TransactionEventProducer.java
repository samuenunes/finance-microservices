package com.leumas.transaction.kafka.producer;

import com.leumas.transaction.kafka.event.TransactionCreatedEvent;
import com.leumas.transaction.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor

public class TransactionEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public static final String TOPIC = "transaction-events";

    public void publishCreated(Transaction tx) {
        log.info("Publishing created transaction {}", tx.getId());
        kafkaTemplate.send(TOPIC, tx.getId().toString(), TransactionCreatedEvent.from(tx));
    }

    public void publishCompleted(Transaction tx, BigDecimal balanceAfter) {
        Map<String,Object> payload = Map.of(
                "eventType","TRANSACTION_COMPLETED",
                "timestamp", Instant.now().toString(),
                "transactionId", tx.getId().toString(),
                "accountId", tx.getAccountId(),
                "amount", tx.getAmount(),
                "currency", tx.getCurrency(),
                "balanceAfter", balanceAfter
        );
        kafkaTemplate.send(TOPIC, tx.getId().toString(), payload);
    }

    public void publishFailed(Transaction tx, String reason) {
        Map<String,Object> payload = Map.of(
                "eventType","TRANSACTION_FAILED",
                "timestamp", Instant.now().toString(),
                "transactionId", tx.getId().toString(),
                "accountId", tx.getAccountId(),
                "reason", reason
        );
        kafkaTemplate.send(TOPIC, tx.getId().toString(), payload);
    }
}

