package com.example.ticketmanagementsystem.Dao;


import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketDAO {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }
}
