package com.example.controller;

import com.example.dao.Customer;
import com.example.dao.CustomerDAO;
import com.example.dao.CustomerDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class DBController {
    @Autowired
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @GetMapping("/search")
    public List<Customer> search(@RequestParam(value = "id", required = true) int id) {
        return customerDAO.getCustomer(id);
    }
}
