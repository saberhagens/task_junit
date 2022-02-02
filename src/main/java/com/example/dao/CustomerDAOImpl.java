package com.example.dao;


import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@Service
public class CustomerDAOImpl implements CustomerDAO{
    private String user = "test";
    private String password = "test";
    private String url = "jdbc:oracle:thin:@0.0.0.0:1521/orclpdb1";
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String query = "SELECT * FROM Employees where id=?";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public CustomerDAOImpl() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public List<Customer> getCustomer(long id) {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = getStatement(connection);
            statement.setString(1, String.valueOf(id));
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Customer customer = new Customer();
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setSalary(rs.getDouble("salary"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}