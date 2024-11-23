package org.example.ticketing.config;

public class Configuration {
    // Constants for validation
    private static final int MIN_POOL_CAPACITY = 1;
    private static final int MAX_POOL_CAPACITY = 100;
    private static final int MIN_RATE = 1;
    private static final int MAX_RATE = 10;
    private static final double MIN_PRICE = 10.0;
    private static final double MAX_PRICE = 1000.0;

    private int maxTicketPoolCapacity;
    private int vendorReleaseRate;
    private int customerPurchaseRate;
    private double defaultTicketPrice;

    // Default constructor
    public Configuration() {
        this(10, 2, 1, 100.0);
    }

    // Constructor with parameters
    public Configuration(int maxTicketPoolCapacity, int vendorReleaseRate,
                         int customerPurchaseRate, double defaultTicketPrice) {
        validateAndSetPoolCapacity(maxTicketPoolCapacity);
        validateAndSetVendorRate(vendorReleaseRate);
        validateAndSetCustomerRate(customerPurchaseRate);
        validateAndSetTicketPrice(defaultTicketPrice);
    }

    private void validateAndSetPoolCapacity(int capacity) {
        if (capacity < MIN_POOL_CAPACITY || capacity > MAX_POOL_CAPACITY) {
            throw new IllegalArgumentException(
                    String.format("Pool capacity must be between %d and %d",
                            MIN_POOL_CAPACITY, MAX_POOL_CAPACITY)
            );
        }
        this.maxTicketPoolCapacity = capacity;
    }

    private void validateAndSetVendorRate(int rate) {
        if (rate < MIN_RATE || rate > MAX_RATE) {
            throw new IllegalArgumentException(
                    String.format("Vendor release rate must be between %d and %d per second",
                            MIN_RATE, MAX_RATE)
            );
        }
        this.vendorReleaseRate = rate;
    }

    private void validateAndSetCustomerRate(int rate) {
        if (rate < MIN_RATE || rate > MAX_RATE) {
            throw new IllegalArgumentException(
                    String.format("Customer purchase rate must be between %d and %d per second",
                            MIN_RATE, MAX_RATE)
            );
        }
        this.customerPurchaseRate = rate;
    }

    private void validateAndSetTicketPrice(double price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(
                    String.format("Ticket price must be between %.2f and %.2f",
                            MIN_PRICE, MAX_PRICE)
            );
        }
        this.defaultTicketPrice = price;
    }

    // Getters
    public int getMaxTicketPoolCapacity() {
        return maxTicketPoolCapacity;
    }

    public int getVendorReleaseRate() {
        return vendorReleaseRate;
    }

    public int getCustomerPurchaseRate() {
        return customerPurchaseRate;
    }

    public double getDefaultTicketPrice() {
        return defaultTicketPrice;
    }

    @Override
    public String toString() {
        return String.format("""
            System Configuration:
            - Max Pool Capacity: %d
            - Vendor Release Rate: %d tickets/second
            - Customer Purchase Rate: %d attempts/second
            - Default Ticket Price: $%.2f
            """,
                maxTicketPoolCapacity,
                vendorReleaseRate,
                customerPurchaseRate,
                defaultTicketPrice);
    }
}