package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.api.model.Tweet;

import java.util.List;

/**
 * The interface Tweet search engine.
 */
public interface TweetSearchEngine {

    /**
     * Find tweets by username list.
     *
     * @param username the username
     * @return the list of tweets
     */
    List<Tweet> findRecentTweetsByUsername(String username);
}
