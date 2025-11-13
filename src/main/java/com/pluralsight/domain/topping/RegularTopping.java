package com.pluralsight.domain.topping;

import com.pluralsight.domain.enums.PizzaSize;

public class RegularTopping extends Topping {

    public RegularTopping(String name) {
        super(name, false);
    }

    @Override
    public double getPrice(PizzaSize size) {
        return 0; // free
    }
}
