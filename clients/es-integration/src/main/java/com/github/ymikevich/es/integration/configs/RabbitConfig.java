package com.github.ymikevich.es.integration.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String queueName;

    public RabbitConfig(@Value("${rabbit.queue.name}") final String queueName) {
        this.queueName = queueName;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
}
