package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.Vendor;
import com.example.ticketmanagementsystem.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor addVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // Additional methods can be added as needed for vendor management.
}