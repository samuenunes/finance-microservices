package com.leumas.account.kafka.producer;

import com.leumas.account.kafka.event.TransactionPayload;
import com.leumas.account.kafka.event.TransactionProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventsProducer {
    private final KafkaTemplate<String, TransactionProcessedEvent> processedEventKafkaTemplate;
    private static final String ACCOUNT_EVENTS_TOPIC = "account-events";

    public void sendTransactionCompleted(TransactionPayload payload) {
        log.info("send transaction completed: {}", payload.transactionId());
        processedEventKafkaTemplate.send(ACCOUNT_EVENTS_TOPIC, payload.transactionId().toString(), TransactionProcessedEvent.success(payload));
    }

    public void sendTransactionFailed(TransactionPayload payload, String errorMessage) {
        log.info("send transaction failed: {}", payload.transactionId());
        processedEventKafkaTemplate.send(
                ACCOUNT_EVENTS_TOPIC,
                payload.transactionId().toString(),
                TransactionProcessedEvent.failure(payload, errorMessage)
        );
    }
}
