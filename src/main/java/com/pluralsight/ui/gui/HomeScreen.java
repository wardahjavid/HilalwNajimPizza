package com.pluralsight.ui.gui;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {

    public HomeScreen(POSFrame frame) {
        setLayout(new GridLayout(5,1,10,10));

        JLabel title = new JLabel("HilalwNajim Pizza POS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnNewOrder = new JButton("New Order");
        JButton btnAdmin = new JButton("Manager / Admin");
        JButton btnExit = new JButton("Exit");

        btnNewOrder.addActionListener(e -> frame.showScreen("ORDER"));
        btnExit.addActionListener(e -> System.exit(0));

        add(title);
        add(btnNewOrder);
        add(btnAdmin);
        add(btnExit);
    }
}
