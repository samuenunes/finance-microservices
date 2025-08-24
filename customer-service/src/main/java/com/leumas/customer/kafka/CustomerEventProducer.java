package com.leumas.customer.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventProducer {

    private final KafkaTemplate<String, String> template;
    private final String topic = "customer-events";

    public void sendCustomerCreatedEvent(String customerId) {
        template.send(topic, "Created customer: "+customerId);
    }

    public void sendCustomerUpdatedEvent(String customerId) {
        template.send(topic, "Updated customer: "+customerId);
    }
}
