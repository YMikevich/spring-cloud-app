package com.github.ymikevich.twitter.integration.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Rabbit config.
 */
@Configuration
public class RabbitConfig {
    /**
     * Queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue queue() {
        return new Queue("tweets");
    }
}
