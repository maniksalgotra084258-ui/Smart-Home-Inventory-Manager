
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import model.InventoryItem;
import service.InventoryItemService;

public class LowStockAlert extends JFrame {

    private JTable lowStockTable;
    private DefaultTableModel model;

    private final InventoryItemService service =
            new InventoryItemService();

    public LowStockAlert() {

        setTitle("Low Stock Alert");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Heading
        JLabel heading = new JLabel(
                "LOW STOCK ALERT", SwingConstants.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Table
        String[] columns = {
                "Item ID",
                "Item Name",
                "Category",
                "Current Stock",
                "Minimum Stock",
                "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        lowStockTable = new JTable(model);
        lowStockTable.setRowHeight(30);

        lowStockTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14));

        mainPanel.add(
                new JScrollPane(lowStockTable),
                BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton restockButton = new JButton("Restock Item");
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back");

        buttonPanel.add(restockButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Restock
        restockButton.addActionListener(e -> restockItem());

        // Refresh
        refreshButton.addActionListener(e -> loadLowStock());

        // Back
        backButton.addActionListener(e -> dispose());

        add(mainPanel);

        loadLowStock();
    }

    // Load low-stock items from database
    private void loadLowStock() {

        model.setRowCount(0);

        for (InventoryItem item : service.getLowStockItems()) {

            String status = item.getQuantity() == 0
                    ? "CRITICAL" : "LOW STOCK";

            model.addRow(new Object[]{
                    item.getItemId(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getMinimumStock(),
                    status
            });
        }
    }

    // Restock selected item
    private void restockItem() {

        int selectedRow = lowStockTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this, "Please select an item to restock.");
            return;
        }

        int itemId = Integer.parseInt(
                model.getValueAt(selectedRow, 0).toString());

        String input = JOptionPane.showInputDialog(
                this, "Enter quantity to add:");

        if (input == null) {
            return;
        }

        try {
            int addedQuantity = Integer.parseInt(input.trim());

            if (addedQuantity <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Enter a quantity greater than zero.");
                return;
            }

            for (InventoryItem item : service.getAllItems()) {

                if (item.getItemId() == itemId) {

                    int newQuantity =
                            item.getQuantity() + addedQuantity;

                    InventoryItem updatedItem = new InventoryItem(
                            item.getItemId(),
                            item.getItemName(),
                            item.getCategory(),
                            item.getSupplier(),
                            newQuantity,
                            item.getPrice(),
                            item.getMinimumStock()
                    );

                    if (service.updateItem(updatedItem)) {
                        JOptionPane.showMessageDialog(
                                this, "Item restocked successfully!");

                        loadLowStock();
                    } else {
                        JOptionPane.showMessageDialog(
                                this, "Unable to restock item.");
                    }

                    return;
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Please enter a valid whole number.");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LowStockAlert alert = new LowStockAlert();
            alert.setVisible(true);
        });
    }
}
