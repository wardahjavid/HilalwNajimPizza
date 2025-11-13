package com.pluralsight.config;

import com.pluralsight.domain.enums.DrinkSize;
import com.pluralsight.domain.enums.PizzaSize;

public final class PriceConfig {

    private PriceConfig() {}

    private static final double PERSONAL_BASE = 8.50;
    private static final double MEDIUM_BASE   = 12.00;
    private static final double LARGE_BASE    = 16.50;

    private static final double PERSONAL_SIG = 9.50;
    private static final double MEDIUM_SIG   = 13.50;
    private static final double LARGE_SIG    = 18.00;

    public static final double STUFFED_CRUST_UPCHARGE = 2.00;

    public static final double GARLIC_KNOT_PRICE = 1.50;

    private static final double DRINK_SMALL  = 2.00;
    private static final double DRINK_MEDIUM = 2.50;
    private static final double DRINK_LARGE  = 3.00;

    private static final double CHEESE_BASE_PERSONAL = 0.75;
    private static final double MEAT_BASE_PERSONAL   = 1.00;

    public static double basePizzaPrice(PizzaSize size) {
        return switch (size) {
            case PERSONAL -> PERSONAL_BASE;
            case MEDIUM   -> MEDIUM_BASE;
            case LARGE    -> LARGE_BASE;
        };
    }

    public static double signatureBasePrice(PizzaSize size) {
        return switch (size) {
            case PERSONAL -> PERSONAL_SIG;
            case MEDIUM   -> MEDIUM_SIG;
            case LARGE    -> LARGE_SIG;
        };
    }

    public static double drinkPrice(DrinkSize size) {
        return switch (size) {
            case SMALL  -> DRINK_SMALL;
            case MEDIUM -> DRINK_MEDIUM;
            case LARGE  -> DRINK_LARGE;
        };
    }

    public static double cheesePrice(PizzaSize size, boolean extra) {
        double base = switch (size) {
            case PERSONAL -> CHEESE_BASE_PERSONAL;
            case MEDIUM   -> CHEESE_BASE_PERSONAL * 2;
            case LARGE    -> CHEESE_BASE_PERSONAL * 3;
        };
        if (extra) base += CHEESE_BASE_PERSONAL / 2;
        return base;
    }

    public static double meatPrice(PizzaSize size, boolean extra) {
        double base = switch (size) {
            case PERSONAL -> MEAT_BASE_PERSONAL;
            case MEDIUM   -> MEAT_BASE_PERSONAL * 2;
            case LARGE    -> MEAT_BASE_PERSONAL * 3;
        };
        if (extra) base += MEAT_BASE_PERSONAL / 2;
        return base;
    }
}
