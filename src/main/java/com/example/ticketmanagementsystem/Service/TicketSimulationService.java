package com.example.ticketmanagementsystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TicketSimulationService {

    private AtomicInteger addedTickets = new AtomicInteger(0);
    private AtomicInteger boughtTickets = new AtomicInteger(0);
    private boolean isSimulationRunning = false;

    private int totalTickets;
    private int maxTicketCapacity;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Start the simulation with the given parameters.
     */
    public void startSimulation(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        if (isSimulationRunning) return;

        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        isSimulationRunning = true;

        // Start Vendor Thread
        new Thread(() -> {
            while (addedTickets.get() < totalTickets && isSimulationRunning) {
                if (addedTickets.get() < maxTicketCapacity) {
                    addedTickets.incrementAndGet();
                }

                // Broadcast updates to WebSocket
                messagingTemplate.convertAndSend("/topic/ticketAvailability", getAvailableTicketCount());
                messagingTemplate.convertAndSend("/topic/ticketActions", new TicketActions(addedTickets.get(), boughtTickets.get()));

                try {
                    Thread.sleep(ticketReleaseRate * 1000); // Simulate delay for vendor
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        // Start Customer Thread
        new Thread(() -> {
            while (isSimulationRunning) {
                if (boughtTickets.get() < addedTickets.get()) {
                    boughtTickets.incrementAndGet();
                }

                // Broadcast updates to WebSocket
                messagingTemplate.convertAndSend("/topic/ticketAvailability", getAvailableTicketCount());
                messagingTemplate.convertAndSend("/topic/ticketActions", new TicketActions(addedTickets.get(), boughtTickets.get()));

                try {
                    Thread.sleep(customerRetrievalRate * 1000); // Simulate delay for customer
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /**
     * Stop the simulation.
     */
    public void stopSimulation() {
        isSimulationRunning = false;
    }

    /**
     * Get the available ticket count.
     */
    public int getAvailableTicketCount() {
        return addedTickets.get() - boughtTickets.get();
    }

    /**
     * Get the ticket actions (added and bought).
     */
    public TicketActions getTicketActions() {
        return new TicketActions(addedTickets.get(), boughtTickets.get());
    }

    public boolean isSimulationRunning() {
        return isSimulationRunning;
    }

    public void startSimulation(String eventName, int price) {
    }

    // Helper Class for Simulation Stats
    public static class TicketActions {
        private int added;
        private int bought;

        public TicketActions(int added, int bought) {
            this.added = added;
            this.bought = bought;
        }

        public int getAdded() {
            return added;
        }

        public int getBought() {
            return bought;
        }
    }
}
