package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

    public void persistTweets(final List<Tweet> tweets) {
        tweetRepository.saveAll(tweets);
    }
}
