package com.github.ymikevich.es.integration.api.responses.search;

import com.github.ymikevich.es.integration.api.requests.search.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchResponse<T> {

    private List<T> responseEntities = new ArrayList<>();
    private Pagination pagination = new Pagination();
}
