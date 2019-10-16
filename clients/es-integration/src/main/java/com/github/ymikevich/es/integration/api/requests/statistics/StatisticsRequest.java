package com.github.ymikevich.es.integration.api.requests.statistics;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class StatisticsRequest {

    @NotBlank
    private String username;
    @NotNull
    @PositiveOrZero
    private Integer sinceDays;
}
