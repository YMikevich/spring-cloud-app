package com.github.ymikevich.es.integration.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String routingKey;
    private final String queueName;
    private final String exchangeName;

    public RabbitConfig(@Value("${rabbit.routing_key.name}") final String routingKey,
                        @Value("${rabbit.queue.name}") final String queueName,
                        @Value("${rabbit.exchange.name}") final String exchangeName) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.exchangeName = exchangeName;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Exchange fanoutExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Binding binding(final Queue queue, final Exchange fanoutExchange) {
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange)
                .with(routingKey)
                .noargs();
    }
}
