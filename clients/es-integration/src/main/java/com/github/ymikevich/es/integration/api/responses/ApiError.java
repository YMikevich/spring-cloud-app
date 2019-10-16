package com.github.ymikevich.es.integration.api.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiError {

    private String httpStatus;
    private String errorMessage;
    private StackTraceElement[] stackTrace;
}
