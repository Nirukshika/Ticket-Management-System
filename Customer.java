package com.example.ticket_managment_system;

public class Customer implements Runnable {
    private final String customerId;
    private final TicketPool ticketPool;
    private final int retrievalInterval; // in milliseconds
    private volatile boolean running = true;

    public Customer(String customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (running) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    Logger.log("Customer " + customerId + " purchased ticket: " + ticket);
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            Logger.log("Customer " + customerId + " interrupted.");
        }
    }

    public void stop() {
        running = false;
    }
}

