package com.pluralsight.gui;

import javax.swing.*;

public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PosFrame frame = new PosFrame();
            frame.setVisible(true);
        });
    }
}
