package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.TicketPool;
import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPoolService {

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    public TicketPool createTicketPool(int maxCapacity) {
        TicketPool ticketPool = new TicketPool(maxCapacity);
        return ticketPoolRepository.save(ticketPool);
    }

    public void addTicketToPool(Long poolId, Ticket ticket) {
        TicketPool ticketPool = ticketPoolRepository.findById(poolId).orElse(null);
        if (ticketPool != null) {
            ticketPool.addTicket(ticket);
            ticketPoolRepository.save(ticketPool); // Save changes to the database.
        }
    }

    public Ticket buyTicketFromPool(Long poolId) {
        TicketPool ticketPool = ticketPoolRepository.findById(poolId).orElse(null);
        if (ticketPool != null) {
            return ticketPool.buyTicket();
        }
        return null; // Handle case where pool is not found.
    }
}