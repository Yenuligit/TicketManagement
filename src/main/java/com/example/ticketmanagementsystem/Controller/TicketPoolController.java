package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Exception.TicketPoolFullException;
import com.example.ticketmanagementsystem.Exception.TicketPoolNotFoundException;
import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Model.TicketPool;
import com.example.ticketmanagementsystem.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketpool")
public class TicketPoolController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping("/create")
    public TicketPool createTicketPool(@RequestParam int maxCapacity) {
        return ticketPoolService.createTicketPool(maxCapacity);
    }

    @PostMapping("/{poolId}/add")
    public ResponseEntity<?> addTicket(@PathVariable Long poolId, @RequestBody Ticket ticket) {
        try {
            ticketPoolService.addTicketToPool(poolId, ticket);
            return ResponseEntity.ok().build();  // Return 200 OK
        } catch (TicketPoolNotFoundException | TicketPoolFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{poolId}/buy")
    public ResponseEntity<Ticket> buyTicket(@PathVariable Long poolId) {
        try {
            Ticket ticket = ticketPoolService.buyTicketFromPool(poolId);
            return ResponseEntity.ok(ticket);
        } catch (TicketPoolNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if pool not found
        }
    }
}
