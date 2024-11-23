package org.example.ticketing;

import org.example.ticketing.config.Configuration;
import org.example.ticketing.consumer.Customer;
import org.example.ticketing.model.TicketPool;
import org.example.ticketing.producer.Vendor;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Ticket System (Week 03)...\n");

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

            // Lists to store threads and entities
            List<Thread> threads = new ArrayList<>();
            List<Vendor> vendors = new ArrayList<>();
            List<Customer> customers = new ArrayList<>();

            // Create and start vendors (2 vendors)
            System.out.println("\nStarting vendors...");
            for (int i = 1; i <= 2; i++) {
                Vendor vendor = new Vendor("V" + i, ticketPool,
                        config.getDefaultTicketPrice(),
                        config.getVendorReleaseRate());
                vendors.add(vendor);
                Thread vendorThread = new Thread(vendor, "VendorThread-" + i);
                threads.add(vendorThread);
                vendorThread.start();
                System.out.println("Started Vendor " + i);
            }

            // Create and start customers (3 customers)
            System.out.println("\nStarting customers...");
            for (int i = 1; i <= 3; i++) {
                Customer customer = new Customer("C" + i, ticketPool,
                        config.getCustomerPurchaseRate());
                customers.add(customer);
                Thread customerThread = new Thread(customer, "CustomerThread-" + i);
                threads.add(customerThread);
                customerThread.start();
                System.out.println("Started Customer " + i);
            }

            // Run system with periodic status updates
            System.out.println("\nSystem running...");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(5000);  // Wait 5 seconds between updates
                System.out.println("\n=== Status Update " + (i + 1) + " ===");

                // Print pool statistics
                ticketPool.printStatistics();

                // Print vendor statistics
                System.out.println("\nVendor Statistics:");
                for (Vendor vendor : vendors) {
                    System.out.printf("Vendor %s has produced %d tickets%n",
                            vendor.getVendorId(), vendor.getTicketsProduced());
                }

                // Print customer statistics
                System.out.println("\nCustomer Statistics:");
                for (Customer customer : customers) {
                    System.out.printf("Customer %s has purchased %d tickets%n",
                            customer.getCustomerId(), customer.getTicketsPurchased());
                }
            }

            // Graceful shutdown
            System.out.println("\nInitiating system shutdown...");

            // Stop all vendors and customers
            for (Vendor vendor : vendors) {
                vendor.stop();
            }
            for (Customer customer : customers) {
                customer.stop();
            }

            // Wait for all threads to complete
            System.out.println("Waiting for threads to complete...");
            for (Thread thread : threads) {
                thread.join();
            }

            // Final status
            System.out.println("\nFinal System Status:");
            ticketPool.printStatistics();

        } catch (IllegalArgumentException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("System Interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("System Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}