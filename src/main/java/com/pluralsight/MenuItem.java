package com.pluralsight;

public abstract class MenuItem {
    protected String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public abstract double getPrice();
    public abstract String toString();
}

