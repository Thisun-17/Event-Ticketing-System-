package org.example.ticketing.model;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {
    // Thread-safe queue to store tickets
    private final ConcurrentLinkedQueue<Ticket> tickets;
    private final int maxCapacity;
    private int totalTicketsSold;

    // Constructor
    public TicketPool(int maxCapacity) {
        this.tickets = new ConcurrentLinkedQueue<>();
        this.maxCapacity = maxCapacity;
        this.totalTicketsSold = 0;
    }

    // Method for vendors to add tickets to pool
    public synchronized boolean addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            boolean added = tickets.offer(ticket);
            if (added) {
                System.out.println("Added ticket: " + ticket.getTicketId());
            }
            return added;
        }
        System.out.println("Pool is full - cannot add ticket");
        return false;
    }

    // Method for customers to purchase tickets from pool
    public synchronized Ticket purchaseTicket() {
        Ticket ticket = tickets.poll();
        if (ticket != null && ticket.sell()) {
            totalTicketsSold++;
            System.out.println("Sold ticket: " + ticket.getTicketId());
            return ticket;
        }
        return null;
    }

    // Get current number of available tickets
    public int getAvailableTickets() {
        return tickets.size();
    }

    // Get total tickets sold
    public int getTotalTicketsSold() {
        return totalTicketsSold;
    }

    // Get maximum capacity of the pool
    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Check if pool is full
    public boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    // Check if pool is empty
    public boolean isEmpty() {
        return tickets.isEmpty();
    }
}