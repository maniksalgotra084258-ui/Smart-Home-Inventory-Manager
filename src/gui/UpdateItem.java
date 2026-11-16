
package gui;

import javax.swing.*;
import java.awt.*;
import model.InventoryItem;
import service.InventoryItemService;

public class UpdateItem extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField supplierField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField minStockField;

    private final InventoryItemService service =
            new InventoryItemService();

    private final Runnable onSaved;

    // Constructor used by Inventory.java
    public UpdateItem(InventoryItem item) {
        this(item, null);
    }

    // Constructor with refresh callback
    public UpdateItem(InventoryItem item, Runnable onSaved) {

        this.onSaved = onSaved;

        setTitle("Update Inventory Item");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        idField = new JTextField();
        nameField = new JTextField();
        categoryField = new JTextField();
        supplierField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        minStockField = new JTextField();

        panel.add(new JLabel("Item ID:"));
        panel.add(idField);

        panel.add(new JLabel("Item Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        panel.add(new JLabel("Supplier:"));
        panel.add(supplierField);

        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        panel.add(new JLabel("Minimum Stock:"));
        panel.add(minStockField);

        JButton updateButton = new JButton("Update Item");
        JButton clearButton = new JButton("Clear");
        JButton backButton = new JButton("Back");

        panel.add(updateButton);
        panel.add(clearButton);
        panel.add(backButton);

        add(panel);

        // Load selected item details
        if (item != null) {
            idField.setText(String.valueOf(item.getItemId()));
            nameField.setText(item.getItemName());
            categoryField.setText(item.getCategory());
            supplierField.setText(item.getSupplier());
            quantityField.setText(String.valueOf(item.getQuantity()));
            priceField.setText(String.valueOf(item.getPrice()));
            minStockField.setText(
                    String.valueOf(item.getMinimumStock()));

            idField.setEditable(false);
        }

        // Update database
        updateButton.addActionListener(e -> updateItem());

        // Clear fields
        clearButton.addActionListener(e -> {
            nameField.setText("");
            categoryField.setText("");
            supplierField.setText("");
            quantityField.setText("");
            priceField.setText("");
            minStockField.setText("");
        });

        // Back
        backButton.addActionListener(e -> dispose());
    }

    private void updateItem() {

        try {
            int id = Integer.parseInt(idField.getText().trim());

            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String supplier = supplierField.getText().trim();

            int quantity = Integer.parseInt(
                    quantityField.getText().trim());

            double price = Double.parseDouble(
                    priceField.getText().trim());

            int minStock = Integer.parseInt(
                    minStockField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Item name is required!");
                return;
            }

            InventoryItem item = new InventoryItem(
                    id, name, category, supplier,
                    quantity, price, minStock);

            if (service.updateItem(item)) {

                JOptionPane.showMessageDialog(
                        this, "Item updated successfully!");

                if (onSaved != null) {
                    onSaved.run();
                }

                dispose();

            } else {
                JOptionPane.showMessageDialog(
                        this, "Unable to update item!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter valid numeric values!");
        }
    }

    // For standalone testing
    public UpdateItem() {
        this(null, null);
    }
}
