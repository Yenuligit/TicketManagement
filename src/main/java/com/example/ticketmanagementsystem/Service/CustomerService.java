package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.Customer;
import com.example.ticketmanagementsystem.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Additional methods can be added as needed for customer management.
}