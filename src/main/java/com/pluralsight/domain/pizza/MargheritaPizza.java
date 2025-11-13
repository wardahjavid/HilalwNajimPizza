package com.pluralsight.domain.pizza;

import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.topping.Cheese;
import com.pluralsight.domain.topping.RegularTopping;

public class MargheritaPizza extends SignaturePizza {

    public MargheritaPizza() {
        super(PizzaSize.MEDIUM, CrustType.REGULAR, false);
    }

    @Override
    protected void initializeToppings() {
        addTopping(new RegularTopping("Tomatoes"));
        addTopping(new RegularTopping("Basil"));
        addTopping(new Cheese("Mozzarella", false));
        addTopping(new RegularTopping("Marinara"));
        addTopping(new RegularTopping("Olive Oil"));
    }
}
