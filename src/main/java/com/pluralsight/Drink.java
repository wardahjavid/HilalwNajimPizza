package com.pluralsight;

public class Drink extends MenuItem {
    private final DrinkSize size;
    private final String flavor;

    public Drink(DrinkSize size, String flavor) {
        super("Drink");
        this.size = size;
        this.flavor = flavor;
    }

    public DrinkSize getSize() { return size; }
    public String getFlavor() { return flavor; }

    @Override
    public double getPrice() {
        return switch (size) {
            case SMALL -> 2.00;
            case MEDIUM -> 2.50;
            case LARGE -> 3.00;
        };
    }

    @Override public String getLabel() { return size + " " + flavor; }
    @Override public String toString() { return getLabel() + " - $" + String.format("%.2f", getPrice()); }
}
