package com.github.ymikevich.twitter.integration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.List;

/**
 * The type Twitter4j search engine.
 * TweetSearchEngine implementation.
 */
@Service
@RequiredArgsConstructor
public class Twitter4jSearchEngine implements TweetSearchEngine {

    private final Twitter twitter;

    @Override
    public List<Status> findTweetsByUsername(final String username) {
        //todo implementation
        return null;
    }
}
