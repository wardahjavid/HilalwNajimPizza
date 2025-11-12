package com.pluralsight;

public  class GarlicKnot extends MenuItem {
    private final int quantity;
    public GarlicKnot(int quantity) { super("GarlicKnot"); this.quantity = quantity; }
    public int getQuantity() { return quantity; }
    @Override public double getPrice() { return quantity * 1.50; }
    @Override public String getLabel() { return quantity + " order(s)"; }
    @Override public String toString() { return getLabel() + " - $" + String.format("%.2f", getPrice()); }

    @Override
    public double calculatePrice() {
        return 0;
    }
}
