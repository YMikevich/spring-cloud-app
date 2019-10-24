package com.github.ymikevich.service.utils;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseRequestConsumer<T> {

    void accept(T t) throws SQLException;
}
