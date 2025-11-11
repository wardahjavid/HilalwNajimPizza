package com.pluralsight;

public abstract class MenuItem {
    protected String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public abstract double getPrice();
    public String getCategory() { return name; }
    public String getLabel() { return name; }
    @Override
    public abstract String toString();
}

