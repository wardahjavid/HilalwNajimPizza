package com.pluralsight.filters;

import com.pluralsight.domain.MenuItem;

import java.util.Comparator;

public final class MenuSorters {

    private MenuSorters() {}

    public static Comparator<MenuItem> byPriceAsc() {
        return Comparator.comparingDouble(MenuItem::getPrice);
    }

    public static Comparator<MenuItem> byPriceDesc() {
        return Comparator.comparingDouble(MenuItem::getPrice).reversed();
    }

    public static Comparator<MenuItem> byCategory() {
        return Comparator.comparing(MenuItem::getCategory, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<MenuItem> byLabel() {
        return Comparator.comparing(MenuItem::getLabel, String.CASE_INSENSITIVE_ORDER);
    }
}
