package com.pluralsight.domain.pizza;

import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.config.PriceConfig;

public abstract class SignaturePizza extends Pizza {

    protected SignaturePizza(PizzaSize size, CrustType crust, boolean stuffed) {
        super(size, crust, stuffed);
        initializeToppings();
    }

    protected abstract void initializeToppings();

    @Override
    public double getPrice() {
        double total = PriceConfig.signatureBasePrice(size);
        if (stuffedCrust) {
            total += PriceConfig.STUFFED_CRUST_UPCHARGE;
        }
        for (var topping : toppings) {
            total += topping.getPrice(size);
        }
        return total;
    }




    @Override
    public String toString() {
        return "\nSignature Pizza: " + getClass().getSimpleName() +
                " (" + size + ", " + crust +
                (stuffedCrust ? ", Stuffed" : "") + ")" +
                "\nToppings: " + toppings.stream()
                .map(t -> t.getName()).toList() +
                "\nPrice: $" + String.format("%.2f", getPrice());
    }
}
