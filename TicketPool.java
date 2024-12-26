package com.example.ticket_managment_system;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TicketPool {
    private final PriorityQueue<String> tickets = new PriorityQueue<>(Comparator.reverseOrder());

    public synchronized void addTickets(String ticket) {
        tickets.add(ticket);
        Logger.log("Ticket added: " + ticket);
    }

    public synchronized String removeTicket() {
        if (!tickets.isEmpty()) {
            String ticket = tickets.poll();
            Logger.log("Ticket purchased: " + ticket);
            return ticket;
        } else {
            Logger.log("Attempt to purchase ticket failed: No tickets available.");
            return null;
        }
    }

    public int getAvailableTickets() {
        return tickets.size();
    }
}
