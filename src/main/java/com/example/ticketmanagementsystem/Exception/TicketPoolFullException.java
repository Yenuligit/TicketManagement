package com.example.ticketmanagementsystem.Exception;

public class TicketPoolFullException extends RuntimeException {
    public TicketPoolFullException(String message) {
        super(message);
    }
}

