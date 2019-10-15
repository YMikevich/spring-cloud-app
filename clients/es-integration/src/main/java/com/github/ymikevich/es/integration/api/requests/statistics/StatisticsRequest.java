package com.github.ymikevich.es.integration.api.requests.statistics;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class StatisticsRequest {

    @NotBlank
    private String username;
    @Positive
    private Integer sinceDays;
}
