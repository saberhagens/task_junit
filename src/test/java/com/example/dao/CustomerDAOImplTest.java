package com.example.dao;

import org.junit.Test;
import org.h2.tools.Csv;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class CustomerDAOImplTest {

    @Test
    public void getCustomerTest_emptyResponse() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        List<Customer> expResult = new ArrayList<>();

        Mockito.when(statement.executeQuery()).thenReturn(rs);
        CustomerDAO cnt = new CustomerDAOImpl(){
            @Override
            public PreparedStatement getStatement(Connection connection) {
                return statement;
            }

            @Override
            public Connection getConnection() throws SQLException {
                return  mock(Connection.class);
            }
        };
        List<Customer> customerTest = cnt.getCustomer(4);
        assertThat(expResult, equalTo(customerTest));
    }


    @Test
    public void getCustomerTest_oneRecord() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = null;
        {
            try {
                String csvResults =
                        "Кислов, Иван, 100.00\n";
                rs = new Csv().read(new StringReader(csvResults), new String[] {"first_name", "last_name", "salary"});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        CustomerDAO cnt = new CustomerDAOImpl(){
            @Override
            public PreparedStatement getStatement(Connection connection) throws SQLException {
                return statement;
            }

            @Override
            public Connection getConnection() throws SQLException {
                return  mock(Connection.class);
            }
        };
        Customer customer = new Customer("Кислов", "Иван", 100.00);
        List<Customer> expResult = new ArrayList<>(Arrays.asList(customer));
        List<Customer> customerTest = cnt.getCustomer(4);

        assertThat(customerTest.get(0).getLastName(), is(expResult.get(0).getLastName()));
        assertThat(customerTest.get(0).getFirstName(), is(expResult.get(0).getFirstName()));
        assertThat(customerTest.get(0).getSalary(), is(expResult.get(0).getSalary()));
    }

    @Test
    public void getCustomerTest_severalRecord() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = null;
        {
            try {
                String csvResults = "Кислов, Иван, 100.00\n" +
                        "Кислов, Иван, 100.00\n" +
                        "Кислов, Иван, 100.00\n";
                rs = new Csv().read(new StringReader(csvResults), new String[] {"first_name", "last_name", "salary"});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Mockito.when(statement.executeQuery()).thenReturn(rs);
        CustomerDAO cnt = new CustomerDAOImpl(){
            @Override
            public PreparedStatement getStatement(Connection connection) throws SQLException {
                return statement;
            }

            @Override
            public Connection getConnection() throws SQLException {
                return  mock(Connection.class);
            }
        };
        Customer customer = new Customer("Кислов", "Иван", 100.00);
        List<Customer> expResult = new ArrayList<>(Arrays.asList(customer, customer, customer));
        List<Customer> customerTest = cnt.getCustomer(4);

        assertThat(customerTest.get(0).getLastName(), is(expResult.get(0).getLastName()));
        assertThat(customerTest.get(0).getFirstName(), is(expResult.get(0).getFirstName()));
        assertThat(customerTest.get(0).getSalary(), is(expResult.get(0).getSalary()));
        assertThat(customerTest.get(0).getLastName(), is(expResult.get(0).getLastName()));
        assertThat(customerTest.get(0).getFirstName(), is(expResult.get(0).getFirstName()));
        assertThat(customerTest.get(0).getSalary(), is(expResult.get(0).getSalary()));
    }
}