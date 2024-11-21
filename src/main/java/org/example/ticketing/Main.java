package org.example.ticketing;

import org.example.ticketing.config.Configuration;
import org.example.ticketing.model.TicketPool;
import org.example.ticketing.producer.Vendor;
import org.example.ticketing.config.Customer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ticket System (Week 02 Final)...\n");

        try {
            // Initialize with validated configuration
            Configuration config = new Configuration(
                    5,      // maxTicketPoolCapacity (1-100)
                    2,      // vendorReleaseRate (1-10 per second)
                    1,      // customerPurchaseRate (1-10 per second)
                    100.0   // defaultTicketPrice ($10-$1000)
            );

            // Display configuration
            System.out.println(config);

            // Initialize system components
            TicketPool ticketPool = new TicketPool(config.getMaxTicketPoolCapacity());
            Vendor vendor = new Vendor("V1", ticketPool,
                    config.getDefaultTicketPrice(),
                    config.getVendorReleaseRate());
            Customer customer = new Customer("C1", ticketPool,
                    config.getCustomerPurchaseRate());

            // Create and start threads
            Thread vendorThread = new Thread(vendor, "VendorThread");
            Thread customerThread = new Thread(customer, "CustomerThread");

            System.out.println("Starting system threads...");
            vendorThread.start();
            customerThread.start();

            // Run system with periodic status updates
            for (int i = 0; i < 3; i++) {
                Thread.sleep(5000);
                ticketPool.printStatistics();
            }

            // Graceful shutdown
            System.out.println("\nInitiating system shutdown...");
            vendor.stop();
            customer.stop();

            vendorThread.join();
            customerThread.join();

            // Final status
            System.out.println("\nFinal System Status:");
            ticketPool.printStatistics();

        } catch (IllegalArgumentException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("System Interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("System Error: " + e.getMessage());
        }
    }
}