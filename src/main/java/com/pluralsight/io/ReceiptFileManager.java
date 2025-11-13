package com.pluralsight.io;

import com.pluralsight.domain.Order;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptFileManager {

    private static final boolean ENABLE_COLORS = true;

    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String RESET  = "\u001B[0m";


    private static String color(String ansi) {
        return ENABLE_COLORS ? ansi : "";
    }

    private final String directory = "receipts/";

    public String saveOrder(Order order) {
        try {
            File folder = new File(directory);
            if (!folder.exists()) folder.mkdirs();

            LocalDateTime now = LocalDateTime.now();
            String fileTimestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String readableTimestamp = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a"));

            String fileName = "receipt-" + fileTimestamp + ".txt";
            String filePath = directory + fileName;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write("==============================================\n");
                bw.write("  HilalwNajimPizza Point-of-Sale System\n");
                bw.write(" Date & Time: " + readableTimestamp + "\n");
                bw.write(" Receipt ID:  " + fileTimestamp + "\n");
                bw.write("==============================================\n");
                bw.write(order.toString());
                bw.write("\n==============================================\n");
                bw.write("Thank you for dining with HilalwNajimPizza!\n");
                bw.write("==============================================\n");
            }

            System.out.println();
            System.out.println(color(YELLOW) + " Receipt saved successfully!" + color(RESET));
            System.out.println(" File: " + filePath);
            System.out.println(" Saved On: " + readableTimestamp);

            appendToDailyLog(order);

            return filePath;

        } catch (IOException e) {
            System.out.println(color(RED) + " Error saving receipt: " + e.getMessage() + color(RESET));
            return null;
        }
    }

    public void appendToDailyLog(Order order) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String dateFile = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String logPath = directory + "receipt-log-" + dateFile + ".txt";
            String timeStamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            double total = order.calculateTotal();
            int itemCount = order.getItems().size();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logPath, true))) {
                bw.write(timeStamp + " | Total: $" + String.format("%.2f", total)
                        + " | Items: " + itemCount);
                bw.newLine();
            }

            System.out.println(color(CYAN) + " Daily sales log updated (" + logPath + ")" + color(RESET));

        } catch (IOException e) {
            System.out.println(color(RED) + " Error writing to daily log: " + e.getMessage() + color(RESET));
        }
    }

    public void showDailySummary(String dateInput) {
        String logPath = directory + "receipt-log-" + dateInput + ".txt";
        double totalSales = 0.0;
        int orderCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(logPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                int totalIndex = line.indexOf("Total: $");
                if (totalIndex != -1) {
                    String valueStr = line.substring(totalIndex + 8).split("\\|")[0].trim();
                    try {
                        double value = Double.parseDouble(valueStr);
                        totalSales += value;
                        orderCount++;
                    } catch (NumberFormatException ignored) {}
                }
            }

            if (orderCount > 0) {
                double average = totalSales / orderCount;

                System.out.println();
                System.out.println(color(YELLOW) + "Date: " + dateInput + color(RESET));
                System.out.println(color(CYAN)   + "Orders: " + orderCount + color(RESET));
                System.out.println(color(GREEN)  + "Total Sales: $" + String.format("%.2f", totalSales) + color(RESET));
                System.out.println(color(YELLOW) + "Average Order: $" + String.format("%.2f", average) + color(RESET));
                System.out.println();

            } else {
                System.out.println(color(RED) + " No sales recorded for this date." + color(RESET));
            }

        } catch (FileNotFoundException e) {
            System.out.println(color(RED) + "No log file found for " + dateInput + color(RESET));
        } catch (IOException e) {
            System.out.println(color(RED) + "Error reading log file: " + e.getMessage() + color(RESET));
        }
    }
}

