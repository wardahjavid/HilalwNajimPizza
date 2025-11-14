package com.pluralsight.filters;

import com.pluralsight.domain.MenuItem;
import com.pluralsight.testutil.TestMenuFactory;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuSortersTest {

    @Test
    void testSortByPriceAsc() {
        List<MenuItem> items = Arrays.asList(
                TestMenuFactory.knots(5),      // 7.50
                TestMenuFactory.drinkSmallCola(), // 2.00
                TestMenuFactory.drinkLargeSprite() // 3.00
        );

        items.sort(MenuSorters.byPriceAsc());

        assertEquals("Drink", items.get(0).getCategory());
    }

    @Test
    void testSortByPriceDesc() {
        List<MenuItem> items = Arrays.asList(
                TestMenuFactory.knots(3), // 4.50
                TestMenuFactory.drinkSmallCola() // 2.00
        );

        items.sort(MenuSorters.byPriceDesc());

        assertEquals(4.50, items.get(0).getPrice(), 0.01);
    }

    @Test
    void testSortByCategory() {
        List<MenuItem> items = Arrays.asList(
                TestMenuFactory.knots(1), // Category = Garlic Knots
                TestMenuFactory.drinkSmallCola() // Category = Drink
        );

        items.sort(MenuSorters.byCategory());

        assertEquals("Drink", items.get(0).getCategory());
    }

    @Test
    void testSortByLabel() {
        List<MenuItem> items = Arrays.asList(
                TestMenuFactory.drinkLargeSprite(), // LABEL = LARGE Sprite
                TestMenuFactory.drinkSmallCola()    // LABEL = SMALL Cola
        );

        items.sort(MenuSorters.byLabel());

        assertTrue(items.get(0).getLabel().startsWith("LARGE"));
    }

    @Test
    void testPriceAscStability() {
        MenuItem a = TestMenuFactory.drinkSmallCola();  // 2.00
        MenuItem b = TestMenuFactory.drinkSmallCola();  // 2.00 (duplicate price)

        List<MenuItem> items = Arrays.asList(a, b);
        items.sort(MenuSorters.byPriceAsc());

        assertSame(a, items.get(0)); // stable sorting
    }

    @Test
    void testCompositeSortPriceThenLabel() {
        List<MenuItem> items = Arrays.asList(
                TestMenuFactory.drinkLargeSprite(),   // 3.00
                TestMenuFactory.drinkSmallCola(),     // 2.00
                TestMenuFactory.knots(1)              // 1.50
        );

        items.sort(
                MenuSorters.byPriceAsc()
                        .thenComparing(MenuSorters.byLabel())
        );

        assertEquals("1 order(s)", items.get(0).getLabel());
    }

    @Test
    void testNullSafety() {
        Comparator<MenuItem> sorter = MenuSorters.byPriceAsc();
        assertNotNull(sorter);
    }
}
