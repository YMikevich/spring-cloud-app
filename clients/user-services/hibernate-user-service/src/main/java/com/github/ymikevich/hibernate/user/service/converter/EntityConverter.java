package com.github.ymikevich.hibernate.user.service.converter;

import com.github.ymikevich.hibernate.user.service.assist.DozerBeanMapperProvider;
import org.dozer.DozerBeanMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EntityConverter {

    private final DozerBeanMapper dozerBeanMapper = DozerBeanMapperProvider.getDozerBeanMapper();

    public <F, T> T convert(final F from, final Class<T> targetClass) {
        return dozerBeanMapper.map(from, targetClass);
    }

    public <F, T> List<T> convertList(final List<F> from, final Class<T> targetClass) {
        return from.stream().map(it -> dozerBeanMapper.map(it, targetClass)).collect(Collectors.toList());
    }
}
