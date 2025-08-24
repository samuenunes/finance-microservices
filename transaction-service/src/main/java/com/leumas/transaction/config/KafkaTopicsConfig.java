package com.leumas.transaction.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaTopicsConfig {
    @Bean
    public NewTopic transactionEventsTopic() {
        return new NewTopic("transaction-events", 3, (short) 1);
    }
}
