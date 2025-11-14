package com.pluralsight.gui;

import com.pluralsight.domain.*;
import com.pluralsight.domain.MenuItem;
import com.pluralsight.domain.enums.*;
import com.pluralsight.domain.pizza.CustomPizza;
import com.pluralsight.domain.pizza.MargheritaPizza;
import com.pluralsight.domain.pizza.Pizza;
import com.pluralsight.domain.pizza.VeggiePizza;
import com.pluralsight.domain.topping.Cheese;
import com.pluralsight.domain.topping.Meat;
import com.pluralsight.domain.topping.RegularTopping;
import com.pluralsight.domain.topping.Sauce;
import com.pluralsight.io.ReceiptFileManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PosFrame extends JFrame {

    private static final String CARD_HOME   = "HOME";
    private static final String CARD_ORDER  = "ORDER";
    private static final String CARD_PIZZA  = "PIZZA";
    private static final String CARD_DRINK  = "DRINK";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final Order currentOrder = new Order();
    private final ReceiptFileManager receiptManager = new ReceiptFileManager();

    private final JTextArea summaryArea = new JTextArea(10, 40);

    private JComboBox<PizzaSize> sizeCombo;
    private JComboBox<CrustType> crustCombo;
    private JCheckBox stuffedCheck;
    private DefaultListModel<String> toppingListModel;
    private java.util.List<com.pluralsight.domain.topping.Topping> pendingToppings;

    private JComboBox<DrinkSize> drinkSizeCombo;
    private JTextField drinkFlavorField;

    public PosFrame() {
        setTitle("HilalwNajimPizza POS - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        buildScreens();

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, CARD_HOME);
    }

    private void buildScreens() {
        cardPanel.add(buildHomePanel(), CARD_HOME);
        cardPanel.add(buildOrderPanel(), CARD_ORDER);
        cardPanel.add(buildCustomPizzaPanel(), CARD_PIZZA);
        cardPanel.add(buildDrinkPanel(), CARD_DRINK);
    }

    private JPanel buildHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("HilalwNajimPizza - Point of Sale System", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JPanel center = new JPanel();
        JButton newOrderBtn = new JButton("Start New Order");
        JButton exitBtn     = new JButton("Exit");

        newOrderBtn.addActionListener(e -> {
            currentOrder.getItems().clear();
            updateSummaryArea();
            cardLayout.show(cardPanel, CARD_ORDER);
        });

        exitBtn.addActionListener(e -> dispose());

        center.add(newOrderBtn);
        center.add(exitBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Order Menu", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));

        JPanel buttons = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton customPizzaBtn   = new JButton("Add Custom Pizza");
        JButton signaturePizzaBtn= new JButton("Add Signature Pizza");
        JButton drinkBtn         = new JButton("Add Drink");
        JButton knotsBtn         = new JButton("Add Garlic Knots");
        JButton checkoutBtn      = new JButton("Checkout");
        JButton backHomeBtn      = new JButton("Back to Home");

        customPizzaBtn.addActionListener(e -> {
            resetCustomPizzaPanel();
            cardLayout.show(cardPanel, CARD_PIZZA);
        });

        signaturePizzaBtn.addActionListener(e -> addSignaturePizzaGui());

        drinkBtn.addActionListener(e -> {
            resetDrinkPanel();
            cardLayout.show(cardPanel, CARD_DRINK);
        });

        knotsBtn.addActionListener(e -> addGarlicKnotsGui());

        checkoutBtn.addActionListener(e -> checkoutGui());

        backHomeBtn.addActionListener(e -> cardLayout.show(cardPanel, CARD_HOME));

        buttons.add(customPizzaBtn);
        buttons.add(signaturePizzaBtn);
        buttons.add(drinkBtn);
        buttons.add(knotsBtn);
        buttons.add(checkoutBtn);
        buttons.add(backHomeBtn);

        // Summary area
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(summaryArea);
        updateSummaryArea();

        panel.add(label, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildCustomPizzaPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Add Custom Pizza", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        sizeCombo = new JComboBox<>(PizzaSize.values());
        crustCombo = new JComboBox<>(CrustType.values());
        stuffedCheck = new JCheckBox("Stuffed crust");

        toppingListModel = new DefaultListModel<>();
        JList<String> toppingList = new JList<>(toppingListModel);

        JTextField toppingNameField = new JTextField(15);
        JComboBox<String> toppingTypeCombo = new JComboBox<>(new String[]{"regular", "cheese", "meat", "sauce"});
        JButton addToppingBtn = new JButton("Add Topping");

        pendingToppings = new java.util.ArrayList<>();

        addToppingBtn.addActionListener(e -> {
            String name = toppingNameField.getText().trim();
            if (name.isEmpty()) return;
            String type = ((String) toppingTypeCombo.getSelectedItem()).toLowerCase();

            com.pluralsight.domain.topping.Topping topping;
            switch (type) {
                case "cheese" -> topping = new Cheese(name, false);
                case "meat"   -> topping = new Meat(name, false);
                case "sauce"  -> topping = new Sauce(name);
                default       -> topping = new RegularTopping(name);
            }
            pendingToppings.add(topping);
            toppingListModel.addElement(type + ": " + name);
            toppingNameField.setText("");
        });

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1; form.add(sizeCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Crust:"), gbc);
        gbc.gridx = 1; form.add(crustCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Stuffed:"), gbc);
        gbc.gridx = 1; form.add(stuffedCheck, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Topping type:"), gbc);
        gbc.gridx = 1; form.add(toppingTypeCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Topping name:"), gbc);
        gbc.gridx = 1; form.add(toppingNameField, gbc);

        row++;
        gbc.gridx = 1; gbc.gridy = row; form.add(addToppingBtn, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        form.add(new JScrollPane(toppingList), gbc);

        JPanel bottom = new JPanel();
        JButton saveBtn = new JButton("Save Pizza");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveCustomPizza());
        cancelBtn.addActionListener(e -> cardLayout.show(cardPanel, CARD_ORDER));

        bottom.add(saveBtn);
        bottom.add(cancelBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void resetCustomPizzaPanel() {
        sizeCombo.setSelectedItem(PizzaSize.MEDIUM);
        crustCombo.setSelectedItem(CrustType.REGULAR);
        stuffedCheck.setSelected(false);
        pendingToppings.clear();
        toppingListModel.clear();
    }

    private void saveCustomPizza() {
        PizzaSize size = (PizzaSize) sizeCombo.getSelectedItem();
        CrustType crust = (CrustType) crustCombo.getSelectedItem();
        boolean stuffed = stuffedCheck.isSelected();

        CustomPizza pizza = new CustomPizza(size, crust, stuffed);
        for (com.pluralsight.domain.topping.Topping t : pendingToppings) {
            pizza.addTopping(t);
        }

        currentOrder.addItem(pizza);
        updateSummaryArea();

        JOptionPane.showMessageDialog(this,
                "Custom pizza added to order.",
                "Pizza Added",
                JOptionPane.INFORMATION_MESSAGE);

        cardLayout.show(cardPanel, CARD_ORDER);
    }

    private JPanel buildDrinkPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Add Drink", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        drinkSizeCombo = new JComboBox<>(DrinkSize.values());
        drinkFlavorField = new JTextField(15);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1; form.add(drinkSizeCombo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; form.add(new JLabel("Flavor:"), gbc);
        gbc.gridx = 1; form.add(drinkFlavorField, gbc);

        JPanel bottom = new JPanel();
        JButton saveBtn = new JButton("Add Drink");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveDrink());
        cancelBtn.addActionListener(e -> cardLayout.show(cardPanel, CARD_ORDER));

        bottom.add(saveBtn);
        bottom.add(cancelBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private void resetDrinkPanel() {
        drinkSizeCombo.setSelectedItem(DrinkSize.MEDIUM);
        drinkFlavorField.setText("");
    }

    private void saveDrink() {
        DrinkSize size = (DrinkSize) drinkSizeCombo.getSelectedItem();
        String flavor = drinkFlavorField.getText().trim();
        if (flavor.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a flavor.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentOrder.addItem(new Drink(size, flavor));
        updateSummaryArea();

        JOptionPane.showMessageDialog(this,
                "Drink added to order.",
                "Drink Added",
                JOptionPane.INFORMATION_MESSAGE);

        cardLayout.show(cardPanel, CARD_ORDER);
    }
    private void addSignaturePizzaGui() {
        Object[] options = {"Margherita", "Veggie", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose a signature pizza:",
                "Signature Pizza",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        Pizza pizza = null;
        if (choice == 0) pizza = new MargheritaPizza();
        else if (choice == 1) pizza = new VeggiePizza();

        if (pizza != null) {
            currentOrder.addItem(pizza);
            updateSummaryArea();
            JOptionPane.showMessageDialog(this,
                    pizza.getClass().getSimpleName() + " added to order.",
                    "Signature Pizza Added",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addGarlicKnotsGui() {
        String input = JOptionPane.showInputDialog(this,
                "Enter quantity of garlic knot orders:",
                "Garlic Knots",
                JOptionPane.QUESTION_MESSAGE);

        if (input == null) return;
        try {
            int qty = Integer.parseInt(input.trim());
            if (qty <= 0) throw new NumberFormatException();
            currentOrder.addItem(new GarlicKnot(qty));
            updateSummaryArea();
            JOptionPane.showMessageDialog(this,
                    "Garlic knots added.",
                    "Added",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid positive integer.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkoutGui() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot checkout an empty order.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = currentOrder.calculateTotal();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Total: $" + String.format("%.2f", total) + "\nProceed to checkout?",
                "Confirm Checkout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        String filePath = receiptManager.saveOrder(currentOrder);

        LocalDateTime now = LocalDateTime.now();
        String readableTime = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm:ss a"));

        JOptionPane.showMessageDialog(
                this,
                "Receipt saved:\n" + filePath + "\n" + readableTime,
                "Checkout Complete",
                JOptionPane.INFORMATION_MESSAGE
        );

        currentOrder.getItems().clear();
        updateSummaryArea();
    }

    private void updateSummaryArea() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Order:\n");
        int index = 1;
        for (MenuItem item : currentOrder.getItems()) {
            sb.append(index++)
                    .append(") ")
                    .append(item.getCategory())
                    .append(" - ")
                    .append(item.getLabel())
                    .append("  $")
                    .append(String.format("%.2f", item.getPrice()))
                    .append("\n");
        }
        sb.append("\nTotal: $")
                .append(String.format("%.2f", currentOrder.calculateTotal()));

        summaryArea.setText(sb.toString());,
    }
}
