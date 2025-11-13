package com.pluralsight.ui.gui;

import com.pluralsight.domain.MenuItem;

import javax.swing.*;
import java.awt.*;

public class CheckoutScreen extends JPanel {

    public CheckoutScreen(POSFrame frame) {

        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        JButton confirm = new JButton("Confirm & Save Receipt");
        JButton back = new JButton("Back");

        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(confirm);
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);

        this.addHierarchyListener(e -> {
            if (isShowing()) {
                area.setText("");

                for (MenuItem m : GUIState.currentOrder.getItems()) {
                    area.append(m + "\n");
                }
                area.append("\nTotal: $" + GUIState.currentOrder.calculateTotal());
            }
        });

        confirm.addActionListener(e -> {
            GUIState.receiptManager.saveOrder(GUIState.currentOrder);
            JOptionPane.showMessageDialog(this, "Order Saved!");
            GUIState.currentOrder = new com.pluralsight.domain.Order(); // reset
            frame.showScreen("HOME");
        });

        back.addActionListener(e -> frame.showScreen("ORDER"));
    }
}
