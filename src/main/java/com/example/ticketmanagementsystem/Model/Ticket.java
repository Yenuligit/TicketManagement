package com.example.ticketmanagementsystem.Model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private int price;

    @ManyToOne
    @JoinColumn(name = "ticket_pool_id")
    private TicketPool ticketPool;

    public Ticket() {}

    public Ticket(String eventName, int price) {
        this.eventName = eventName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", eventName='" + eventName + "', price=" + price + '}';
    }
}
