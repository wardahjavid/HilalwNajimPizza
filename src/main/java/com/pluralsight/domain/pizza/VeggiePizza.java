package com.pluralsight.domain.pizza;

import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.topping.RegularTopping;
import com.pluralsight.domain.topping.Cheese;

public class VeggiePizza extends SignaturePizza {

    public VeggiePizza() {
        super(PizzaSize.PERSONAL, CrustType.REGULAR, false);
    }

    @Override
    protected void initializeToppings() {
        addTopping(new RegularTopping("Bell Peppers"));
        addTopping(new RegularTopping("Spinach"));
        addTopping(new RegularTopping("Olives"));
        addTopping(new RegularTopping("Onions"));
        addTopping(new RegularTopping("Marinara"));
        addTopping(new Cheese("Mozzarella", false));
    }
}
