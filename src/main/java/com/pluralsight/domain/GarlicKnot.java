package com.pluralsight.domain;

import com.pluralsight.config.PriceConfig;

public class GarlicKnot extends MenuItem {

    private final int quantity;

    public GarlicKnot(int quantity) {
        super("Garlic Knots");
        this.quantity = quantity;
    }

    @Override
    public String getLabel() {
        return quantity + " order(s)";
    }

    @Override
    public double getPrice() {
        return quantity * PriceConfig.GARLIC_KNOT_PRICE;
    }

    public int getQuantity() {
        return quantity;
    }
}
