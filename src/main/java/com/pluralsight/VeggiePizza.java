package com.pluralsight;
public class VeggiePizza extends SignaturPizza {

    public VeggiePizza() {
        super(PizzaSize.PERSONAL, CrustType.REGULAR, false);
    }

    @Override
    protected void initializeSignatureToppings() {
        addTopping(new RegularTopping("Bell Peppers", false));
        addTopping(new RegularTopping("Spinach", false));
        addTopping(new RegularTopping("Olives", false));
        addTopping(new RegularTopping("Onions", false));
        addTopping(new RegularTopping("Marinara", false));
        addTopping(new Cheese("Mozzarella", false, 0.75));
    }
}
