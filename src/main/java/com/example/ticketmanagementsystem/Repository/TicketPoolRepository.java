package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}