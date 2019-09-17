package com.github.ymikevich.twitter.integration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.List;

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
