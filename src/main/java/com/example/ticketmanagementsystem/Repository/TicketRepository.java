package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}