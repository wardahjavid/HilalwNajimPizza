package com.pluralsight;

public class Cheese extends Topping {
    private static final double BASE_PERSONAL = 0.75;
    public Cheese(String name, boolean isExtra) { super(name, isExtra); }

    @Override
    public double getPrice(PizzaSize size) {
        double base = switch (size) {
            case PERSONAL -> BASE_PERSONAL;
            case MEDIUM -> BASE_PERSONAL * 2;
            case LARGE -> BASE_PERSONAL * 3;
        };
        return base + (isExtra ? BASE_PERSONAL / 2 : 0);
    }
}
