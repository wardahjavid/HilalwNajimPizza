package com.pluralsight.filters;

import com.pluralsight.domain.*;
import com.pluralsight.domain.enums.*;
import com.pluralsight.domain.pizza.CustomPizza;
import com.pluralsight.domain.topping.*;
import com.pluralsight.testutil.TestMenuFactory;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class MenuFiltersTest {

    @Test
    void testCategoryFilter() {
        Drink item = TestMenuFactory.drinkSmallCola();
        Predicate<MenuItem> f = MenuFilters.category("drink");

        assertTrue(f.test(item));
        assertFalse(f.test(TestMenuFactory.knots(3)));
    }

    @Test
    void testPriceBetween() {
        MenuItem d = TestMenuFactory.drinkSmallCola(); // $2.00
        Predicate<MenuItem> f = MenuFilters.priceBetween(1.0, 2.5);

        assertTrue(f.test(d));
        assertFalse(f.test(TestMenuFactory.knots(10))); // $15.00
    }

    @Test
    void testPizzaSizeFilter() {
        CustomPizza p = TestMenuFactory.pizzaPersonalRegular();
        Predicate<MenuItem> f = MenuFilters.pizzaSize(PizzaSize.PERSONAL);

        assertTrue(f.test(p));
        assertFalse(f.test(TestMenuFactory.drinkSmallCola()));
    }

    @Test
    void testCrustFilter() {
        CustomPizza p = TestMenuFactory.pizzaLargeThickMeatball();
        Predicate<MenuItem> f = MenuFilters.crust(CrustType.THICK);

        assertTrue(f.test(p));
    }

    @Test
    void testHasToppingNamed() {
        CustomPizza p = TestMenuFactory.pizzaPersonalRegular();
        Predicate<MenuItem> f = MenuFilters.hasToppingNamed("onion");

        assertTrue(f.test(p));
        assertFalse(f.test(TestMenuFactory.drinkLargeSprite()));
    }

    @Test
    void testDrinkSizeFilter() {
        Drink d = TestMenuFactory.drinkLargeSprite();
        Predicate<MenuItem> f = MenuFilters.drinkSize(DrinkSize.LARGE);

        assertTrue(f.test(d));
        assertFalse(f.test(TestMenuFactory.knots(2)));
    }

    @Test
    void testFlavorContainsFilter() {
        Drink d = TestMenuFactory.drinkLargeSprite();
        Predicate<MenuItem> f = MenuFilters.flavorContains("spr");

        assertTrue(f.test(d));
    }

    @Test
    void testMinKnots() {
        GarlicKnot g = TestMenuFactory.knots(5);
        Predicate<MenuItem> f = MenuFilters.minKnots(3);

        assertTrue(f.test(g));
        assertFalse(f.test(TestMenuFactory.knots(1)));
    }

    @Test
    void testAnyFilter() {
        Predicate<MenuItem> f = MenuFilters.any();
        assertTrue(f.test(TestMenuFactory.drinkSmallCola()));
    }
}
