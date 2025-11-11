package com.pluralsight;
import java.util.ArrayList;
import java.util.List;

public class Pizza extends MenuItem {
    private PizzaSize size;
    private CrustType crust;
    private boolean stuffedCrust;
    private List<Topping> toppings = new ArrayList<>();

    public Pizza(PizzaSize size, CrustType crust, boolean stuffedCrust) {
        super("Pizza");
        this.size = size;
        this.crust = crust;
        this.stuffedCrust = stuffedCrust;
    }
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

