package com.github.ymikevich.twitter.integration.messaging.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.twitter.integration.responses.TweetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TweetProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    /**
     * Instantiates a new Tweet producer.
     *
     * @param queueName      the queue name in rabbitMQ
     * @param rabbitTemplate the rabbit template
     */
    public TweetProducer(@Value("${rabbit.queue.name}") final String queueName, final RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(final List<TweetResponse> tweets) {
        log.trace("Producing tweets");

        try {
            rabbitTemplate.convertAndSend(tweetsExchange.getName(), routingKey, objectMapper.writeValueAsString(tweets));
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }
}
