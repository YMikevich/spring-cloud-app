package com.github.ymikevich.es.integration.domain;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TweetConsumer {
    @RabbitListener(queues = {"tweets"})
    public void receiveTweets(final String tweets) {

        //todo implementation
    }
}
