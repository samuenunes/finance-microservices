package com.leumas.account.kafka.event;

import com.leumas.account.kafka.event.enums.TransactionStatus;

import java.util.UUID;

public record TransactionProcessedEvent(
        UUID transactionId,
        Long accountId,
        String type,
        String status,
        String reason
) {
    public static TransactionProcessedEvent success (TransactionPayload origin) {
        return new TransactionProcessedEvent(
                origin.transactionId(),
                origin.accountId(),
                origin.type(),
                TransactionStatus.COMPLETED.name(),
                null
        );
    }

    public static TransactionProcessedEvent failure (TransactionPayload origin, String reason) {
        return new TransactionProcessedEvent(
                origin.transactionId(),
                origin.accountId(),
                origin.type(),
                TransactionStatus.FAILED.name(),
                reason
        );
    }
}
