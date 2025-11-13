package com.pluralsight.domain.pizza;

import com.pluralsight.config.PriceConfig;
import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.topping.Topping;

public class CustomPizza extends Pizza {

    public CustomPizza(PizzaSize size, CrustType crust, boolean stuffedCrust) {
        super(size, crust, stuffedCrust);
    }

    @Override
    public double getPrice() {
        double total = PriceConfig.basePizzaPrice(size);

        if (stuffedCrust)
            total += PriceConfig.STUFFED_CRUST_UPCHARGE;

        for (Topping t : toppings)
            total += t.getPrice(size);

        return total;
    }
}
