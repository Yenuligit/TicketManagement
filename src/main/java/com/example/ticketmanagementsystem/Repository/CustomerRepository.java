package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}