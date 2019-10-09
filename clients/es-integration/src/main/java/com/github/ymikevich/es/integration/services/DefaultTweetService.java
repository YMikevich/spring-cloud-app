package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.SearchRequest;
import com.github.ymikevich.es.integration.converters.SearchRequestToPageable;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTweetService implements TweetService {

    private final TweetRepository tweetRepository;
    private final SearchRequestToPageable searchRequestToPageable;

    @Override
    public void persistTweets(final List<Tweet> tweets) {
        tweetRepository.saveAll(tweets);
    }

    @Override
    public List<Tweet> searchForTweets(final SearchRequest searchRequest) {
        return tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                searchRequestToPageable.createPageRequest(searchRequest));
    }
}
