package com.pluralsight.domain.topping;

import com.pluralsight.domain.enums.PizzaSize;

public abstract class Topping {

    protected final String name;
    protected final boolean extra;

    protected Topping(String name, boolean extra) {
        this.name = name;
        this.extra = extra;
    }

    public String getName() {
        return name;
    }

    public boolean isExtra() {
        return extra;
    }

    public abstract double getPrice(PizzaSize size);

    @Override
    public String toString() {
        return name + (extra ? " (extra)" : "");
    }
}
