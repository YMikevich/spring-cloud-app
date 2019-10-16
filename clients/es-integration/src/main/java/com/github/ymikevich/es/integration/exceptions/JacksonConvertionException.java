package com.github.ymikevich.es.integration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JacksonConvertionException extends  RuntimeException {

    private IOException ioException;

    public JacksonConvertionException(final IOException ioException) {
        this.ioException = ioException;
    }

    @Override
    public String getMessage() {
        return "Unexpected I/O exception while converting JSON response from "
                + "elastisearch to StatisticsResponse.class: " + ioException.getMessage();
    }
}
