package com.github.ymikevich.hibernate.user.service.assist;

import org.dozer.DozerBeanMapper;

import java.util.Collections;

public class DozerBeanMapperProvider {

    private static DozerBeanMapper dozerBeanMapper;

    public static DozerBeanMapper getDozerBeanMapper() {
        if (dozerBeanMapper == null) {
            dozerBeanMapper = new DozerBeanMapper();
            dozerBeanMapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        }
        return dozerBeanMapper;
    }
}
