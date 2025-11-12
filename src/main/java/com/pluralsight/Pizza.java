package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Pizza extends MenuItem {
    private static final double BASE_PERSONAL = 8.50;
    private static final double BASE_MEDIUM   = 12.00;
    private static final double BASE_LARGE    = 16.50;

    private final PizzaSize size;
    private final CrustType crust;
    private final boolean stuffedCrust;
    private final List<Topping> toppings = new ArrayList<>();

    public Pizza(PizzaSize size, CrustType crust, boolean stuffedCrust) {
        super("Pizza");
        this.size = size;
        this.crust = crust;
        this.stuffedCrust = stuffedCrust;
    }

    public PizzaSize getSize() { return size; }
    public CrustType getCrust() { return crust; }
    public boolean isStuffedCrust() { return stuffedCrust; }
    public List<Topping> getToppings() { return toppings; }

    public void addTopping(Topping topping) { toppings.add(topping); }

    @Override
    public double getPrice() {
        double base = switch (size) {
            case PERSONAL -> BASE_PERSONAL;
            case MEDIUM   -> BASE_MEDIUM;
            case LARGE    -> BASE_LARGE;
        };
        double addOns = 0;
        for (Topping t : toppings) addOns += t.getPrice(size);
        return base + addOns;
    }

    @Override
    public String getLabel() {
        return size + " " + crust + (stuffedCrust ? " (stuffed)" : "");
    }

    @Override
    public String toString() {
        return getLabel() + " | " + toppings + " | $" + String.format("%.2f", getPrice());
    }

    @Override
    public double calculatePrice() {
        return 0;
    }
}
