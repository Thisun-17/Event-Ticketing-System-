package org.example.ticketing;

import org.example.ticketing.config.Configuration;
import org.example.ticketing.model.TicketPool;
import org.example.ticketing.producer.Vendor;
import org.example.ticketing.consumer.Customer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ticket System (Week 02 Implementation)...\n");

        // 1. Initialize Configuration
        Configuration config = new Configuration(
                5,      // maxTicketPoolCapacity
                2,      // vendorReleaseRate
                1,      // customerPurchaseRate
                100.0   // defaultTicketPrice
        );

        // 2. Create TicketPool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketPoolCapacity());

        // 3. Create Producer (Vendor)
        Vendor vendor = new Vendor(
                "V1",
                ticketPool,
                config.getDefaultTicketPrice(),
                config.getVendorReleaseRate()
        );

        // 4. Create Consumer (Customer)
        Customer customer = new Customer(
                "C1",
                ticketPool,
                config.getCustomerPurchaseRate()
        );

        // 5. Create Threads
        Thread vendorThread = new Thread(vendor, "VendorThread");
        Thread customerThread = new Thread(customer, "CustomerThread");

        try {
            // 6. Start System
            System.out.println("System Configuration:");
            System.out.println("- Max Pool Capacity: " + config.getMaxTicketPoolCapacity());
            System.out.println("- Vendor Release Rate: " + config.getVendorReleaseRate() + " tickets/second");
            System.out.println("- Customer Purchase Rate: " + config.getCustomerPurchaseRate() + " attempts/second\n");

            vendorThread.start();
            customerThread.start();

            // 7. Run System
            for (int i = 0; i < 3; i++) {
                Thread.sleep(5000); // Check every 5 seconds
                ticketPool.printStatistics();
            }

            // 8. Stop System
            vendor.stop();
            customer.stop();

            // 9. Wait for Completion
            vendorThread.join();
            customerThread.join();

            // 10. Final Statistics
            System.out.println("\nFinal Results:");
            System.out.println("Vendor (" + vendor.getVendorId() + ") produced: " + vendor.getTicketsProduced());
            System.out.println("Customer (" + customer.getCustomerId() + ") purchased: " + customer.getTicketsPurchased());
            ticketPool.printStatistics();

        } catch (InterruptedException e) {
            System.out.println("System interrupted: " + e.getMessage());
        }
    }
}