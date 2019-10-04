package com.github.ymikevich.twitter.integration.messaging.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.twitter.integration.api.model.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class TweetProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;
    private final ObjectMapper objectMapper;

    public TweetProducer(@Value("${rabbit.routing_key.name}") final String routingKey,
                         final RabbitTemplate rabbitTemplate,
                         final ObjectMapper objectMapper) {
        this.routingKey = routingKey;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void produce(final List<Tweet> tweets) {
        log.trace("Producing tweets");

        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(tweets));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
