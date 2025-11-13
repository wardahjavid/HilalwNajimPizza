package com.pluralsight;

import java.util.Comparator;

/**
 * MenuSorters provides Comparator<MenuItem> implementations
 * for sorting by price, category, and label (name).
 */
public final class MenuSorters {
    private MenuSorters() {}

    /** Sort items by price ascending */
    public static Comparator<MenuItem> byPriceAsc() {
        return Comparator.comparingDouble(MenuItem::getPrice);
    }

    /** Sort items by price descending */
    public static Comparator<MenuItem> byPriceDesc() {
        return Comparator.comparingDouble(MenuItem::getPrice).reversed();
    }

    /** Sort items alphabetically by category */
    public static Comparator<MenuItem> byCategory() {
        return Comparator.comparing(MenuItem::getCategory, String.CASE_INSENSITIVE_ORDER);
    }

    /** Sort items alphabetically by label */
    public static Comparator<MenuItem> byLabel() {
        return Comparator.comparing(MenuItem::getLabel, String.CASE_INSENSITIVE_ORDER);
    }
}
