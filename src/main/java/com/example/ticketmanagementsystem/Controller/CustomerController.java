package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Model.Customer;
import com.example.ticketmanagementsystem.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    // Additional endpoints can be defined as needed.
}