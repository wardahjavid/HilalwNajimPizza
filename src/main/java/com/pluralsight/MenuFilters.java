package com.pluralsight;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * MenuFilters provides reusable Predicate<MenuItem> filters
 * for searching and narrowing order items by category, price,
 * pizza attributes, drinks, and garlic knots.
 */
public final class MenuFilters {
    private MenuFilters() {}

    /** Default filter that matches everything */
    public static Predicate<MenuItem> any() {
        return it -> true;
    }

    /** Filter by menu item category (Pizza, Drink, Knot, etc.) */
    public static Predicate<MenuItem> category(String category) {
        String target = category.toLowerCase(Locale.ROOT);
        return it -> it.getCategory().toLowerCase(Locale.ROOT).contains(target);
    }

    /** Filter by price range (inclusive) */
    public static Predicate<MenuItem> priceBetween(double min, double max) {
        return it -> it.getPrice() >= min && it.getPrice() <= max;
    }

    // ─────────── Pizza-specific filters ───────────

    /** Filter pizzas by size */
    public static Predicate<MenuItem> pizzaSize(PizzaSize size) {
        return it -> (it instanceof Pizza p) && p.getSize() == size;
    }

    /** Filter pizzas by crust type */
    public static Predicate<MenuItem> crust(CrustType crust) {
        return it -> (it instanceof Pizza p) && p.getCrust() == crust;
    }

    /** Filter pizzas that contain a given topping name */
    public static Predicate<MenuItem> hasToppingNamed(String namePart) {
        String needle = namePart.toLowerCase(Locale.ROOT);
        return it -> (it instanceof Pizza p)
                && p.getToppings().stream()
                .anyMatch(t -> t.getName().toLowerCase(Locale.ROOT).contains(needle));
    }

    // ─────────── Drink-specific filters ───────────

    /** Filter drinks by size */
    public static Predicate<MenuItem> drinkSize(DrinkSize size) {
        return it -> (it instanceof Drink d) && d.getSize() == size;
    }

    /** Filter drinks by flavor substring */
    public static Predicate<MenuItem> flavorContains(String keyword) {
        String s = keyword.toLowerCase(Locale.ROOT);
        return it -> (it instanceof Drink d)
                && d.getFlavor().toLowerCase(Locale.ROOT).contains(s);
    }

    // ─────────── Garlic Knot filters ───────────

    /** Filter by minimum garlic knot quantity */
    public static Predicate<MenuItem> minKnots(int minQty) {
        return it -> (it instanceof GarlicKnot g) && g.getQuantity() >= minQty;
    }
}
