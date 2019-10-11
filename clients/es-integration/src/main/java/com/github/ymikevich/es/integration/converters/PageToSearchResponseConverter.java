package com.github.ymikevich.es.integration.converters;

import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.responses.search.SearchResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class PageToSearchResponseConverter implements Converter<Page<Tweet>, SearchResponse<Tweet>> {

    @Override
    public SearchResponse<Tweet> convert(final Page<Tweet> source) {
        SearchResponse<Tweet> searchResponse = new SearchResponse<>();

        searchResponse.setTweets(source.getContent());
        searchResponse.getPagination().setPage(source.getPageable().getPageNumber());
        searchResponse.getPagination().setCount(source.getPageable().getPageSize());

        return searchResponse;
    }
}
