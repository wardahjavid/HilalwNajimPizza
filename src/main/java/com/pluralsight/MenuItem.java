package com.pluralsight;

public abstract class MenuItem {
    protected String name;

    protected MenuItem(String name) { this.name = name; }

    public String getCategory() { return name; }
    public String getLabel() { return name; }

    public abstract double getPrice();
    @Override
    public abstract String toString();
}

