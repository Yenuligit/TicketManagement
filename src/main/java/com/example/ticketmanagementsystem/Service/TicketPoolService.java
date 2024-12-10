package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Model.TicketPool;
import com.example.ticketmanagementsystem.Repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPoolService {

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    public TicketPool createTicketPool(int maxCapacity) {
        TicketPool ticketPool = new TicketPool(maxCapacity);
        return ticketPoolRepository.save(ticketPool); // Save the ticket pool
    }

    public Ticket addTicketToPool(Long poolId, Ticket ticket) {
        TicketPool ticketPool = ticketPoolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Ticket pool not found"));

        ticketPool.addTicket(ticket); // Add ticket to pool
        ticketPoolRepository.save(ticketPool); // Save updated pool

        return ticket;
    }

    public Ticket buyTicketFromPool(Long poolId) {
        TicketPool ticketPool = ticketPoolRepository.findById(poolId)
                .orElseThrow(() -> new RuntimeException("Ticket pool not found"));

        Ticket ticket = ticketPool.buyTicket(); // Buy ticket
        ticketPoolRepository.save(ticketPool); // Save updated pool

        return ticket;
    }
}
