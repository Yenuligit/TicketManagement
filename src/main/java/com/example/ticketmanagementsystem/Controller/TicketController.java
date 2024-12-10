package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Service.TicketService;
import com.example.ticketmanagementsystem.Service.TicketSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService; // Service to save and retrieve tickets

    @Autowired
    private TicketSimulationService ticketSimulationService; // Service for handling ticket simulation

    // Endpoint to add a new ticket (using data input from GUI)
    @PostMapping("/add")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket); // Saves the ticket to the database
    }

    // Endpoint to start the ticket simulation (triggered by GUI)
    @PostMapping("/startSimulation")
    public String startSimulation(@RequestParam String eventName, @RequestParam int price) {
        ticketSimulationService.startSimulation(eventName, price); // Start the simulation logic
        return "Simulation Started!";
    }

    // Endpoint to stop the ticket simulation (triggered by GUI)
    @PostMapping("/stopSimulation")
    public String stopSimulation() {
        ticketSimulationService.stopSimulation(); // Stop the simulation logic
        return "Simulation Stopped!";
    }

    // Endpoint to get all tickets (optional, if needed for display)
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets(); // Fetch all tickets from the database
    }
}
