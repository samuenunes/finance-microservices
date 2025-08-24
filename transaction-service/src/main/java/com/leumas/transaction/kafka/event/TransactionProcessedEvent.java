package com.leumas.transaction.kafka.event;

import com.leumas.transaction.model.TransactionStatus;

import java.util.UUID;

public record TransactionProcessedEvent(
        UUID transactionId,
        Long accountId,
        String type,
        TransactionStatus status,
        String reason
) {}
