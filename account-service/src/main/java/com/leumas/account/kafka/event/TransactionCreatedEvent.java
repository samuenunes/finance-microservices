package com.leumas.account.kafka.event;

import java.time.Instant;

public record TransactionCreatedEvent(
        String eventType,
        Instant timestamp,
        TransactionPayload transaction
){}