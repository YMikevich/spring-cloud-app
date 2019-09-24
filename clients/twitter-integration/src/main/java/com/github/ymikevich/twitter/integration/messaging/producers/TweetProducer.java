package com.github.ymikevich.twitter.integration.messaging.producers;

import com.github.ymikevich.twitter.integration.responses.TweetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Tweet producer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TweetProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Produce tweets to the queue using RabbitMQ.
     *
     * @param tweets the tweets
     */
    public void produce(final List<TweetResponse> tweets) {
        log.trace("Producing tweets");

        rabbitTemplate.convertAndSend("tweets", tweets);
    }
}
