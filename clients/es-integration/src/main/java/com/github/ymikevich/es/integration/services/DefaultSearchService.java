package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.requests.SearchRequest;
import com.github.ymikevich.es.integration.api.requests.Sorting;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DefaultSearchService implements SearchService {
    private final TweetRepository tweetRepository;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_COUNT = 10;

    @Override
    public List<Tweet> searchForTweets(final SearchRequest searchRequest) {
        Optional<Sorting> optionalSorting = Optional.ofNullable(searchRequest.getSorting());

        AtomicReference<List<Tweet>> tweets = new AtomicReference<>(List.of());

        optionalSorting.ifPresentOrElse(sorting ->
                        tweets.set(tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                                createPageableWithSortingAndPagination(searchRequest))),
                () -> tweets.set(tweetRepository.findAllByTextLike(searchRequest.getSearchString(),
                        createPageableWithoutSorting(searchRequest))));

        return tweets.get();
    }

    private Pageable createPageableWithoutSorting(final SearchRequest searchRequest) {
        var pagination = searchRequest.getPagination();
        return PageRequest.of(pagination == null ? DEFAULT_PAGE_NUMBER : pagination.getPage(),
                pagination == null ? DEFAULT_PAGE_COUNT : pagination.getCount());
    }

    private Pageable createPageableWithSortingAndPagination(final SearchRequest searchRequest) {
        var pagination = searchRequest.getPagination();
        return PageRequest.of(pagination == null ? DEFAULT_PAGE_NUMBER : pagination.getPage(),
                pagination == null ? DEFAULT_PAGE_COUNT : pagination.getCount(),
                Sort.Direction.fromString(searchRequest.getSorting().getDirection()),
                searchRequest.getSorting().getFieldName());
    }
}
