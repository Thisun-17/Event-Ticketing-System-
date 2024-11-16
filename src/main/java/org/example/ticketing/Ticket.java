package org.example.ticketing;

public class Ticket {
    // Just two basic properties to start with
    private String ticketId;
    private double price;

    // constructor
    public Ticket(String ticketId, double price) {
        this.ticketId = ticketId;
        this.price = price;
    }

    // getters
    public String getTicketId() {
        return ticketId;
    }

    public double getPrice() {
        return price;
    }
}