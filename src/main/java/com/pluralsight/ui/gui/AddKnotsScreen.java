package com.pluralsight.ui.gui;

import com.pluralsight.domain.GarlicKnot;

import javax.swing.*;
import java.awt.*;

public class AddKnotsScreen extends JPanel {

    public AddKnotsScreen(POSFrame frame) {

        setLayout(new GridLayout(5,1));

        JTextField qtyField = new JTextField();

        JButton confirm = new JButton("Add Knots");
        JButton back = new JButton("Back");

        add(new JLabel("Add Garlic Knots", SwingConstants.CENTER));
        add(new JLabel("Quantity:"));
        add(qtyField);
        add(confirm);
        add(back);

        confirm.addActionListener(e -> {
            int qty = Integer.parseInt(qtyField.getText());
            GUIState.currentOrder.addItem(new GarlicKnot(qty));
            JOptionPane.showMessageDialog(this, "Garlic Knots Added!");
            frame.showScreen("ORDER");
        });

        back.addActionListener(e -> frame.showScreen("ORDER"));
    }
}
