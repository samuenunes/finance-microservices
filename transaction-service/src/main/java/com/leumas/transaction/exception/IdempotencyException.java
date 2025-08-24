package com.leumas.transaction.exception;

import com.leumas.transaction.model.Transaction;

public class IdempotencyException extends RuntimeException {
    public IdempotencyException(Transaction existing) {
        super("Erro idenpotency tal ...");
    }
}
