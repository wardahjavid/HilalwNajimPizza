package com.pluralsight.domain;

public abstract class MenuItem {

    private final String category;

    protected MenuItem(String category) {
        this.category = category;
    }

    /** e.g. "Pizza", "Drink", "Garlic Knots" */
    public String getCategory() {
        return category;
    }

    /** e.g. "Large Thin Pizza", "Medium Cola", "12 Knots" */
    public abstract String getLabel();

    /** price for this menu item */
    public abstract double getPrice();

    @Override
    public String toString() {
        return getLabel() + " - $" + String.format("%.2f", getPrice());
    }
}
