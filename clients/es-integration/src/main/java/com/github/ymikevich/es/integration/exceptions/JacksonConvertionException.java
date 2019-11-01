package com.github.ymikevich.es.integration.exceptions;

import java.io.IOException;

public class JacksonConvertionException extends RuntimeException {

    private IOException ioException;

    public JacksonConvertionException(final IOException ioException) {
        this.ioException = ioException;
    }

    @Override
    public String getMessage() {
        return "Unexpected I/O exception while converting JSON response from "
                + "elastisearch to StatisticsResponse.class: " + ioException.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return ioException.getStackTrace();
    }
}
