package com.example.ticketmanagementsystem.Service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service  // Ensure this class is registered as a Spring service
public class TicketCounterService {

    private AtomicInteger addedTickets = new AtomicInteger(0);
    private AtomicInteger boughtTickets = new AtomicInteger(0);

    // Method to reset counters
    public void resetCounters() {
        addedTickets.set(0);
        boughtTickets.set(0);
    }

    // Increment added tickets
    public void incrementAddedTickets() {
        addedTickets.incrementAndGet();
    }

    // Increment bought tickets
    public void incrementBoughtTickets() {
        boughtTickets.incrementAndGet();
    }

    // Get the number of added tickets
    public int getAddedTickets() {
        return addedTickets.get();
    }

    // Get the number of bought tickets
    public int getBoughtTickets() {
        return boughtTickets.get();
    }
}
