package com.pluralsight;

public abstract class Topping {
    protected String name;
    protected boolean isExtra;

    public Topping(String name, boolean isExtra) {
        this.name = name;
        this.isExtra = isExtra;
    }
    public String getName() {
        return name;
    }
    public abstract double getPrice(PizzaSize size);

    @Override
    public String toString() {
        return name + (isExtra ? " (extra)" : "");
    }
}


