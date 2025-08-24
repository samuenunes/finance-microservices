package com.leumas.transaction.service;

import com.leumas.transaction.controller.request.CreateTransactionRequest;
import com.leumas.transaction.exception.IdempotencyException;
import com.leumas.transaction.kafka.event.TransactionProcessedEvent;
import com.leumas.transaction.kafka.producer.TransactionEventProducer;
import com.leumas.transaction.model.Transaction;
import com.leumas.transaction.model.TransactionStatus;
import com.leumas.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionEventProducer producer;

    @Transactional
    public Transaction createTransaction(CreateTransactionRequest req) {
        if (req.getIdempotencyKey() != null) {
            repository.findByIdempotencyKey(req.getIdempotencyKey()).ifPresent(existing -> {
                throw new IdempotencyException(existing);
            });
        }
        Transaction tx = Transaction.builder()
                .accountId(req.getAccountId())
                .relatedAccountId(req.getRelatedAccountId())
                .type(req.getType())
                .amount(req.getAmount())
                .currency(req.getCurrency() == null ? "BRL" : req.getCurrency())
                .status(TransactionStatus.PENDING)
                .idempotencyKey(req.getIdempotencyKey())
                .correlationId(req.getCorrelationId() == null ? UUID.randomUUID() : req.getCorrelationId())
                .build();

        Transaction saved = repository.save(tx);
        producer.publishCreated(saved);
        return saved;
    }

    public Optional<Transaction> findTransactionById(UUID id) {
        return repository.findById(id);
    }

    public List<Transaction> findTransactionsByAccount(Long accountId) {
        return repository.findAllByAccountId(accountId);
    }

    public void handleTransactionProcessed(TransactionProcessedEvent event) {
        //por enquanto so atualiza o status da transaacao
        repository.findById(event.transactionId()).ifPresent(tx -> {
            tx.setStatus(event.status());
            tx.setReason(event.reason());
            repository.save(tx);
        });
    }
}

