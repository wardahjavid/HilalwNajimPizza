package com.pluralsight.domain;

import com.pluralsight.domain.enums.DrinkSize;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testAddItem() {
        Order order = new Order();
        order.addItem(new Drink(DrinkSize.SMALL, "Cola"));
        assertEquals(1, order.getItems().size());
    }

    @Test
    void testCalculateTotal() {
        Order o = new Order();
        o.addItem(new Drink(DrinkSize.MEDIUM, "Sprite"));
        o.addItem(new GarlicKnot(2));

        assertTrue(o.calculateTotal() > 4.0);
    }

    @Test
    void testQueryFilter() {
        Order o = new Order();
        o.addItem(new Drink(DrinkSize.SMALL, "Cola"));

        var result = o.query(it -> it.getCategory().equals("Drink"), null);
        assertEquals(1, result.size());
    }

    @Test
    void testToRows() {
        Order o = new Order();
        o.addItem(new Drink(DrinkSize.LARGE, "Cola"));

        List<String[]> rows = o.toRows(o.getItems());
        assertEquals(3, rows.get(0).length);
    }
}
