package org.example.ticketing;

import org.example.ticketing.config.Configuration;
import org.example.ticketing.model.TicketPool;
import org.example.ticketing.producer.Vendor;
import org.example.ticketing.consumer.Customer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ticket System with Configuration...");

        try {
            // Initialize configuration
            Configuration config = new Configuration(
                    10,     // maxTicketPoolCapacity
                    2,      // vendorReleaseRate (2 tickets per second)
                    1,      // customerPurchaseRate (1 purchase attempt per second)
                    100.0   // defaultTicketPrice
            );

            // Create ticket pool using configuration
            TicketPool ticketPool = new TicketPool(config.getMaxTicketPoolCapacity());

            // Create vendor and customer using configuration
            Vendor vendor = new Vendor("V1", ticketPool, config.getDefaultTicketPrice(),
                    config.getVendorReleaseRate());
            Customer customer = new Customer("C1", ticketPool,
                    config.getCustomerPurchaseRate());

            // Create threads
            Thread vendorThread = new Thread(vendor);
            Thread customerThread = new Thread(customer);

            // Start threads
            vendorThread.start();
            customerThread.start();

            // Run system for 10 seconds
            Thread.sleep(10000);

            // Print mid-point status
            System.out.println("\nMid-point Status:");
            System.out.println("Tickets in pool: " + ticketPool.getAvailableTickets());
            System.out.println("Vendor produced: " + vendor.getTicketsProduced());
            System.out.println("Customer purchased: " + customer.getTicketsPurchased());

            // Stop threads
            vendor.stop();
            customer.stop();

            // Wait for threads to finish
            vendorThread.join();
            customerThread.join();

            // Print final results
            System.out.println("\nFinal Results:");
            System.out.println("Total tickets produced: " + vendor.getTicketsProduced());
            System.out.println("Total tickets purchased: " + customer.getTicketsPurchased());
            System.out.println("Remaining tickets: " + ticketPool.getAvailableTickets());

        } catch (IllegalArgumentException e) {
            System.out.println("Configuration error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("System interrupted: " + e.getMessage());
        }
    }
}