package com.pluralsight.domain.topping;

import com.pluralsight.config.PriceConfig;
import com.pluralsight.domain.enums.PizzaSize;

public class Meat extends Topping {

    public Meat(String name, boolean extra) {
        super(name, extra);
    }

    @Override
    public double getPrice(PizzaSize size) {
        return PriceConfig.meatPrice(size, extra);
    }
}
