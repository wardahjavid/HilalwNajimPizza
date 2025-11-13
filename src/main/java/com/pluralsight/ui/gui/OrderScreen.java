package com.pluralsight.ui.gui;

import javax.swing.*;
import java.awt.*;

public class OrderScreen extends JPanel {

    public OrderScreen(POSFrame frame) {

        setLayout(new GridLayout(7, 1, 8, 8));

        add(new JLabel("Order Menu", SwingConstants.CENTER));

        JButton addPizza = new JButton("Add Custom Pizza");
        JButton addDrink = new JButton("Add Drink");
        JButton addKnot = new JButton("Add Garlic Knots");
        JButton checkout = new JButton("Checkout");
        JButton cancel = new JButton("Cancel Order");
        JButton back = new JButton("Back");

        addPizza.addActionListener(e -> frame.showScreen("ADD_PIZZA"));
        addDrink.addActionListener(e -> frame.showScreen("ADD_DRINK"));
        addKnot.addActionListener(e -> frame.showScreen("ADD_KNOTS"));
        checkout.addActionListener(e -> frame.showScreen("CHECKOUT"));
        back.addActionListener(e -> frame.showScreen("HOME"));
        cancel.addActionListener(e -> frame.showScreen("HOME"));

        add(addPizza);
        add(addDrink);
        add(addKnot);
        add(checkout);
        add(cancel);
        add(back);
    }
}
