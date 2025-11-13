package com.pluralsight.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GarlicKnotTest {

    @Test
    void testPriceCalculation() {
        GarlicKnot g = new GarlicKnot(3);
        assertEquals(4.50, g.getPrice(), 0.01);
    }

    @Test
    void testCategory() {
        GarlicKnot g = new GarlicKnot(1);
        assertEquals("Garlic Knot", g.getCategory());
    }
}
