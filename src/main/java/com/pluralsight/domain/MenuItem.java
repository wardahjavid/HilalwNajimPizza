package com.pluralsight.domain;

public abstract class MenuItem {

    private final String category;

    protected MenuItem(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public abstract String getLabel();

    public abstract double getPrice();

    @Override
    public String toString() {
        return getLabel() + " - $" + String.format("%.2f", getPrice());
    }
}
