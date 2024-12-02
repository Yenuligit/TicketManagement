package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public Ticket buyTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket != null) {
            ticketRepository.delete(ticket);
            return ticket; // Return purchased ticket details.
        }
        return null; // Handle case where ticket is not found.
    }
}