package com.leumas.finance.admin.kafka;

import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public static final String TOPIC = "transaction-events";

    public void publishCreated(Transaction tx) {
        Map<String, Object> payload = Map.of(
                "eventType","TRANSACTION_CREATED",
                "timestamp", Instant.now().toString(),
                "transaction", Map.of(
                        "transactionId", tx.getId().toString(),
                        "accountId", tx.getAccountId(),
                        "relatedAccountId", tx.getRelatedAccountId(),
                        "type", tx.getType().name(),
                        "amount", tx.getAmount(),
                        "currency", tx.getCurrency(),
                        "status", tx.getStatus().name(),
                        "idempotencyKey", tx.getIdempotencyKey(),
                        "correlationId", tx.getCorrelationId()
                )
        );
        kafkaTemplate.send(TOPIC, tx.getId().toString(), payload);
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
