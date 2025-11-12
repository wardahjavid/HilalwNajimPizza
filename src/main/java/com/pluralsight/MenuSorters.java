package com.pluralsight;

import java.util.Comparator;

public final class MenuSorters {
    private MenuSorters() {}

    public static Comparator<MenuItem> byPriceAsc()  { return Comparator.comparingDouble(MenuItem::getPrice); }
    public static Comparator<MenuItem> byPriceDesc() { return Comparator.comparingDouble(MenuItem::getPrice).reversed(); }
    public static Comparator<MenuItem> byCategory()  { return Comparator.comparing(MenuItem::getCategory); }
    public static Comparator<MenuItem> byLabel()     { return Comparator.comparing(MenuItem::getLabel); }
}
