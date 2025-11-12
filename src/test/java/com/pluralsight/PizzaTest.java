package com.pluralsight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaTest {

    private Pizza smallRegular;
    private Pizza mediumStuffed;

    @BeforeEach
    void setup() {
        smallRegular = new Pizza(PizzaSize.PERSONAL, "Regular", false);
        mediumStuffed = new Pizza(PizzaSize.MEDIUM, "Thin", true);
    }

    @Test
    void testBasePriceBySize() {
        assertEquals(8.50, smallRegular.calculatePrice(), 0.01,
                "8\" pizza should cost $8.50 base");
        assertEquals(12.00 + 1.00, mediumStuffed.calculatePrice(), 0.01,
                "12\" stuffed crust adds $1.00");
    }

    @Test
    void testAddRegularTopping() {
        smallRegular.addTopping(new RegularTopping("Onion", false));
        assertTrue(smallRegular.getToppings().size() > 0,
                "Should add at least one topping");
    }

    @Test
    void testAddPremiumTopping() {
        mediumStuffed.addTopping(new Meat("Pepperoni", false, 1.00));
        double price = mediumStuffed.calculatePrice();
        assertTrue(price > 12.00, "Premium topping should increase price");
    }

    @Test
    void testStuffedCrustAddsCost() {
        double base = smallRegular.calculatePrice();
        mediumStuffed.addTopping(new RegularTopping("Onion", false));
        assertTrue(mediumStuffed.calculatePrice() > base,
                "Stuffed crust must increase cost");
    }

    @Test
    void testToStringContainsSizeAndCrust() {
        String desc = smallRegular.toString();
        assertTrue(desc.contains("PERSONAL") || desc.contains("8"),
                "Description must include pizza size");
        assertTrue(desc.contains("Regular"), "Description must include crust");
    }
}
