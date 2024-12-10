package com.example.ticketmanagementsystem;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EntityScan(basePackages = "com.example.ticketmanagementsystem.Model") // Ensure only entities are scanned
public class TicketManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketManagementSystemApplication.class, args);
    }
}
