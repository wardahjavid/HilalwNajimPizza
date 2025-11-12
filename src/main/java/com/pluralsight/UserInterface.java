package com.pluralsight;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserInterface {
    // ANSI colors
    private static final String RESET  = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String WHITE  = "\u001B[37m";

    private final Scanner scanner = new Scanner(System.in);
    private Order order = new Order();
    private final ReceiptFileManager receiptManager = new ReceiptFileManager();

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println(YELLOW + "\n╔══════════════════════════════════════╗");
            System.out.println("║           HalilwNajimPizza  PIZZA-LICIOUS          ║");
            System.out.println("╚══════════════════════════════════════╝" + RESET);
            System.out.println(CYAN + "1) New Order");
            System.out.println("2) View / Filter / Sort Current Order");
            System.out.println("99) Manager / Admin Menu");
            System.out.println("0) Exit" + RESET);
            System.out.print(WHITE + "Enter option: " + RESET);

            int choice = safeInt();
            switch (choice) {
                case 1 -> startOrder();
                case 2 -> viewOrderMenu();
                case 99 -> managerMenu();
                case 0 -> {
                    running = false;
                    System.out.println(GREEN + "Goodbye!" + RESET);
                }
                default -> System.out.println(RED + "Invalid choice, please try again." + RESET);
            }
        }
    }

