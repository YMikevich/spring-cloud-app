package com.github.ymikevich.twitter.integration.controllers;

import com.github.ymikevich.twitter.integration.messaging.producers.TweetProducer;
import com.github.ymikevich.twitter.integration.responses.TweetResponse;
import com.github.ymikevich.twitter.integration.services.TweetSearchEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Twitter integration controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TwitterIntegrationController {

    private final TweetSearchEngine tweetSearchEngine;
    private final TweetProducer tweetProducer;


    /**
     * Gets tweets by username from service.
     *
     * @param username the twitter username
     * @return the tweets by username
     */
    @GetMapping("/search/{username}")
    public List<TweetResponse> getTweetsByUsername(@PathVariable final String username) {
        log.trace("Controller received request to search for @" + username + " tweets");

        List<TweetResponse> tweets = tweetSearchEngine.findTweetsByUsername(username);
        log.trace("Sending tweets via rabbitMQ");
        tweetProducer.produce(tweets);

        return tweets;
    }
}
