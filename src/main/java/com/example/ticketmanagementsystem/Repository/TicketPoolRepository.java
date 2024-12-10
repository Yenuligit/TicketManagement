package com.example.ticketmanagementsystem.Repository;

import com.example.ticketmanagementsystem.Model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}
