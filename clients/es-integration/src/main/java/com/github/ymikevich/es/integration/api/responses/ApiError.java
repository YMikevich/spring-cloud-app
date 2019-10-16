package com.github.ymikevich.es.integration.api.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final StackTraceElement[] stackTrace;

    public ApiError(final HttpStatus httpStatus, final String errorMessage, final StackTraceElement[] stackTrace) {
        this.errorCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
    }
}
