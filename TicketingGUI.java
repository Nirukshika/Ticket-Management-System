package com.example.ticket_managment_system;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TicketingGUI extends Application {

    private BackendManager backendManager = new BackendManager();

    private IntegerProperty vendorCount = new SimpleIntegerProperty(2);
    private IntegerProperty ticketsPerRelease = new SimpleIntegerProperty(5);
    private IntegerProperty releaseInterval = new SimpleIntegerProperty(1000);
    private IntegerProperty customerCount = new SimpleIntegerProperty(3);
    private IntegerProperty retrievalInterval = new SimpleIntegerProperty(800);

    private Label ticketCountLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Real-Time Ticketing System with Advanced Features");

        // Configuration Input Fields
        TextField vendorCountField = createBoundField(vendorCount, "Number of Vendors");
        TextField ticketsPerReleaseField = createBoundField(ticketsPerRelease, "Tickets per Release");
        TextField releaseIntervalField = createBoundField(releaseInterval, "Vendor Release Interval (ms)");
        TextField customerCountField = createBoundField(customerCount, "Number of Customers");
        TextField retrievalIntervalField = createBoundField(retrievalInterval, "Customer Retrieval Interval (ms)");

        GridPane configPane = new GridPane();
        configPane.setHgap(10);
        configPane.setVgap(10);
        configPane.add(new Label("Configuration:"), 0, 0, 2, 1);
        configPane.addRow(1, new Label("Number of Vendors:"), vendorCountField);
        configPane.addRow(2, new Label("Tickets per Release:"), ticketsPerReleaseField);
        configPane.addRow(3, new Label("Vendor Release Interval (ms):"), releaseIntervalField);
        configPane.addRow(4, new Label("Number of Customers:"), customerCountField);
        configPane.addRow(5, new Label("Customer Retrieval Interval (ms):"), retrievalIntervalField);

        // Ticket Count Display
        ticketCountLabel = new Label("Available Tickets: 0");

        // Control Buttons
        Button startButton = new Button("Start System");
        Button stopButton = new Button("Stop System");
        stopButton.setDisable(true);

        startButton.setOnAction(e -> startSystem(startButton, stopButton));
        stopButton.setOnAction(e -> stopSystem(startButton, stopButton));

        HBox controlButtons = new HBox(10, startButton, stopButton);

        VBox mainLayout = new VBox(15, configPane, ticketCountLabel, controlButtons);
        mainLayout.setSpacing(15);

        primaryStage.setScene(new Scene(mainLayout, 500, 400));
        primaryStage.show();
    }

    private TextField createBoundField(IntegerProperty property, String label) {
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(property, new javafx.util.converter.NumberStringConverter());
        return textField;
    }

    private void startSystem(Button startButton, Button stopButton) {
        backendManager.configureSystem(vendorCount.get(), ticketsPerRelease.get(), releaseInterval.get(),
                customerCount.get(), retrievalInterval.get());
        backendManager.startSystem();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        updateTicketCountPeriodically();
    }

    private void stopSystem(Button startButton, Button stopButton) {
        backendManager.stopSystem();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        ticketCountLabel.setText("Available Tickets: " + backendManager.getAvailableTickets());
    }

    private void updateTicketCountPeriodically() {
        Thread updaterThread = new Thread(() -> {
            while (backendManager.isRunning()) {
                Platform.runLater(() -> ticketCountLabel.setText("Available Tickets: " + backendManager.getAvailableTickets()));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        updaterThread.setDaemon(true);
        updaterThread.start();
    }
}

