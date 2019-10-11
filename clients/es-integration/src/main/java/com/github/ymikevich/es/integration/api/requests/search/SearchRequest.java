package com.github.ymikevich.es.integration.api.requests.search;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SearchRequest {
    @NotBlank
    private String searchString;
    @Valid
    private Pagination pagination;
    @Valid
    private Sorting sorting;
}
