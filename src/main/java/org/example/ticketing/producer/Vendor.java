package org.example.ticketing.producer;

import org.example.ticketing.model.Ticket;
import org.example.ticketing.model.TicketPool;

public class Vendor implements Runnable {
    private final String vendorId;
    private final TicketPool ticketPool;
    private final double ticketPrice;
    private int ticketsProduced;
    private volatile boolean isRunning;
    private final int releaseRate;  // How many tickets to release per second

    public Vendor(String vendorId, TicketPool ticketPool, double ticketPrice, int releaseRate) {
        this.vendorId = vendorId;
        this.ticketPool = ticketPool;
        this.ticketPrice = ticketPrice;
        this.releaseRate = releaseRate;
        this.ticketsProduced = 0;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                // Create new ticket
                String ticketId = vendorId + "-" + (ticketsProduced + 1);
                Ticket ticket = new Ticket(ticketId, ticketPrice);

                // Try to add to pool
                if (ticketPool.addTicket(ticket)) {
                    ticketsProduced++;
                    System.out.println("Vendor " + vendorId + " created ticket: " + ticketId);
                }

                // Wait based on release rate
                Thread.sleep(1000 / releaseRate);

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
    public int getTicketsProduced() {
        return ticketsProduced;
    }

    public String getVendorId() {
        return vendorId;
    }
}