package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Custom queries can be added if necessary
}
