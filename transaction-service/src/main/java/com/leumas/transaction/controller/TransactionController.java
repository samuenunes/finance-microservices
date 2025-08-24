package com.leumas.transaction.controller;

import com.leumas.transaction.controller.request.CreateTransactionRequest;
import com.leumas.transaction.mapper.TransactionMapper;
import com.leumas.transaction.model.Transaction;
import com.leumas.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody CreateTransactionRequest req,
                                              @RequestHeader(value="Idempotency-Key", required=false) String idem) {
        req.setIdempotencyKey(idem==null ? req.getIdempotencyKey() : idem);
        Transaction tx = service.createTransaction(req);
        return ResponseEntity.accepted().body(TransactionMapper.toResponse(tx));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable UUID id) {
        return service.findTransactionById(id)
                .map(tx -> ok(TransactionMapper.toResponse(tx)))
                .orElse(notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> findByAccount(@PathVariable Long accountId) {
        return service.findTransactionsByAccount(accountId);
    }
}

