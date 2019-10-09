package com.github.ymikevich.es.integration.converters;

import com.github.ymikevich.es.integration.api.requests.Pagination;
import com.github.ymikevich.es.integration.api.requests.SearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class SearchRequestToPageable {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_COUNT = 10;

    public Pageable createPageRequest(final SearchRequest searchRequest) {
        var pagination = ofNullable(searchRequest.getPagination());
        var sort = ofNullable(searchRequest.getSorting())
                .map(it -> Sort.by(Sort.Direction.fromString(it.getDirection()), it.getFieldName()))
                .orElseGet(Sort::unsorted);

        return PageRequest.of(
                pagination.map(Pagination::getPage).orElse(DEFAULT_PAGE),
                pagination.map(Pagination::getCount).orElse(DEFAULT_COUNT),
                sort
        );
    }
}
