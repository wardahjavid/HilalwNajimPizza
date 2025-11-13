package com.pluralsight.ui.gui;

import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new POSFrame().setVisible(true));
    }
}
