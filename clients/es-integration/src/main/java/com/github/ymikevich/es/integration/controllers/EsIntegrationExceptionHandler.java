package com.github.ymikevich.es.integration.controllers;

import com.github.ymikevich.es.integration.api.responses.ApiError;
import com.github.ymikevich.es.integration.exceptions.InvalidStatisticsRequestException;
import com.github.ymikevich.es.integration.exceptions.JacksonConvertionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class EsIntegrationExceptionHandler {

    @ExceptionHandler(InvalidStatisticsRequestException.class)
    public ResponseEntity<ApiError> handleInvalidStatisticsException(final InvalidStatisticsRequestException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .errorMessage(exception.getMessage())
                .stackTrace(exception.getStackTrace())
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JacksonConvertionException.class)
    public ResponseEntity<ApiError> handleJacksonConversionException(final JacksonConvertionException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .errorMessage(exception.getMessage())
                .stackTrace(exception.getStackTrace())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
