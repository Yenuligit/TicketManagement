package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/add")
    public void addTicket(@RequestBody Ticket ticket) {
        ticketService.addTicket(ticket);
    }

    @GetMapping("/buy/{id}")
    public Ticket buyTicket(@PathVariable Long id) {
        return ticketService.buyTicket(id);
    }
}