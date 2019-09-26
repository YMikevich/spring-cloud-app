package com.github.ymikevich.twitter.integration.configs;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Rabbit config.
 */
@Configuration
public class RabbitConfig {

    private final String exchangeName;

    public RabbitConfig(@Value("${rabbit.exchange.name}") final String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Bean
    Exchange fanoutExchange() {
        return new FanoutExchange(exchangeName);
    }
}
