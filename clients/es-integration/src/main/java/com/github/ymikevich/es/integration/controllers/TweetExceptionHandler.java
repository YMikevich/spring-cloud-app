package com.github.ymikevich.es.integration.controllers;

import com.github.ymikevich.es.integration.api.responses.ApiError;
import com.github.ymikevich.es.integration.exceptions.InvalidStatisticsRequestException;
import com.github.ymikevich.es.integration.exceptions.JacksonConvertionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class TweetExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidStatisticsRequestException.class)
    public ResponseEntity<ApiError> handleInvalidStatisticsException(final InvalidStatisticsRequestException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ApiError(400, HttpStatus.BAD_REQUEST, exception.getMessage(),
                exception.getStackTrace()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JacksonConvertionException.class)
    public ResponseEntity<ApiError> handleJacksonConversionException(final JacksonConvertionException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ApiError(500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                exception.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
