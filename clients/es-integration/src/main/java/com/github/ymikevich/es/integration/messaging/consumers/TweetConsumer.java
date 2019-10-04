package com.github.ymikevich.es.integration.messaging.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.services.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TweetConsumer {

    private final ObjectMapper objectMapper;
    private final TweetService tweetService;

    @RabbitListener(queues = {"tweets"})
    public void receiveTweets(final String tweets) {
        try {
            tweetService.persistTweets(objectMapper.readValue(tweets, new TypeReference<List<Tweet>>() {
            }));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
