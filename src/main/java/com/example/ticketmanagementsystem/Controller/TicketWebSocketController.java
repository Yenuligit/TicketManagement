package com.example.ticketmanagementsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class TicketWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendTicketAvailability(int availableTickets) {
        messagingTemplate.convertAndSend("/topic/ticketAvailability", availableTickets);
    }

    public void sendTicketActions(int added, int bought) {
        messagingTemplate.convertAndSend("/topic/ticketActions", Map.of("added", added, "bought", bought));
    }
}
