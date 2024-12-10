package com.example.ticketmanagementsystem.Exception;

public class TicketCapacityExceededException extends TicketManagementException {
    public TicketCapacityExceededException(String message) {
        super(message);
    }
}
