package org.example.ticketing.model;

public class Ticket {
    private final String ticketId;
    private final double price;
    private boolean isAvailable;

    public Ticket(String ticketId, double price) {
        this.ticketId = ticketId;
        this.price = price;
        this.isAvailable = true;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Synchronized method to sell the ticket
    public synchronized boolean sell() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + ticketId + '\'' +
                ", price=" + price +
                ", available=" + isAvailable +
                '}';
    }
}