package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.api.model.Tweet;
import com.github.ymikevich.twitter.integration.feign.clients.UserClient;
import com.github.ymikevich.twitter.integration.messaging.producers.TweetProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * The type Default twitter service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTwitterService implements TwitterService {

    private final TweetSearchEngine tweetSearchEngine;
    private final TweetProducer tweetProducer;
    private final UserClient userClient;

    @Override
    public List<Tweet> findAndProduceTweetsByUsername(final String username) {
        var tweets = tweetSearchEngine.findRecentTweetsByUsername(username);
        log.trace("Sending tweets via rabbitMQ");
        tweetProducer.produce(tweets);

        return tweets;
    }

    @Override
    public void sync(final Long userId) {
        var userAccounts = ofNullable(userClient.getAccountsByUserId(userId));
        userAccounts.ifPresent(accountResponses -> accountResponses
                .stream()
                .forEach(accountResponse -> findAndProduceTweetsByUsername(accountResponse.getNickname()))
        );
    }
}
