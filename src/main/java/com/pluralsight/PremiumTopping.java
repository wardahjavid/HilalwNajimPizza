package com.pluralsight;

public abstract class PremiumTopping extends Topping {
    protected double basePrice;

    public PremiumTopping(String name, boolean isExtra, double basePrice) {
        super(name, isExtra);
        this.basePrice = basePrice;
    }
    @Override
    public double getPrice(PizzaSize size) {
        double price = switch (size) {
            case PERSONAL -> basePrice;
            case MEDIUM -> basePrice * 2;
            case LARGE -> basePrice * 3;
        };
        if (isExtra) price += basePrice / 2;
        return price;
    }
}
