package com.pluralsight.domain.pizza;

import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.topping.Cheese;
import com.pluralsight.domain.topping.RegularTopping;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomPizzaTest {

    @Test
    void testBasePrice() {
        CustomPizza p = new CustomPizza(PizzaSize.MEDIUM, CrustType.THIN, false);
        double price = p.getPrice();
        assertTrue(price > 0);
    }

    @Test
    void testAddTopping() {
        CustomPizza p = new CustomPizza(PizzaSize.PERSONAL, CrustType.REGULAR, false);
        p.addTopping(new RegularTopping("Onions"));
        assertEquals(1, p.getToppings().size());
    }

    @Test
    void testPriceWithCheese() {
        CustomPizza p = new CustomPizza(PizzaSize.LARGE, CrustType.THICK, false);
        p.addTopping(new Cheese("Mozzarella", false));
        assertTrue(p.getPrice() > 16.0);
    }

    @Test
    void testStuffedCrustPriceIncrease() {
        CustomPizza p1 = new CustomPizza(PizzaSize.MEDIUM, CrustType.THIN, false);
        CustomPizza p2 = new CustomPizza(PizzaSize.MEDIUM, CrustType.THIN, true);

        assertTrue(p2.getPrice() > p1.getPrice());
    }
}
