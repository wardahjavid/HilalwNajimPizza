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
        System.out.println(GREEN + "Pizza added!" + RESET);
    }
    private void addDrink() {
        System.out.print(CYAN + "Size (1=Small,2=Medium,3=Large): " + RESET);
        DrinkSize size = switch (safeInt()) {
            case 1 -> DrinkSize.SMALL;
            case 2 -> DrinkSize.MEDIUM;
            default -> DrinkSize.LARGE;
        };
        System.out.print(CYAN + "Flavor: " + RESET);
        String flavor = scanner.next().trim();
        order.addItem(new Drink(size, flavor));
        System.out.println(GREEN + "Drink added!" + RESET);
    }
    private void addGarlicKnots() {
        System.out.print(CYAN + "Quantity: " + RESET);
        int qty = safeInt();
        order.addItem(new GarlicKnot(qty));
        System.out.println(GREEN + "✅ Garlic Knots added!" + RESET);
    }
    private void checkout() {
        if (order.getItems().isEmpty()) {
            System.out.println(RED + "⚠️  Cannot checkout an empty order! Add at least one item first." + RESET);
            return;
        }

        System.out.println(YELLOW + "\n══════════════ ORDER SUMMARY ══════════════" + RESET);
        AsciiTable.print(
                "ORDER DETAILS",
                new String[]{"Category", "Label", "Price"},
                order.toRows(order.getItems())
        );

        double total = order.calculateTotal();
        System.out.println(GREEN + "TOTAL: $" + String.format("%.2f", total) + RESET);

        String filePath = receiptManager.saveOrder(order);

        System.out.println(YELLOW + "\nChoose your receipt delivery option:" + RESET);
        System.out.println(CYAN + "1) Print receipt");
        System.out.println("2) Email receipt");
        System.out.println("3) Both (print + email)" + RESET);
        System.out.print(WHITE + "Enter choice: " + RESET);
        int opt = safeInt();

        switch (opt) {
            case 1 -> printReceipt(filePath);
            case 2 -> emailReceipt(filePath);
            case 3 -> { printReceipt(filePath); emailReceipt(filePath); }
            default -> printReceipt(filePath);
        }

        System.out.println(GREEN + "\n Checkout complete! Thank you for choosing PIZZA-licious." + RESET);
    }
    private void printReceipt(String filePath) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String readableTime = now.format(
                java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a")
        );
        String fileTime = now.format(
                java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
        );

        System.out.println(YELLOW + "\n Printing receipt..." + RESET);
        System.out.println(WHITE + "──────────────────────────────────────────────" + RESET);
        System.out.println(CYAN + "Receipt File:" + RESET + " " + filePath);
        System.out.println(CYAN + "File Timestamp:" + RESET + " " + fileTime);
        System.out.println(CYAN + "Printed On:" + RESET + " " + readableTime);
        System.out.println(WHITE + "──────────────────────────────────────────────" + RESET);
        System.out.println(GREEN + " Order printed successfully!" + RESET);
    }
    private void emailReceipt(String filePath) {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String readableTime = now.format(
                java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a")
        );

        System.out.println(YELLOW + "\n📧 Sending receipt via email..." + RESET);
        System.out.print(CYAN + "Enter customer email address: " + RESET);
        String email = scanner.next().trim();

        System.out.println(WHITE + "──────────────────────────────────────────────" + RESET);
        System.out.println(WHITE + "Connecting to mail server..." + RESET);
        System.out.println("Sending receipt file: " + CYAN + filePath + RESET);
        System.out.println("Timestamp: " + readableTime);
        System.out.println(GREEN + "✅ Receipt emailed successfully to " + email + RESET);
        System.out.println(WHITE + "──────────────────────────────────────────────" + RESET);
    }
    private void viewOrderMenu() {
        Predicate<MenuItem> filter = MenuFilters.any();
        Comparator<MenuItem> sorter = MenuSorters.byLabel();
        boolean loop = true;

        while (loop) {
            System.out.println(YELLOW + "\n────────── VIEW / FILTER / SORT ──────────" + RESET);
            System.out.println(CYAN + "1) Filter: Category");
            System.out.println("2) Filter: Price Range");
            System.out.println("3) Filter: Pizza Size/Crust/Topping");
            System.out.println("4) Filter: Drink Size/Flavor");
            System.out.println("5) Filter: Garlic Knot Qty");
            System.out.println("6) Sort: Price/Category/Label");
            System.out.println("7) Show Table");
            System.out.println("0) Back" + RESET);
            System.out.print(WHITE + "Choice: " + RESET);

            int c = safeInt();
            switch (c) {
                case 1 -> {
                    System.out.print(CYAN + "Enter category: " + RESET);
                    filter = filter.and(MenuFilters.category(scanner.next().trim()));
                }
                case 2 -> {
                    System.out.print(CYAN + "Min price: " + RESET); double min = safeDouble();
                    System.out.print(CYAN + "Max price: " + RESET); double max = safeDouble();
                    filter = filter.and(MenuFilters.priceBetween(min, max));
                }
                case 3 -> {
                    System.out.print(CYAN + "Size (1=Personal,2=Medium,3=Large,0=skip): " + RESET);
                    int s = safeInt();
                    if (s != 0)
                        filter = filter.and(MenuFilters.pizzaSize(
                                s==1? PizzaSize.PERSONAL : s==2? PizzaSize.MEDIUM : PizzaSize.LARGE));

                    System.out.print(CYAN + "Crust (thin/regular/thick/cauliflower/skip): " + RESET);
                    String crust = scanner.next().trim();
                    if (!crust.equalsIgnoreCase("skip"))
                        filter = filter.and(MenuFilters.crust(CrustType.valueOf(crust.toUpperCase())));

                    System.out.print(CYAN + "Has topping name (or skip): " + RESET);
                    String tn = scanner.next().trim();
                    if (!tn.equalsIgnoreCase("skip"))
                        filter = filter.and(MenuFilters.hasToppingNamed(tn));
                }
                case 4 -> {
                    System.out.print(CYAN + "Drink size (1=Small,2=Medium,3=Large,0=skip): " + RESET);
                    int ds = safeInt();
                    if (ds != 0)
                        filter = filter.and(MenuFilters.drinkSize(
                                ds==1? DrinkSize.SMALL : ds==2? DrinkSize.MEDIUM : DrinkSize.LARGE));

                    System.out.print(CYAN + "Flavor contains (or skip): " + RESET);
                    String flv = scanner.next().trim();
                    if (!flv.equalsIgnoreCase("skip"))
                        filter = filter.and(MenuFilters.flavorContains(flv));
                }
                case 5 -> {
                    System.out.print(CYAN + "Min knot orders: " + RESET);
                    int q = safeInt();
                    filter = filter.and(MenuFilters.minKnots(q));
                }
                case 6 -> {
                    System.out.println(CYAN + "1=Price Asc, 2=Price Desc, 3=Category, 4=Label" + RESET);
                    sorter = switch (safeInt()) {
                        case 1 -> MenuSorters.byPriceAsc();
                        case 2 -> MenuSorters.byPriceDesc();
                        case 3 -> MenuSorters.byCategory();
                        case 4 -> MenuSorters.byLabel();
                        default -> sorter;
                    };
                }
                case 7 -> AsciiTable.print(
                        "CURRENT ORDER (Filtered/Sorted)",
                        new String[]{"Category", "Label", "Price"},
                        order.toRows(order.query(filter, sorter))
                );
                case 0 -> loop = false;
                default -> System.out.println(RED + "Invalid." + RESET);
            }
        }
    }



