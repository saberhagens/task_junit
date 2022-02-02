package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomer(long id);

    PreparedStatement getStatement(Connection connection) throws SQLException;

    Connection getConnection() throws SQLException;
}
