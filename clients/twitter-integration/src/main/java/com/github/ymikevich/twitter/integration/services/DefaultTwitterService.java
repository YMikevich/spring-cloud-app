package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.messaging.producers.TweetProducer;
import com.github.ymikevich.twitter.integration.api.model.Tweet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Default twitter service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTwitterService implements TwitterService {

    private final TweetSearchEngine tweetSearchEngine;
    private final TweetProducer tweetProducer;

    @Override
    public List<Tweet> findAndProduceTweetsByUsername(final String username) {

        var tweets = tweetSearchEngine.findRecentTweetsByUsername(username);
        log.trace("Sending tweets via rabbitMQ");
        tweetProducer.produce(tweets);

        return tweets;
    }
}
