package com.leumas.transaction.controller.request;

import com.leumas.transaction.model.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CreateTransactionRequest {
    private Long accountId;
    private Long relatedAccountId;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String idempotencyKey;
    private UUID correlationId;
}

