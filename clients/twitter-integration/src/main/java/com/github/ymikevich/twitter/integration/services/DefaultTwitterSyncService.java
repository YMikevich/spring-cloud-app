package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.api.model.responses.AccountResponse;
import com.github.ymikevich.twitter.integration.feign.clients.UserClient;
import com.github.ymikevich.twitter.integration.messaging.producers.TweetProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        produceTweetsByUsername(userAccounts.stream()
                .map(AccountResponse::getNickname)
                .collect(Collectors.toList()));
    }

    private void produceTweetsByUsername(final List<String> usernames) {
        usernames.forEach(username -> tweetProducer.produce(tweetSearchEngine.findRecentTweetsByUsername(username)));
    }
}
