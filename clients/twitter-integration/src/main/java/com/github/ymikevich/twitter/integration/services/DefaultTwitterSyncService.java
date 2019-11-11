package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.api.model.responses.AccountResponse;
import com.github.ymikevich.twitter.integration.feign.clients.UserClient;
import com.github.ymikevich.twitter.integration.messaging.producers.TweetProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Default twitter service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTwitterSyncService implements TwitterSyncService {

    private final UserClient userClient;
    private final TweetSearchEngine tweetSearchEngine;
    private final TweetProducer tweetProducer;

    @Override
    public void sync(final Long userId) {
        var userAccounts = userClient.getAccountsByUserId(userId);
        userAccounts.stream()
                .map(AccountResponse::getNickname)
                .forEach(this::produceTweetsByUsername);
    }

    private void produceTweetsByUsername(final String username) {
        tweetProducer.produce(tweetSearchEngine.findRecentTweetsByUsername(username));
    }
}
