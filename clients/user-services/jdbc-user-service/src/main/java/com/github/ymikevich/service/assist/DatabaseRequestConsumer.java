package com.github.ymikevich.service.assist;

import java.sql.SQLException;

@FunctionalInterface
public interface DatabaseRequestConsumer<T> {

    void accept(T t) throws SQLException;
}
