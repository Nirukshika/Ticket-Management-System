package com.example.ticket_managment_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String LOG_FILE = "system_logs.txt";
    private static BufferedWriter writer;

    static {
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static synchronized void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logMessage = "[" + timestamp + "] " + message;

        // Write to console
        System.out.println(logMessage);

        // Write to file
        if (writer != null) {
            try {
                writer.write(logMessage);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.err.println("Failed to write log to file: " + e.getMessage());
            }
        }
    }
}

