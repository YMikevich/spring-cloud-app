package com.github.ymikevich.twitter.integration.services;

import com.github.ymikevich.twitter.integration.converters.StatusToTweetResponseConverter;
import com.github.ymikevich.twitter.integration.responses.TweetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Twitter4j search engine.
 * TweetSearchEngine implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Twitter4jSearchEngine implements TweetSearchEngine {

    private final Twitter twitter;
    private final StatusToTweetResponseConverter statusToTweetResponseConverter;

    @Override
    public List<TweetResponse> findRecentTweetsByUsername(final String username) {
        try {
            log.trace("Trying to get the list of tweets for user @" + username);

            return twitter.getUserTimeline(username).stream()
                    .map(statusToTweetResponseConverter::convert)
                    .collect(Collectors.toList());

        } catch (TwitterException te) {

            log.error(te.getMessage());
        }

        return List.of();
    }
}
