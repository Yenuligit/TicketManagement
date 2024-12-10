package com.example.ticketmanagementsystem.Dao;


import com.example.ticketmanagementsystem.Model.Vendor;
import com.example.ticketmanagementsystem.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VendorDAO {
    @Autowired
    private VendorRepository vendorRepository;

    public Vendor save(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Optional<Vendor> findById(Long id) {
        return vendorRepository.findById(id);
    }

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }
}

