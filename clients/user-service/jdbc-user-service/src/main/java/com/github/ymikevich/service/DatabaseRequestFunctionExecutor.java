package com.github.ymikevich.service;

import com.github.ymikevich.exceptions.DatabaseOperationException;
import com.github.ymikevich.service.connection.pool.HikariCPDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseRequestFunctionExecutor {

    default <R> R execute(DatabaseRequestFunction<Connection, R> function) {
        var connection = HikariCPDataSource.getConnection();
        try {
            return function.apply(connection);
        } catch (SQLException e) {
            rollbackConnection(connection, e);
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DatabaseOperationException("Unexpected query execution error while closing connection", ex);
            }
        }
        return null;
    }

    default void rollbackConnection(Connection connection, SQLException exception) {
        try {
            connection.rollback();
            throw new DatabaseOperationException("Query execution error, rollback successful", exception);
        } catch (SQLException ex) {
            throw new DatabaseOperationException("Unexpected query execution error during rollback operation" ,exception);
        }
    }
}
