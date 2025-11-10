package com.pluralsight;

public abstract class PremiumTopping extends Topping {
    protected double basePrice;

    public PremiumTopping(String name, boolean isExtra, double basePrice) {
        super(name, isExtra);
        this.basePrice = basePrice;
    }

}
