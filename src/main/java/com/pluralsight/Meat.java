package com.pluralsight;

public class Meat extends Topping {
    private static final double BASE_PERSONAL = 1.00;
    public Meat(String name, boolean isExtra, double v) { super(name, isExtra); }


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
