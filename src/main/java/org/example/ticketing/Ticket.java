package org.example.ticketing;

public class Ticket {
    private String ticketId;
    private double price;
    private boolean isAvailable;  // New property

    public Ticket(String ticketId, double price) {
        this.ticketId = ticketId;
        this.price = price;
        this.isAvailable = true;  // New ticket is always available initially
    }

    // Existing getters
    public String getTicketId() {
        return ticketId;
    }

    public double getPrice() {
        return price;
    }

    // New getter for availability
    public boolean isAvailable() {
        return isAvailable;
    }

    // New method to sell ticket
    public void sellTicket() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Ticket " + ticketId + " has been sold.");
        } else {
            System.out.println("Ticket " + ticketId + " is already sold!");
        }
    }
}