package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Model.Vendor;
import com.example.ticketmanagementsystem.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/add")
    public Vendor addVendor(@RequestBody Vendor vendor) {
        return vendorService.addVendor(vendor);
    }

    // Additional endpoints can be defined as needed.
}