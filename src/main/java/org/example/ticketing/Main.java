package org.example.ticketing;

public class Main {
    public static void main(String[] args) {
        System.out.println("Event Ticketing System\n");

        // Create a ticket
        Ticket ticket = new Ticket("T001", 100.0);

        // Test 1: Print initial ticket information
        System.out.println("Initial Ticket Status:");
        System.out.println("Ticket ID: " + ticket.getTicketId());
        System.out.println("Price: $" + ticket.getPrice());
        System.out.println("Available: " + ticket.isAvailable());

        // Test 2: Try to sell the ticket
        System.out.println("\nSelling ticket...");
        ticket.sellTicket();

        // Test 3: Check ticket status after selling
        System.out.println("\nTicket Status After Sale:");
        System.out.println("Ticket ID: " + ticket.getTicketId());
        System.out.println("Available: " + ticket.isAvailable());

        // Test 4: Try to sell the same ticket again
        System.out.println("\nTrying to sell the same ticket again...");
        ticket.sellTicket();
    }
}