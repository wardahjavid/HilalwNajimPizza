package com.pluralsight;

public abstract class SignaturPizza extends Pizza {

    public SignaturPizza(PizzaSize size, CrustType crustType, boolean stuffedCrust) {
        super(size, crustType, stuffedCrust);
        initializeSignatureToppings();
    }

    protected abstract void initializeSignatureToppings();

    @Override
    public String toString() {
        return "\n Signature Pizza: " + getClass().getSimpleName() +
                " (" + getSize() + ", " + getCrust() +
                (isStuffedCrust() ? ", Stuffed" : "") + ")" +
                "\nToppings: " + getToppings().stream().map(Topping::getName).toList() +
                "\nPrice: $" + String.format("%.2f", getPrice());
    }
}
