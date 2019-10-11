package com.github.ymikevich.es.integration.api.requests.search;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class Pagination {

    @PositiveOrZero
    @NotNull
    private Integer page;
    @PositiveOrZero
    @NotNull
    private Integer count;
}
