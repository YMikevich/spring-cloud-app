package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.search.SearchRequest;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTweetService implements TweetService {

    private final TweetRepository tweetRepository;
    private final Converter<SearchRequest, Pageable> searchRequestToPageableConverter;

    @Override
    public void persistTweets(final List<Tweet> tweets) {
        tweetRepository.saveAll(tweets);
    }

    @Override
    public Page<Tweet> searchForTweets(final SearchRequest searchRequest) {
        return tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                searchRequestToPageableConverter.convert(searchRequest));
    }
}
