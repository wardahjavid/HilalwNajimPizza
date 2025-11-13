package com.pluralsight.domain.topping;

import com.pluralsight.domain.enums.PizzaSize;

public class Sauce extends Topping {

    public Sauce(String name) {
        super(name, false);
    }

    @Override
    public double getPrice(PizzaSize size) {
        return 0; // Sauces are free
    }
}
