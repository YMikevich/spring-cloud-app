package com.github.ymikevich.es.integration.api.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiError {

    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final StackTraceElement[] stackTrace;
}
