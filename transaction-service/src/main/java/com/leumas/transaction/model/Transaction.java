package com.leumas.transaction.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "related_account_id")
    private Long relatedAccountId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TransactionType type;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(length = 3, nullable = false)
    private String currency = "BRL";

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TransactionStatus status;

    @Column(columnDefinition = "text")
    private String reason;

    @Column(length = 128, unique = true)
    private String idempotencyKey;

    private UUID correlationId;

    //@Column(columnDefinition = "jsonb")
    //private Map<String, Object> metadata; //por enquanto

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
