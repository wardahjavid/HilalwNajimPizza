package com.pluralsight;

public abstract class Topping {
    protected String name;
    protected boolean isExtra;

    public Topping(String name, boolean isExtra) {
        this.name = name;
        this.isExtra = isExtra;
    }
}


