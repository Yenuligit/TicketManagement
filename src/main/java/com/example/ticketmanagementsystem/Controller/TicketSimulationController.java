package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Service.TicketSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
public class TicketSimulationController {

    @Autowired
    private TicketSimulationService simulationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // To send WebSocket updates

    /**
     * Start the simulation with parameters.
     * Handles input validation, checks for conflicts, and broadcasts updates.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startSimulation(@RequestBody SimulationParams params) {
        // Manual validation for params
        if (params.getTotalTickets() <= 0 || params.getMaxTicketCapacity() <= 0) {
            return ResponseEntity.badRequest().body("{ \"message\": \"Total tickets and max capacity must be greater than zero.\" }");
        }

        if (simulationService.isSimulationRunning()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{ \"message\": \"Simulation is already running.\" }");
        }

        try {
            // Start simulation
            simulationService.startSimulation(
                    params.getTotalTickets(),
                    params.getTicketReleaseRate(),
                    params.getCustomerRetrievalRate(),
                    params.getMaxTicketCapacity()
            );

            return ResponseEntity.ok().body("{ \"message\": \"Simulation started successfully\" }");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ \"message\": \"Error starting simulation.\" }");
        }
    }

    /**
     * Stop the simulation.
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopSimulation() {
        if (!simulationService.isSimulationRunning()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{ \"message\": \"No simulation is currently running.\" }");
        }

        simulationService.stopSimulation();
        messagingTemplate.convertAndSend("/topic/simulationStatus", "Simulation Stopped");
        return ResponseEntity.ok().body("{ \"message\": \"Simulation stopped successfully\" }");
    }

    /**
     * Get the current number of available tickets.
     */
    @GetMapping("/tickets/available-count")
    public ResponseEntity<?> getAvailableTickets() {
        int availableTickets = simulationService.getAvailableTicketCount();
        return ResponseEntity.ok().body("{ \"availableTickets\": " + availableTickets + " }");
    }

    /**
     * Get ticket actions (added and bought).
     */
    @GetMapping("/tickets/actions")
    public ResponseEntity<?> getTicketActions() {
        TicketSimulationService.TicketActions ticketActions = simulationService.getTicketActions();
        return ResponseEntity.ok().body(ticketActions);
    }

    /**
     * Helper class for simulation input params.
     */
    public static class SimulationParams {
        private int totalTickets;
        private int ticketReleaseRate;
        private int customerRetrievalRate;
        private int maxTicketCapacity;

        public int getTotalTickets() { return totalTickets; }
        public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

        public int getTicketReleaseRate() { return ticketReleaseRate; }
        public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

        public int getCustomerRetrievalRate() { return customerRetrievalRate; }
        public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

        public int getMaxTicketCapacity() { return maxTicketCapacity; }
        public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }
    }
}
