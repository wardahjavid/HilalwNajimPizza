package com.pluralsight;

public class RegularTopping extends Topping {
    public RegularTopping(String name, boolean b) { super(name, false); }
    @Override public double getPrice(PizzaSize size) { return 0.0; }
}
