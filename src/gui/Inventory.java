
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.InventoryItem;
import service.InventoryItemService;

public class Inventory extends JFrame {

    private JTable inventoryTable;
    private DefaultTableModel model;
    private final InventoryItemService service =
            new InventoryItemService();

    public Inventory() {

        setTitle("Inventory Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel(
                "INVENTORY MANAGEMENT", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Item Name", "Category", "Supplier",
                "Quantity", "Price", "Min Stock", "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(model);
        inventoryTable.setRowHeight(30);
        inventoryTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14));

        mainPanel.add(
                new JScrollPane(inventoryTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add Item
        addButton.addActionListener(e -> {
            new AddItem().setVisible(true);
        });

        // Update Item
        updateButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this, "Please select an item to update.");
                return;
            }

            int itemId = Integer.parseInt(
                    model.getValueAt(selectedRow, 0).toString());

            for (InventoryItem item : service.getAllItems()) {
                if (item.getItemId() == itemId) {
                    UpdateItem update = new UpdateItem(item);
                    update.setVisible(true);
                    break;
                }
            }
        });

        // Delete Item
        deleteButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this, "Please select an item to delete.");
                return;
            }

            int itemId = Integer.parseInt(
                    model.getValueAt(selectedRow, 0).toString());

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this item?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                if (service.deleteItem(itemId)) {
                    JOptionPane.showMessageDialog(
                            this, "Item deleted successfully!");
                    loadInventory();
                } else {
                    JOptionPane.showMessageDialog(
                            this, "Unable to delete item.");
                }
            }
        });

        // Refresh
        refreshButton.addActionListener(e -> loadInventory());

        // Back
        backButton.addActionListener(e -> dispose());

        add(mainPanel);

        loadInventory();
    }

    // Load records from MySQL
    private void loadInventory() {

        model.setRowCount(0);

        for (InventoryItem item : service.getAllItems()) {

            String status = item.isLowStock()
                    ? "LOW STOCK" : "Normal";

            model.addRow(new Object[]{
                    item.getItemId(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getSupplier(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getMinimumStock(),
                    status
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inventory inventory = new Inventory();
            inventory.setVisible(true);
        });
    }
}
