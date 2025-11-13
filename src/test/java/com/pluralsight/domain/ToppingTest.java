package com.pluralsight.domain;

import com.pluralsight.domain.pizza.MargheritaPizza;
import com.pluralsight.domain.pizza.VeggiePizza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignaturePizzaTest {

    @Test
    void testMargheritaHasToppings() {
        MargheritaPizza p = new MargheritaPizza();
        assertFalse(p.getToppings().isEmpty());
    }

    @Test
    void testVeggieHasToppings() {
        VeggiePizza p = new VeggiePizza();
        assertTrue(p.getToppings().size() >= 3);
    }

    @Test
    void testSignaturePrice() {
        MargheritaPizza p = new MargheritaPizza();
        assertTrue(p.getPrice() > 0);
    }
}
