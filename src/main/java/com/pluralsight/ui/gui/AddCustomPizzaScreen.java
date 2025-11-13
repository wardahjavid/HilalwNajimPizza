package com.pluralsight.ui.gui;

import com.pluralsight.domain.enums.CrustType;
import com.pluralsight.domain.enums.PizzaSize;
import com.pluralsight.domain.pizza.CustomPizza;

import javax.swing.*;
import java.awt.*;

public class AddCustomPizzaScreen extends JPanel {

    public AddCustomPizzaScreen(POSFrame frame) {

        setLayout(new GridLayout(10, 1));

        JLabel title = new JLabel("Create Custom Pizza", SwingConstants.CENTER);

        JComboBox<PizzaSize> sizeBox = new JComboBox<>(PizzaSize.values());
        JComboBox<CrustType> crustBox = new JComboBox<>(CrustType.values());

        JCheckBox stuffed = new JCheckBox("Stuffed Crust (+$2)");

        JButton addToppings = new JButton("Add Toppings");
        JButton confirm = new JButton("Add Pizza");
        JButton back = new JButton("Back");

        add(title);
        add(new JLabel("Size:"));
        add(sizeBox);
        add(new JLabel("Crust:"));
        add(crustBox);
        add(stuffed);
        add(addToppings);
        add(confirm);
        add(back);

        confirm.addActionListener(e -> {
            CustomPizza pizza = new CustomPizza(
                    (PizzaSize) sizeBox.getSelectedItem(),
                    (CrustType) crustBox.getSelectedItem(),
                    stuffed.isSelected()
            );

            // Store Pizza in temporary static buffer
            GUIState.currentOrder.addItem(pizza);

            JOptionPane.showMessageDialog(this, "Pizza Added!");
            frame.showScreen("ORDER");
        });

        back.addActionListener(e -> frame.showScreen("ORDER"));
    }
}
