package com.pluralsight.domain;

import com.pluralsight.config.PriceConfig;
import com.pluralsight.domain.enums.DrinkSize;
import com.pluralsight.domain.enums.PizzaSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceConfigTest {

    @Test
    void testPizzaBasePrice() {
        assertEquals(8.50, PriceConfig.basePizzaPrice(PizzaSize.PERSONAL));
        assertTrue(PriceConfig.basePizzaPrice(PizzaSize.LARGE) > 10);
    }

    @Test
    void testDrinkPrices() {
        assertEquals(2.00, PriceConfig.drinkPrice(DrinkSize.SMALL));
        assertEquals(3.00, PriceConfig.drinkPrice(DrinkSize.LARGE));
    }

    @Test
    void testMeatPricing() {
        double p = PriceConfig.meatPrice(PizzaSize.MEDIUM, true);
        assertTrue(p > 2.0);
    }
}
