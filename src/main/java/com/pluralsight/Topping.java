package com.pluralsight;

public abstract class Topping {
    protected final String name;
    protected final boolean isExtra;

    protected Topping(String name, boolean isExtra) {
        this.name = name;
        this.isExtra = isExtra;
    }


    public String getName() { return name; }
    public boolean isExtra() { return isExtra; }

    public abstract double getPrice(PizzaSize size);

    @Override
    public String toString() { return name + (isExtra ? " (extra)" : ""); }
}

