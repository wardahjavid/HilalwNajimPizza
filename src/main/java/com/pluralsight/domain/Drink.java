package com.pluralsight.domain;

import com.pluralsight.config.PriceConfig;
import com.pluralsight.domain.enums.DrinkSize;

public class Drink extends MenuItem {

    private final DrinkSize size;
    private final String flavor;

    public Drink(DrinkSize size, String flavor) {
        super("Drink");
        this.size = size;
        this.flavor = flavor;
    }

    @Override
    public String getLabel() {
        return size + " " + flavor;
    }

    @Override
    public double getPrice() {
        return PriceConfig.drinkPrice(size);
    }

    public DrinkSize getSize() {
        return size;
    }

    public String getFlavor() {
        return flavor;
    }
}
