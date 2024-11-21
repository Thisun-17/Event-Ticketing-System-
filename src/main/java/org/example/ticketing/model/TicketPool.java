package org.example.ticketing.model;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> tickets;
    private final int maxCapacity;
    private final AtomicInteger totalTicketsSold;
    private final AtomicInteger ticketsCreated;

    // New monitoring fields
    private long startTime;
    private final AtomicInteger failedPurchaseAttempts;

    public TicketPool(int maxCapacity) {
        this.tickets = new ConcurrentLinkedQueue<>();
        this.maxCapacity = maxCapacity;
        this.totalTicketsSold = new AtomicInteger(0);
        this.ticketsCreated = new AtomicInteger(0);
        this.failedPurchaseAttempts = new AtomicInteger(0);
        this.startTime = System.currentTimeMillis();
    }

    public synchronized boolean addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            boolean added = tickets.offer(ticket);
            if (added) {
                ticketsCreated.incrementAndGet();
                System.out.println("Added ticket: " + ticket.getTicketId() +
                        " (Pool: " + tickets.size() + "/" + maxCapacity + ")");
            }
            return added;
        }
        return false;
    }

    public synchronized Ticket purchaseTicket() {
        Ticket ticket = tickets.poll();
        if (ticket != null && ticket.sell()) {
            totalTicketsSold.incrementAndGet();
            System.out.println("Sold ticket: " + ticket.getTicketId() +
                    " (Remaining: " + tickets.size() + ")");
            return ticket;
        } else {
            failedPurchaseAttempts.incrementAndGet();
        }
        return null;
    }

    // New monitoring methods
    public void printStatistics() {
        long runningTime = (System.currentTimeMillis() - startTime) / 1000; // in seconds
        System.out.println("\n=== Ticket Pool Statistics ===");
        System.out.println("Running Time: " + runningTime + " seconds");
        System.out.println("Available Tickets: " + tickets.size() + "/" + maxCapacity);
        System.out.println("Total Tickets Created: " + ticketsCreated.get());
        System.out.println("Total Tickets Sold: " + totalTicketsSold.get());
        System.out.println("Failed Purchase Attempts: " + failedPurchaseAttempts.get());
        if (runningTime > 0) {
            System.out.println("Sales Rate: " + (totalTicketsSold.get() / runningTime) + " tickets/second");
        }
        System.out.println("============================\n");
    }

    public void resetStatistics() {
        startTime = System.currentTimeMillis();
        totalTicketsSold.set(0);
        ticketsCreated.set(0);
        failedPurchaseAttempts.set(0);
    }

    // Getters for monitoring
    public int getAvailableTickets() {
        return tickets.size();
    }

    public int getTotalTicketsSold() {
        return totalTicketsSold.get();
    }

    public int getTicketsCreated() {
        return ticketsCreated.get();
    }

    public int getFailedPurchaseAttempts() {
        return failedPurchaseAttempts.get();
    }
}