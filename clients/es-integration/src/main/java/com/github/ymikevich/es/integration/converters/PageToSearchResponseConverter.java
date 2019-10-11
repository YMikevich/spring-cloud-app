package com.github.ymikevich.es.integration.converters;

import com.github.ymikevich.es.integration.api.responses.search.SearchResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToSearchResponseConverter<T> implements Converter<Page<T>, SearchResponse<T>> {

    @Override
    public SearchResponse<T> convert(final Page<T> source) {
        var searchResponse = new SearchResponse<T>();

        searchResponse.setResponseEntities(source.getContent());
        searchResponse.getPagination().setPage(source.getPageable().getPageNumber());
        searchResponse.getPagination().setCount(source.getPageable().getPageSize());

        return searchResponse;
    }
}
