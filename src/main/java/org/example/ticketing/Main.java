package org.example.ticketing;

import org.example.ticketing.model.Ticket;
import org.example.ticketing.model.TicketPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ticket System Test...\n");

        // Create a ticket pool with max capacity of 5
        TicketPool ticketPool = new TicketPool(5);

        // Add some test tickets
        for (int i = 1; i <= 3; i++) {
            Ticket ticket = new Ticket("T00" + i, 100.0);
            ticketPool.addTicket(ticket);
        }

        System.out.println("\nAvailable tickets: " + ticketPool.getAvailableTickets());
        System.out.println("Total tickets sold: " + ticketPool.getTotalTicketsSold());

        // Try to purchase tickets
        System.out.println("\nAttempting to purchase tickets...");
        Ticket purchasedTicket1 = ticketPool.purchaseTicket();
        Ticket purchasedTicket2 = ticketPool.purchaseTicket();

        System.out.println("\nFinal Status:");
        System.out.println("Available tickets: " + ticketPool.getAvailableTickets());
        System.out.println("Total tickets sold: " + ticketPool.getTotalTicketsSold());
    }
}