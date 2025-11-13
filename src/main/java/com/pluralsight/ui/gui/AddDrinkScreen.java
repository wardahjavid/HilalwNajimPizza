package com.pluralsight.ui.gui;

import com.pluralsight.domain.Drink;
import com.pluralsight.domain.enums.DrinkSize;

import javax.swing.*;
import java.awt.*;

public class AddDrinkScreen extends JPanel {

    public AddDrinkScreen(POSFrame frame) {

        setLayout(new GridLayout(6,1));

        JComboBox<DrinkSize> sizeBox = new JComboBox<>(DrinkSize.values());
        JTextField flavorField = new JTextField();

        JButton confirm = new JButton("Add Drink");
        JButton back = new JButton("Back");

        add(new JLabel("Add Drink", SwingConstants.CENTER));
        add(new JLabel("Size:"));
        add(sizeBox);
        add(new JLabel("Flavor:"));
        add(flavorField);
        add(confirm);
        add(back);

        confirm.addActionListener(e -> {

            Drink d = new Drink(
                    (DrinkSize) sizeBox.getSelectedItem(),
                    flavorField.getText()
            );
            GUIState.currentOrder.addItem(d);

            JOptionPane.showMessageDialog(this, "Drink Added!");
            frame.showScreen("ORDER");
        });

        back.addActionListener(e -> frame.showScreen("ORDER"));
    }
}
