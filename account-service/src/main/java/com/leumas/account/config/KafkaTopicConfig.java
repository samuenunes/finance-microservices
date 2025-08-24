package com.leumas.account.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaTopicConfig {

    @Bean
    public NewTopic accountTopic() {
        return new NewTopic("account-events", 3, (short) 1);
    }
}
