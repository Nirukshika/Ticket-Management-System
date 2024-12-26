package com.example.ticket_managment_system;

import java.util.Scanner;

public class TicketingCLI {
    private static BackendManager backendManager = new BackendManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Real-Time Ticketing System!");
        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Configure system");
            System.out.println("2. Start ticketing system");
            System.out.println("3. Stop ticketing system");
            System.out.println("4. Show available tickets");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    configureSystem(scanner);
                    break;
                case 2:
                    startSystem();
                    break;
                case 3:
                    stopSystem();
                    break;
                case 4:
                    showAvailableTickets();
                    break;
                case 5:
                    running = false;
                    stopSystem();
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void configureSystem(Scanner scanner) {
        System.out.print("Enter number of vendors: ");
        int vendorCount = scanner.nextInt();

        System.out.print("Enter tickets per release (per vendor): ");
        int ticketsPerRelease = scanner.nextInt();

        System.out.print("Enter vendor release interval (ms): ");
        int releaseInterval = scanner.nextInt();

        System.out.print("Enter number of customers: ");
        int customerCount = scanner.nextInt();

        System.out.print("Enter customer retrieval interval (ms): ");
        int retrievalInterval = scanner.nextInt();

        backendManager.configureSystem(vendorCount, ticketsPerRelease, releaseInterval, customerCount, retrievalInterval);
        System.out.println("System configured successfully!");
    }

    private static void startSystem() {
        try {
            backendManager.startSystem();
            System.out.println("Ticketing system started!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void stopSystem() {
        backendManager.stopSystem();
        System.out.println("Ticketing system stopped!");
    }

    private static void showAvailableTickets() {
        System.out.println("Available tickets: " + backendManager.getAvailableTickets());
    }
}
