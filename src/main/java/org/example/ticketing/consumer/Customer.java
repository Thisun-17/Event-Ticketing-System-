package org.example.ticketing.consumer;

import org.example.ticketing.model.Ticket;
import org.example.ticketing.model.TicketPool;

public class Customer implements Runnable {
    private final String customerId;
    private final TicketPool ticketPool;
    private final int purchaseRate;  // How many purchases to attempt per second
    private int ticketsPurchased;
    private volatile boolean isRunning;

    public Customer(String customerId, TicketPool ticketPool, int purchaseRate) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.purchaseRate = purchaseRate;
        this.ticketsPurchased = 0;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                // Attempt to purchase ticket
                Ticket ticket = ticketPool.purchaseTicket();
                if (ticket != null) {
                    ticketsPurchased++;
                    System.out.println("Customer " + customerId + " purchased ticket: " + ticket.getTicketId());
                }

                // Wait based on purchase rate
                Thread.sleep(1000 / purchaseRate);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stop();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    // Getters
    public int getTicketsPurchased() {
        return ticketsPurchased;
    }

    public String getCustomerId() {
        return customerId;
    }
}