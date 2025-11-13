package com.pluralsight.ui.gui;

import javax.swing.*;
import java.awt.*;

public class POSFrame extends JFrame {

    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    public POSFrame() {

        setTitle("HilalwNajim Pizza POS");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create screens
        HomeScreen home = new HomeScreen(this);
        OrderScreen order = new OrderScreen(this);
        AddCustomPizzaScreen addPizza = new AddCustomPizzaScreen(this);
        AddDrinkScreen addDrink = new AddDrinkScreen(this);
        AddKnotsScreen addKnots = new AddKnotsScreen(this);
        CheckoutScreen checkout = new CheckoutScreen(this);

        // Register screens
        container.add(home, "HOME");
        container.add(order, "ORDER");
        container.add(addPizza, "ADD_PIZZA");
        container.add(addDrink, "ADD_DRINK");
        container.add(addKnots, "ADD_KNOTS");
        container.add(checkout, "CHECKOUT");

        add(container);
    }

    public void showScreen(String name) {
        layout.show(container, name);
    }
}
