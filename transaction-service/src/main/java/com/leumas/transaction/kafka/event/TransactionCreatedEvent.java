package com.leumas.transaction.kafka.event;

import com.leumas.transaction.kafka.event.enums.EventType;
import com.leumas.transaction.model.Transaction;

import java.time.Instant;

public record TransactionCreatedEvent(
        String eventType,
        Instant timestamp,
        TransactionPayload transaction
) {
    public static TransactionCreatedEvent from(Transaction transaction) {
        return new TransactionCreatedEvent(
                EventType.TRANSACTION_CREATED.name(),
                Instant.now(),
                new TransactionPayload(
                        transaction.getId(),
                        transaction.getAccountId(),
                        transaction.getRelatedAccountId(),
                        transaction.getType().name(),
                        transaction.getAmount(),
                        transaction.getCurrency(),
                        transaction.getStatus().name(),
                        transaction.getIdempotencyKey(),
                        transaction.getCorrelationId()
                )
        );
    }
}
