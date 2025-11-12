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
    private void startOrder() {
        order = new Order();
        boolean ordering = true;

        while (ordering) {
            System.out.println(YELLOW + "\n──────────── ORDER MENU ────────────" + RESET);
            System.out.println(CYAN + "1) Add Pizza");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Garlic Knots");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order" + RESET);
            System.out.print(WHITE + "Choice: " + RESET);

            int choice = safeInt();
            switch (choice) {
                case 1 -> addPizza();
                case 2 -> addDrink();
                case 3 -> addGarlicKnots();
                case 4 -> checkout();
                case 0 -> {
                    ordering = false;
                    System.out.println(RED + "Order cancelled." + RESET);
                }
                default -> System.out.println(RED + "Invalid option." + RESET);
            }
        }
    }
    private void addPizza() {
        System.out.print(CYAN + "Size (1=Personal,2=Medium,3=Large): " + RESET);
        PizzaSize size = switch (safeInt()) {
            case 1 -> PizzaSize.PERSONAL;
            case 2 -> PizzaSize.MEDIUM;
            default -> PizzaSize.LARGE;
        };

        System.out.print(CYAN + "Crust (thin/regular/thick/cauliflower): " + RESET);
        CrustType crust = CrustType.valueOf(scanner.next().trim().toUpperCase());

        System.out.print(CYAN + "Stuffed crust? (true/false): " + RESET);
        boolean stuffed = scanner.nextBoolean();

        Pizza pizza = new Pizza(size, crust, stuffed);

        System.out.print(CYAN + "Add toppings? y/n: " + RESET);
        while (scanner.next().trim().equalsIgnoreCase("y")) {
            System.out.print(CYAN + "Type (regular/cheese/meat): " + RESET);
            String type = scanner.next().trim().toLowerCase();

            System.out.print(CYAN + "Name: " + RESET);
            String name = scanner.next().trim();

            switch (type) {
                case "cheese" -> {
                    System.out.print(CYAN + "Extra? y/n: " + RESET);
                    boolean ex = scanner.next().trim().equalsIgnoreCase("y");
                    pizza.addTopping(new Cheese(name, ex));
                }
                case "meat" -> {
                    System.out.print(CYAN + "Extra? y/n: " + RESET);
                    boolean ex = scanner.next().trim().equalsIgnoreCase("y");
                    pizza.addTopping(new Meat(name, ex));
                }
                default -> pizza.addTopping(new RegularTopping(name));
            }
            System.out.print(CYAN + "Add another topping? y/n: " + RESET);
        }

        order.addItem(pizza);
        System.out.println(GREEN + "✅ Pizza added!" + RESET);
    }



