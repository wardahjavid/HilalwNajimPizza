package com.pluralsight.ui;

import com.pluralsight.domain.*;
import com.pluralsight.domain.enums.*;
import com.pluralsight.domain.pizza.CustomPizza;
import com.pluralsight.domain.pizza.MargheritaPizza;
import com.pluralsight.domain.pizza.Pizza;
import com.pluralsight.domain.pizza.VeggiePizza;
import com.pluralsight.domain.topping.Cheese;
import com.pluralsight.domain.topping.Meat;
import com.pluralsight.domain.topping.RegularTopping;
import com.pluralsight.domain.topping.Sauce;
import com.pluralsight.filters.MenuFilters;
import com.pluralsight.filters.MenuSorters;
import com.pluralsight.io.AsciiTable;
import com.pluralsight.io.ReceiptFileManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserInterface {

    private static final String RESET  = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String WHITE  = "\u001B[37m";

    private final Scanner scanner = new Scanner(System.in);
    private final ReceiptFileManager receiptManager = new ReceiptFileManager();

    private Order currentOrder = new Order();

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println(YELLOW + "\n╔══════════════════════════════════════╗");
            System.out.println("║          HilalwNajimPizza Point of Sale System    ║");
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
        currentOrder = new Order();
        boolean ordering = true;

        while (ordering) {
            System.out.println(YELLOW + "\n──────────── ORDER MENU ────────────" + RESET);
            System.out.println(CYAN + "1) Add Custom Pizza");
            System.out.println("2) Add Signature Pizza");
            System.out.println("3) Add Drink");
            System.out.println("4) Add Garlic Knots");
            System.out.println("5) Checkout");
            System.out.println("0) Cancel Order" + RESET);
            System.out.print(WHITE + "Choice: " + RESET);

            int choice = safeInt();
            switch (choice) {
                case 1 -> addCustomPizza();
                case 2 -> addSignaturePizza();
                case 3 -> addDrink();
                case 4 -> addGarlicKnots();
                case 5 -> checkout();
                case 0 -> {
                    ordering = false;
                    System.out.println(RED + "Order cancelled." + RESET);
                }
                default -> System.out.println(RED + "Invalid option." + RESET);
            }
        }
    }

    private void addCustomPizza() {
        System.out.print(CYAN + "Size (1=Personal,2=Medium,3=Large): " + RESET);
        int sizeChoice = safeInt();
        PizzaSize size = switch (sizeChoice) {
            case 1 -> PizzaSize.PERSONAL;
            case 2 -> PizzaSize.MEDIUM;
            default -> PizzaSize.LARGE;
        };

        System.out.print(CYAN + "Crust (thin/regular/thick/cauliflower): " + RESET);
        String crustInput = scanner.nextLine().trim();
        CrustType crust = CrustType.valueOf(crustInput.toUpperCase());

        System.out.print(CYAN + "Stuffed crust? (y/n): " + RESET);
        boolean stuffed = scanner.nextLine().trim().equalsIgnoreCase("y");

        CustomPizza pizza = new CustomPizza(size, crust, stuffed);

        System.out.print(CYAN + "Add toppings? (y/n): " + RESET);
        String ans = scanner.nextLine().trim();
        while (ans.equalsIgnoreCase("y")) {
            System.out.println(YELLOW + "\n AVAILABLE TOPPINGS GUIDE" + RESET);
            System.out.println(WHITE + "Regular (included): " + CYAN +
                    "Onions, Mushrooms, Bell Peppers, Olives, Tomatoes, Spinach, Basil, Pineapple, Anchovies" + RESET);
            System.out.println(WHITE + "Cheese (premium $0.75 base): " + CYAN +
                    "Mozzarella, Parmesan, Ricotta, Goat Cheese, Buffalo" + RESET);
            System.out.println(WHITE + "Meat (premium $1.00 base): " + CYAN +
                    "Pepperoni, Sausage, Ham, Bacon, Chicken, Meatball" + RESET);
            System.out.println(WHITE + "Sauces (included): " + CYAN +
                    "Marinara, Alfredo, Pesto, BBQ, Buffalo, Olive Oil" + RESET);
            System.out.println(WHITE + "──────────────────────────────────────────────" + RESET);

            System.out.print(CYAN + "Type (regular/cheese/meat/sauce): " + RESET);
            String type = scanner.nextLine().trim().toLowerCase();

            System.out.print(CYAN + "Topping name (e.g. Mozzarella, Pepperoni, Marinara): " + RESET);
            String name = scanner.nextLine().trim();

            switch (type) {
                case "cheese" -> {
                    System.out.print(CYAN + "Extra cheese? (y/n): " + RESET);
                    boolean extra = scanner.nextLine().trim().equalsIgnoreCase("y");
                    pizza.addTopping(new Cheese(name, extra));
                }
                case "meat" -> {
                    System.out.print(CYAN + "Extra meat? (y/n): " + RESET);
                    boolean extra = scanner.nextLine().trim().equalsIgnoreCase("y");
                    pizza.addTopping(new Meat(name, extra));
                }
                case "sauce" -> pizza.addTopping(new Sauce(name));
                default -> pizza.addTopping(new RegularTopping(name));
            }

            System.out.print(CYAN + "Add another topping? (y/n): " + RESET);
            ans = scanner.nextLine().trim();
        }

        currentOrder.addItem(pizza);
        System.out.println(GREEN + "Custom pizza added to order!" + RESET);
    }

    private void addSignaturePizza() {
        System.out.println(CYAN + "\n Signature Pizzas" + RESET);
        System.out.println("1) Margherita");
        System.out.println("2) Veggie");
        System.out.print(WHITE + "Choose a pizza (1-2): " + RESET);
        String choice = scanner.nextLine().trim();

        Pizza pizza = switch (choice) {
            case "1" -> new MargheritaPizza();
            case "2" -> new VeggiePizza();
            default -> null;
        };

        if (pizza != null) {
            System.out.println(pizza);
            currentOrder.addItem(pizza);
            System.out.printf("%s%s added to order!%s%n", GREEN, pizza.getClass().getSimpleName(), RESET);
        } else {
            System.out.println(RED + "Invalid selection." + RESET);
        }
    }

    private void addDrink() {
        System.out.print(CYAN + "Size (1=Small,2=Medium,3=Large): " + RESET);
        int sizeChoice = safeInt();
        DrinkSize size = switch (sizeChoice) {
            case 1 -> DrinkSize.SMALL;
            case 2 -> DrinkSize.MEDIUM;
            default -> DrinkSize.LARGE;
        };

        System.out.print(CYAN + "Flavor: " + RESET);
        String flavor = scanner.nextLine().trim();

        currentOrder.addItem(new Drink(size, flavor));
        System.out.println(GREEN + "Drink added!" + RESET);
    }

    private void addGarlicKnots() {
        System.out.print(CYAN + "Quantity: " + RESET);
        int qty = safeInt();
        currentOrder.addItem(new GarlicKnot(qty));
        System.out.println(GREEN + "Garlic Knots added!" + RESET);
    }

    private void checkout() {
        if (currentOrder.isEmpty()) {
            System.out.println(RED + "Cannot checkout an empty order!" + RESET);
            return;
        }

        AsciiTable.print(
                "ORDER DETAILS",
                new String[]{"Category", "Label", "Price"},
                currentOrder.toRows(currentOrder.getItems())
        );

        double total = currentOrder.calculateTotal();
        System.out.println(GREEN + "TOTAL: $" + String.format("%.2f", total) + RESET);

        String filePath = receiptManager.saveOrder(currentOrder);

        System.out.println(YELLOW + "\nChoose your receipt delivery option:" + RESET);
        System.out.println(CYAN + "1) Print receipt");
        System.out.println("2) Email receipt");
        System.out.println("3) Both (print + email)" + RESET);
        System.out.print(WHITE + "Enter choice: " + RESET);
        int opt = safeInt();

        switch (opt) {
            case 1 -> printReceipt(filePath);
            case 2 -> emailReceipt(filePath);
            case 3 -> {
                printReceipt(filePath);
                emailReceipt(filePath);
            }
            default -> printReceipt(filePath);
        }

        System.out.println(GREEN + "\nCheckout complete! Thank you for choosing PIZZA-licious." + RESET);
    }

    private void printReceipt(String filePath) {
        LocalDateTime now = LocalDateTime.now();
        String readableTime = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a"));
        System.out.println(YELLOW + "\n Printing receipt..." + RESET);
        System.out.println(WHITE + "File: " + filePath);
        System.out.println(CYAN + "Printed On: " + readableTime + RESET);
    }

    private void emailReceipt(String filePath) {
        System.out.print(CYAN + "Enter customer email address: " + RESET);
        String email = scanner.nextLine().trim();
        System.out.println(WHITE + "Sending receipt file: " + filePath + RESET);
        System.out.println(GREEN + "Receipt emailed successfully to " + email + RESET);
    }

    private void viewOrderMenu() {
        Predicate<MenuItem> filter = MenuFilters.any();
        Comparator<MenuItem> sorter = MenuSorters.byLabel();
        boolean loop = true;

        while (loop) {
            System.out.println(YELLOW + "\n────────── VIEW / FILTER / SORT ──────────" + RESET);
            System.out.println(CYAN + "1) Filter: Category");
            System.out.println("2) Filter: Price Range");
            System.out.println("3) Filter: Pizza Size / Crust / Topping");
            System.out.println("4) Filter: Drink Size / Flavor");
            System.out.println("5) Filter: Garlic Knot Qty");
            System.out.println("6) Sort: Price / Category / Label");
            System.out.println("7) Show Table");
            System.out.println("0) Back" + RESET);
            System.out.print(WHITE + "Choice: " + RESET);

            int c = safeInt();
            switch (c) {
                case 1 -> {
                    System.out.print(CYAN + "Enter category: " + RESET);
                    String cat = scanner.nextLine().trim();
                    filter = filter.and(MenuFilters.category(cat));
                }
                case 2 -> {
                    System.out.print(CYAN + "Min price: " + RESET);
                    double min = safeDouble();
                    System.out.print(CYAN + "Max price: " + RESET);
                    double max = safeDouble();
                    filter = filter.and(MenuFilters.priceBetween(min, max));
                }
                case 3 -> {
                    System.out.print(CYAN + "Size (1=Personal,2=Medium,3=Large,0=skip): " + RESET);
                    int s = safeInt();
                    if (s != 0) {
                        PizzaSize ps = (s == 1) ? PizzaSize.PERSONAL :
                                (s == 2) ? PizzaSize.MEDIUM : PizzaSize.LARGE;
                        filter = filter.and(MenuFilters.pizzaSize(ps));
                    }

                    System.out.print(CYAN + "Crust (thin/regular/thick/cauliflower/skip): " + RESET);
                    String crustIn = scanner.nextLine().trim();
                    if (!crustIn.equalsIgnoreCase("skip")) {
                        filter = filter.and(MenuFilters.crust(CrustType.valueOf(crustIn.toUpperCase())));
                    }

                    System.out.print(CYAN + "Has topping name (or skip): " + RESET);
                    String tn = scanner.nextLine().trim();
                    if (!tn.equalsIgnoreCase("skip")) {
                        filter = filter.and(MenuFilters.hasToppingNamed(tn));
                    }
                }
                case 4 -> {
                    System.out.print(CYAN + "Drink size (1=Small,2=Medium,3=Large,0=skip): " + RESET);
                    int ds = safeInt();
                    if (ds != 0) {
                        DrinkSize dsz = (ds == 1) ? DrinkSize.SMALL :
                                (ds == 2) ? DrinkSize.MEDIUM : DrinkSize.LARGE;
                        filter = filter.and(MenuFilters.drinkSize(dsz));
                    }

                    System.out.print(CYAN + "Flavor contains (or skip): " + RESET);
                    String flv = scanner.nextLine().trim();
                    if (!flv.equalsIgnoreCase("skip")) {
                        filter = filter.and(MenuFilters.flavorContains(flv));
                    }
                }
                case 5 -> {
                    System.out.print(CYAN + "Min knot orders: " + RESET);
                    int q = safeInt();
                    filter = filter.and(MenuFilters.minKnots(q));
                }
                case 6 -> {
                    System.out.println(CYAN + "1=Price Asc, 2=Price Desc, 3=Category, 4=Label" + RESET);
                    int sChoice = safeInt();
                    sorter = switch (sChoice) {
                        case 1 -> MenuSorters.byPriceAsc();
                        case 2 -> MenuSorters.byPriceDesc();
                        case 3 -> MenuSorters.byCategory();
                        case 4 -> MenuSorters.byLabel();
                        default -> sorter;
                    };
                }
                case 7 -> {
                    List<MenuItem> filtered = currentOrder.query(filter, sorter);
                    AsciiTable.print(
                            "CURRENT ORDER (Filtered/Sorted)",
                            new String[]{"Category", "Label", "Price"},
                            currentOrder.toRows(filtered)
                    );
                }
                case 0 -> loop = false;
                default -> System.out.println(RED + "Invalid." + RESET);
            }
        }
    }

    private void managerMenu() {
        boolean quit = false;

        while (!quit) {
            System.out.println(YELLOW + "\n────────────── MANAGER MENU ──────────────" + RESET);
            System.out.println(CYAN + "1) View Daily Sales Summary");
            System.out.println("2) View Today's Summary (auto)");
            System.out.println("0) Return to Main Menu" + RESET);
            System.out.print(WHITE + "Enter option: " + RESET);

            int choice = safeInt();
            switch (choice) {
                case 1 -> {
                    System.out.print(CYAN + "Enter date (YYYYMMDD): " + RESET);
                    String dateInput = scanner.nextLine().trim();
                    receiptManager.showDailySummary(dateInput);
                }
                case 2 -> {
                    String today = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    System.out.println(CYAN + "\n Automatically showing today's report: " + today + RESET);
                    receiptManager.showDailySummary(today);
                }
                case 0 -> quit = true;
                default -> System.out.println(RED + "Invalid choice. Try again." + RESET);
            }
        }
    }

    private int safeInt() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.print(RED + "Enter a valid number: " + RESET);
            }
        }
    }

    private double safeDouble() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.print(RED + "Enter a valid decimal number: " + RESET);
            }
        }
    }
}
