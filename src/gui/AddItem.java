
package gui;

import javax.swing.*;
import java.awt.*;

import model.InventoryItem;
import service.InventoryItemService;

public class AddItem extends JFrame {

    private JTextField itemNameField;
    private JTextField categoryField;
    private JTextField supplierField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField minimumStockField;

    private final InventoryItemService service =
            new InventoryItemService();

    public AddItem() {

        setTitle("Add Inventory Item");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel(
                "ADD INVENTORY ITEM", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));

        itemNameField = new JTextField();
        categoryField = new JTextField();
        supplierField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        minimumStockField = new JTextField();

        formPanel.add(new JLabel("Item Name:"));
        formPanel.add(itemNameField);

        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Supplier:"));
        formPanel.add(supplierField);

        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        formPanel.add(new JLabel("Minimum Stock:"));
        formPanel.add(minimumStockField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save Item");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // SAVE ITEM TO DATABASE
        saveButton.addActionListener(e -> {

            try {

                String name = itemNameField.getText().trim();
                String category = categoryField.getText().trim();
                String supplier = supplierField.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this, "Please enter Item Name.");
                    return;
                }

                int quantity = Integer.parseInt(
                        quantityField.getText().trim());

                double price = Double.parseDouble(
                        priceField.getText().trim());

                int minimumStock = Integer.parseInt(
                        minimumStockField.getText().trim());

                InventoryItem item = new InventoryItem(
                        0,
                        name,
                        category,
                        supplier,
                        quantity,
                        price,
                        minimumStock
                );

                boolean result = service.addItem(item);

                if (result) {
                    JOptionPane.showMessageDialog(
                            this, "Item saved successfully in database!");

                    clearFields();

                } else {
                    JOptionPane.showMessageDialog(
                            this, "Unable to save item.");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid numbers for Quantity, Price and Minimum Stock."
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this, "Error: " + ex.getMessage());
            }
        });

        // CLEAR FIELDS
        clearButton.addActionListener(e -> clearFields());

        // BACK
        backButton.addActionListener(e -> dispose());

        add(mainPanel);
    }

    private void clearFields() {
        itemNameField.setText("");
        categoryField.setText("");
        supplierField.setText("");
        quantityField.setText("");
        priceField.setText("");
        minimumStockField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddItem addItem = new AddItem();
            addItem.setVisible(true);
        });
    }
}
