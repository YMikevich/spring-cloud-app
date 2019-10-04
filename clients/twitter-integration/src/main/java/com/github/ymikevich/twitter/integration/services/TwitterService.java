package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.api.model.Tweet;

import java.util.List;

/**
 * The interface Twitter service.
 */
public interface TwitterService {
    /**
     * Find and produce tweets by username.
     *
     * @param username the username
     * @return the list
     */
    List<Tweet> findAndProduceTweetsByUsername(String username);
}
