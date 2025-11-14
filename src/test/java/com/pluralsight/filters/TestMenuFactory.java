package com.pluralsight.testutil;

import com.pluralsight.domain.*;
import com.pluralsight.domain.enums.*;
import com.pluralsight.domain.pizza.CustomPizza;
import com.pluralsight.domain.topping.*;

public final class TestMenuFactory {

    private TestMenuFactory() {}

    public static Drink drinkSmallCola() {
        return new Drink(DrinkSize.SMALL, "Cola");
    }

    public static Drink drinkLargeSprite() {
        return new Drink(DrinkSize.LARGE, "Sprite");
    }

    public static GarlicKnot knots(int qty) {
        return new GarlicKnot(qty);
    }

    public static CustomPizza pizzaPersonalRegular() {
        CustomPizza p = new CustomPizza(PizzaSize.PERSONAL, CrustType.REGULAR, false);
        p.addTopping(new RegularTopping("Onions"));
        p.addTopping(new Cheese("Mozzarella", false));
        return p;
    }

    public static CustomPizza pizzaLargeThickMeatball() {
        CustomPizza p = new CustomPizza(PizzaSize.LARGE, CrustType.THICK, true);
        p.addTopping(new Meat("Meatball", true));
        return p;
    }
}
