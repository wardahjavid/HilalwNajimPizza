package com.pluralsight.domain;

import com.pluralsight.domain.enums.DrinkSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkTest {

    @Test
    void testDrinkPrice() {
        Drink d = new Drink(DrinkSize.SMALL, "Cola");
        assertEquals(2.00, d.getPrice());
    }

    @Test
    void testDrinkLabel() {
        Drink d = new Drink(DrinkSize.LARGE, "Sprite");
        assertTrue(d.getLabel().contains("LARGE"));
    }
}
