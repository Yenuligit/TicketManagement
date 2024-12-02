package com.example.ticketmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_pools")
@Data
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    private int maxCapacity;

    public TicketPool() {}

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            notifyAll(); // Notify waiting customers that a ticket has been added.
        }
    }

    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait until a ticket is available.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return tickets.remove(0); // Remove and return the first ticket.
    }
}