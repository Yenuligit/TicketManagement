package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}