package com.pluralsight;

public class MargheritaPizza extends SignaturPizza {

    public MargheritaPizza() {
        super(PizzaSize.MEDIUM, CrustType.REGULAR, false);
    }

    @Override
    protected void initializeSignatureToppings() {
        addTopping(new RegularTopping("Tomatoes", false));
        addTopping(new RegularTopping("Basil", false));
        addTopping(new Cheese("Mozzarella", false, 0.75));
        addTopping(new RegularTopping("Marinara", false));
        addTopping(new RegularTopping("Olive Oil", false));
    }
}
