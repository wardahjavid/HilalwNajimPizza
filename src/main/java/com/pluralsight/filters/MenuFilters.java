package com.pluralsight.filters;

import com.pluralsight.domain.Drink;
import com.pluralsight.domain.GarlicKnot;
import com.pluralsight.domain.MenuItem;
import com.pluralsight.domain.enums.DrinkSize;
import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.pizza.Pizza;

import java.util.function.Predicate;

public final class MenuFilters {

    private MenuFilters() {}

    /** Matches ALL items */
    public static Predicate<MenuItem> any() {
        return it -> true;
    }

    public static Predicate<MenuItem> category(String category) {
        if (category == null || category.isBlank())
            return any();

        String needle = category.toLowerCase();
        return it -> it.getCategory() != null &&
                it.getCategory().toLowerCase().contains(needle);
    }

    public static Predicate<MenuItem> priceBetween(double min, double max) {
        return it -> {
            double p = it.getPrice();
            return p >= min && p <= max;
        };
    }

    public static Predicate<MenuItem> pizzaSize(PizzaSize size) {
        return it -> (it instanceof Pizza p) && p.getSize() == size;
    }

    public static Predicate<MenuItem> crust(CrustType crust) {
        return it -> (it instanceof Pizza p) && p.getCrust() == crust;
    }

    public static Predicate<MenuItem> hasToppingNamed(String part) {
        if (part == null || part.isBlank())
            return any();

        String word = part.toLowerCase();

        return it -> (it instanceof Pizza p)
                && p.getToppings().stream()
                .anyMatch(t -> t.getName().toLowerCase().contains(word));
    }

    public static Predicate<MenuItem> drinkSize(DrinkSize size) {
        return it -> (it instanceof Drink d) && d.getSize() == size;
    }

    public static Predicate<MenuItem> flavorContains(String part) {
        if (part == null || part.isBlank())
            return any();

        String needle = part.toLowerCase();

        return it -> (it instanceof Drink d)
                && d.getFlavor().toLowerCase().contains(needle);
    }

    public static Predicate<MenuItem> minKnots(int minQty) {
        return it -> (it instanceof GarlicKnot g)
                && g.getQuantity() >= minQty;
    }
}
