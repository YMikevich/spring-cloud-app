package com.github.ymikevich.service.assist;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseRequestFunction<T, R> {

    R apply(T t) throws SQLException;
}
