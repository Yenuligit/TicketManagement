package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Model.TicketPool;
import com.example.ticketmanagementsystem.Repository.TicketPoolRepository;
import com.example.ticketmanagementsystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



@Service
public class SimulationService {

    private ExecutorService executorService;
    private volatile boolean running;

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void startSimulation(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        if (running) {
            System.out.println("Simulation is already running!");
            return;
        }

        System.out.println("Starting Simulation...");
        running = true;
        executorService = Executors.newFixedThreadPool(10);

        TicketPool ticketPool = new TicketPool(maxTicketCapacity);
        ticketPoolRepository.save(ticketPool);

        // Vendor Thread (Simulates ticket releases)
        executorService.submit(() -> {
            int ticketsAdded = 0;
            while (running && ticketsAdded < totalTickets) {
                synchronized (ticketPool) {
                    if (ticketPool.getTicketsAvailable() < maxTicketCapacity) {
                        Ticket ticket = new Ticket("Event Name", 100);
                        ticketPool.addTicket(ticket);
                        ticketRepository.save(ticket);
                        ticketsAdded++;

                        // Broadcast to WebSocket /topic/ticketAvailability
                        messagingTemplate.convertAndSend("/topic/ticketAvailability", ticketPool.getTicketsAvailable());
                        messagingTemplate.convertAndSend("/topic/ticketActions", Map.of("added", ticketsAdded, "bought", 0));

                        System.out.println("Ticket Added: " + ticket);
                    }
                }
                try {
                    Thread.sleep(ticketReleaseRate * 1000L); // Delay based on release rate
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Vendor Thread: Finished adding all tickets.");
        });

        // Customer Threads (Simulate customer purchases)
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                int ticketsBought = 0;
                while (running) {
                    synchronized (ticketPool) {
                        if (ticketPool.getTicketsAvailable() > 0) {
                            Ticket ticket = ticketPool.buyTicket();
                            ticketRepository.delete(ticket);
                            ticketsBought++;

                            // Broadcast to WebSocket /topic/ticketAvailability
                            messagingTemplate.convertAndSend("/topic/ticketAvailability", ticketPool.getTicketsAvailable());
                            messagingTemplate.convertAndSend("/topic/ticketActions", Map.of("added", 0, "bought", ticketsBought));

                            System.out.println("Ticket Bought: " + ticket);
                        }
                    }
                    try {
                        Thread.sleep(customerRetrievalRate * 1000L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Customer Thread: Finished buying tickets.");
            });
        }
    }

    public void stopSimulation() {
        if (!running) {
            System.out.println("Simulation is not running.");
            return;
        }

        System.out.println("Stopping Simulation...");
        running = false;

        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Simulation Stopped.");
    }

    public boolean isSimulationRunning() {
        return running;
    }

    public int getAvailableTicketCount() {
        return ticketPoolRepository.findAll().stream()
                .mapToInt(TicketPool::getTicketsAvailable)
                .sum();
    }

}
