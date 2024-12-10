package com.example.ticketmanagementsystem.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxCapacity;

    @OneToMany(mappedBy = "ticketPool", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    // Default constructor (needed for JPA)
    public TicketPool() {
        this.maxCapacity = 0; // Ensure it is initialized to avoid "not initialized" error
    }

    // Custom constructor for custom pool creation
    public TicketPool(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
            ticket.setTicketPool(this);
        } else {
            throw new IllegalStateException("Ticket pool is full.");
        }
    }

    public synchronized Ticket buyTicket() {
        if (!tickets.isEmpty()) {
            return tickets.remove(0);
        }
        throw new IllegalStateException("No tickets available.");
    }


    public synchronized int getTicketsAvailable() {
        return tickets.size();
    }

    @Override
    public String toString() {
        return "TicketPool{" +
                "id=" + id +
                ", maxCapacity=" + maxCapacity +
                ", tickets=" + tickets.size() +
                '}';
    }
}
