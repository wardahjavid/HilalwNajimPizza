package com.pluralsight.domain.pizza;

import com.pluralsight.domain.MenuItem;
import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.topping.Topping;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza extends MenuItem {

    protected PizzaSize size;
    protected CrustType crust;
    protected boolean stuffedCrust;

    protected final List<Topping> toppings = new ArrayList<>();

    protected Pizza(PizzaSize size, CrustType crust, boolean stuffedCrust) {
        super("Pizza");
        this.size = size;
        this.crust = crust;
        this.stuffedCrust = stuffedCrust;
    }

    public PizzaSize getSize() {
        return size;
    }

    public CrustType getCrust() {
        return crust;
    }

    public boolean isStuffedCrust() {
        return stuffedCrust;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void addTopping(Topping t) {
        toppings.add(t);
    }
    @Override
    public double getPrice() {

        double base = switch (size) {
            case PERSONAL -> 8.50;
            case MEDIUM   -> 12.00;
            case LARGE    -> 16.50;
        };

        double toppingTotal = toppings.stream()
                .mapToDouble(t -> t.getPrice(size))
                .sum();

        double stuffedCost = stuffedCrust ? 2.00 : 0.00;

        return base + toppingTotal + stuffedCost;
    }


    @Override
    public String getLabel() {
        return size + " " + crust + (stuffedCrust ? " (Stuffed)" : "");
    }
}
