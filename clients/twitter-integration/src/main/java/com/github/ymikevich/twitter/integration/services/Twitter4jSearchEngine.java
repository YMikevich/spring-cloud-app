package com.github.ymikevich.twitter.integration.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

/**
 * The type Twitter4j search engine.
 * TweetSearchEngine implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Twitter4jSearchEngine implements TweetSearchEngine {

    private final Twitter twitter;

    @Override
    public List<Status> findTweetsByUsername(final String username) {
        try {
            log.info("Trying to get the list of tweets for user @" + username);

            return twitter.getUserTimeline(username);

        } catch (TwitterException te) {

            log.error(te.getMessage());
        }

        return List.of();
    }
}
