package com.leumas.account.kafka.event;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionPayload(
        UUID transactionId,
        Long accountId,
        Long relatedAccountId,
        String type,
        BigDecimal amount,
        String currency,
        String status,
        String reason,
        String idempotencyKey,
        UUID correlationId
) {}
