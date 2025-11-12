package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptFileManager {
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
                bw.write(" PIZZA-licious Point-of-Sale System\n");
                bw.write(" Date & Time: " + readableTimestamp + "\n");
                bw.write(" Receipt ID:  " + fileTimestamp + "\n");
                bw.write("==============================================\n\n");
                bw.write(order.toString());
                bw.write("\n==============================================\n");
                bw.write("Thank you for dining with PIZZA-licious!\n");
                bw.write("==============================================\n");
            }

            System.out.println("\n" + "\u001B[33m" + "🗂️  Receipt saved successfully!" + "\u001B[0m");
            System.out.println(" File: " + filePath);
            System.out.println(" Saved On: " + readableTimestamp);

            appendToDailyLog(order); // BONUS
            return filePath;

        } catch (IOException e) {
            System.out.println("\u001B[31m⚠️ Error saving receipt: " + e.getMessage() + "\u001B[0m");
            return null;
        }
    }

    // BONUS: Append one-line summary to daily log
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

            System.out.println("\u001B[36m📊  Daily sales log updated (" + logPath + ")\u001B[0m");

        } catch (IOException e) {
            System.out.println("\u001B[31m⚠️  Error writing to daily log: " + e.getMessage() + "\u001B[0m");
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
                System.out.println("\u001B[33m\n  Date: " + dateInput + "\u001B[0m");
                System.out.println("\u001B[36m Orders: " + orderCount + "\u001B[0m");
                System.out.println("\u001B[32mTotal Sales: $" + String.format("%.2f", totalSales) + "\u001B[0m");
                System.out.println("\u001B[35m Average Order: $" + String.format("%.2f", average) + "\u001B[0m\n");
            } else {
                System.out.println("\u001B[31m No sales recorded for this date.\u001B[0m");
            }

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31m No log file found for " + dateInput + "\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31m  Error reading log file: " + e.getMessage() + "\u001B[0m");
        }
    }
}
