package org.example.ticketing.model;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> tickets;
    private final int maxCapacity;
    private final AtomicInteger totalTicketsSold;
    private final AtomicInteger ticketsCreated;
    private final AtomicInteger failedPurchaseAttempts;
    private long startTime;

    public TicketPool(int maxCapacity) {
        this.tickets = new ConcurrentLinkedQueue<>();
        this.maxCapacity = maxCapacity;
        this.totalTicketsSold = new AtomicInteger(0);
        this.ticketsCreated = new AtomicInteger(0);
        this.failedPurchaseAttempts = new AtomicInteger(0);
        this.startTime = System.currentTimeMillis();
    }

    public synchronized boolean addTicket(Ticket ticket) {
        try {
            if (tickets.size() < maxCapacity) {
                boolean added = tickets.offer(ticket);
                if (added) {
                    ticketsCreated.incrementAndGet();
                    System.out.printf("Added ticket: %s (Pool: %d/%d)%n",
                            ticket.getTicketId(), tickets.size(), maxCapacity);
                }
                return added;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error adding ticket: " + e.getMessage());
            return false;
        }
    }

    public synchronized Ticket purchaseTicket() {
        try {
            Ticket ticket = tickets.poll();
            if (ticket != null && ticket.sell()) {
                totalTicketsSold.incrementAndGet();
                System.out.printf("Sold ticket: %s (Remaining: %d)%n",
                        ticket.getTicketId(), tickets.size());
                return ticket;
            } else {
                failedPurchaseAttempts.incrementAndGet();
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error purchasing ticket: " + e.getMessage());
            failedPurchaseAttempts.incrementAndGet();
            return null;
        }
    }

    public void printStatistics() {
        long runningTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("\n=== Ticket Pool Statistics ===");
        System.out.println("Running Time: " + runningTime + " seconds");
        System.out.println("Available Tickets: " + tickets.size() + "/" + maxCapacity);
        System.out.println("Total Tickets Created: " + ticketsCreated.get());
        System.out.println("Total Tickets Sold: " + totalTicketsSold.get());
        System.out.println("Failed Purchase Attempts: " + failedPurchaseAttempts.get());

        if (runningTime > 0) {
            double salesRate = (double) totalTicketsSold.get() / runningTime;
            System.out.printf("Sales Rate: %.2f tickets/second%n", salesRate);
        }

        double successRate = totalTicketsSold.get() + failedPurchaseAttempts.get() > 0 ?
                (double) totalTicketsSold.get() / (totalTicketsSold.get() + failedPurchaseAttempts.get()) * 100 : 0;
        System.out.printf("Purchase Success Rate: %.1f%%%n", successRate);
        System.out.println("============================\n");
    }

    public void resetStatistics() {
        startTime = System.currentTimeMillis();
        totalTicketsSold.set(0);
        ticketsCreated.set(0);
        failedPurchaseAttempts.set(0);
    }

    // Getters
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