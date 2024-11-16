package org.example.ticketing;

public class Main {
    public static void main(String[] args) {
        // Create a ticket
        Ticket ticket = new Ticket("T001", 100.0);

        // Print ticket information
        System.out.println("Ticket ID: " + ticket.getTicketId());
        System.out.println("Ticket Price: $" + ticket.getPrice());
    }
}