package com.example.ticketmanagementsystem.Controller;

import com.example.ticketmanagementsystem.Model.Ticket;
import com.example.ticketmanagementsystem.Model.TicketPool;
import com.example.ticketmanagementsystem.Service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addTicket(@PathVariable Long poolId, @RequestBody Ticket ticket) {
        ticketPoolService.addTicketToPool(poolId, ticket);
    }

    @GetMapping("/{poolId}/buy")
    public Ticket buyTicket(@PathVariable Long poolId) {
        return ticketPoolService.buyTicketFromPool(poolId);
    }
}