package com.pluralsight;

import java.util.Locale;
import java.util.function.Predicate;

public final class MenuFilters {
    private MenuFilters() {}

    public static Predicate<MenuItem> any() { return it -> true; }

    public static Predicate<MenuItem> category(String c) {
        String s = c.toLowerCase(Locale.ROOT);
        return it -> it.getCategory().toLowerCase(Locale.ROOT).contains(s);
    }

    public static Predicate<MenuItem> priceBetween(double min, double max) {
        return it -> it.getPrice() >= min && it.getPrice() <= max;
    }

    // Pizza-specific
    public static Predicate<MenuItem> pizzaSize(PizzaSize size) {
        return it -> (it instanceof Pizza p) && p.getSize() == size;
    }
    public static Predicate<MenuItem> crust(CrustType crust) {
        return it -> (it instanceof Pizza p) && p.getCrust() == crust;
    }
    public static Predicate<MenuItem> hasToppingNamed(String part) {
        String needle = part.toLowerCase(Locale.ROOT);
        return it -> (it instanceof Pizza p) &&
                p.getToppings().stream().anyMatch(t -> t.getName().toLowerCase(Locale.ROOT).contains(needle));
    }

    // Drink-specific
    public static Predicate<MenuItem> drinkSize(DrinkSize size) {
        return it -> (it instanceof Drink d) && d.getSize() == size;
    }
    public static Predicate<MenuItem> flavorContains(String part) {
        String s = part.toLowerCase(Locale.ROOT);
        return it -> (it instanceof Drink d) && d.getFlavor().toLowerCase(Locale.ROOT).contains(s);
    }

    // Garlic knots
    public static Predicate<MenuItem> minKnots(int minQty) {
        return it -> (it instanceof GarlicKnot gk) && gk.getQuantity() >= minQty;
    }
}
