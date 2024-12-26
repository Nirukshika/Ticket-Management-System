package com.example.ticket_managment_system;

public class Vendor implements Runnable {
    private final String vendorId;
    private final int ticketsPerRelease;
    private final TicketPool ticketPool;
    private final int releaseInterval; // in milliseconds
    private volatile boolean running = true;

    public Vendor(String vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (running) {
                for (int i = 0; i < ticketsPerRelease; i++) {
                    ticketPool.addTickets("Ticket-" + vendorId + "-" + System.currentTimeMillis());
                }
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
        }
    }

    public void stop() {
        running = false;
    }
}

