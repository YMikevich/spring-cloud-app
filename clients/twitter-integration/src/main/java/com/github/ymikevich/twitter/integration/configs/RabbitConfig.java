package com.github.ymikevich.twitter.integration.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Rabbit config.
 */
@Configuration
public class RabbitConfig {

    private final String queueName;

    /**
     * Instantiates a new Rabbit config.
     *
     * @param queueName the name of the queue in rabbitMQ.
     */
    public RabbitConfig(@Value("${rabbit.queue.name}") final String queueName) {
        this.queueName = queueName;
    }

    /**
     * Queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }
}
