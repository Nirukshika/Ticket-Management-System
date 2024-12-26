package com.example.ticket_managment_system;

import java.util.ArrayList;
import java.util.List;

public class BackendManager {

    private TicketPool ticketPool;
    private List<Thread> vendorThreads;
    private List<Thread> customerThreads;
    private boolean isRunning;

    public BackendManager() {
        this.ticketPool = new TicketPool();
        this.vendorThreads = new ArrayList<>();
        this.customerThreads = new ArrayList<>();
        this.isRunning = false;
    }

    public void configureSystem(int vendorCount, int ticketsPerRelease, int releaseInterval,
                                int customerCount, int retrievalInterval) {
        if (isRunning) {
            throw new IllegalStateException("System is already running. Stop it before reconfiguring.");
        }

        // Clear any existing threads
        vendorThreads.clear();
        customerThreads.clear();

        // Create vendors
        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor("Vendor-" + i, ticketsPerRelease, releaseInterval, ticketPool);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
        }

        // Create customers
        for (int i = 0; i < customerCount; i++) {
            Customer customer = new Customer("Customer-" + i, retrievalInterval, ticketPool);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
        }
    }

    public void startSystem() {
        if (isRunning) {
            throw new IllegalStateException("System is already running.");
        }
        isRunning = true;

        // Start all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
        }

        // Start all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.start();
        }

        System.out.println("System started.");
    }

    public void stopSystem() {
        if (!isRunning) {
            System.out.println("System is not running.");
            return;
        }

        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        vendorThreads.clear();
        customerThreads.clear();
        isRunning = false;

        System.out.println("System stopped.");
    }

    public int getAvailableTickets() {
        return ticketPool.getAvailableTickets();
    }

    public boolean isRunning() {
        return isRunning;
    }
    public void addVendor(String vendorId, int ticketsPerRelease, int releaseInterval) {
        Vendor vendor = new Vendor(vendorId, ticketsPerRelease, releaseInterval, ticketPool);
        Thread vendorThread = new Thread(vendor);
        vendorThreads.add(vendorThread);
        vendorThread.start();
        Logger.log("Vendor added: " + vendorId);
    }

    public void removeVendor(String vendorId) {
        // Logic to find and stop the vendor thread (not shown for brevity)
        Logger.log("Vendor removed: " + vendorId);
    }

    public void addCustomer(String customerId, int retrievalInterval) {
        Customer customer = new Customer(customerId, retrievalInterval, ticketPool);
        Thread customerThread = new Thread(customer);
        customerThreads.add(customerThread);
        customerThread.start();
        Logger.log("Customer added: " + customerId);
    }

    public void removeCustomer(String customerId) {
        // Logic to find and stop the customer thread (not shown for brevity)
        Logger.log("Customer removed: " + customerId);
    }

}

