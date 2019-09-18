package com.github.ymikevich.twitter.integration.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The type Date to LocalDateTime converter.
 */
@Component
@Slf4j
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    /**
     * Convert Date to LocalDateTime.
     *
     * @param dateToConvert the date to convert
     * @return the LocalDateTime
     */
    public LocalDateTime convert(final Date dateToConvert) {
        log.info("Converting Date to LocalDateTime");

        if (dateToConvert == null) {
            log.warn("Date equals null while converting");
            return  null;
        }

        log.info("Converted successfully");
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
