package com.github.ymikevich.es.integration.configs.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.elasticsearch.core.EntityMapper;

import java.io.IOException;

public class CustomEntityMapper implements EntityMapper {
    private final ObjectMapper objectMapper;

    public CustomEntityMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String mapToString(final Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(final String source, final Class<T> clazz) throws IOException {
        return objectMapper.readValue(source, clazz);
    }
}
