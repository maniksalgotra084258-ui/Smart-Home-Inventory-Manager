
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import model.InventoryItem;
import service.InventoryItemService;

public class Reports extends JFrame {

    private final InventoryItemService service =
            new InventoryItemService();

    private JLabel totalItemsLabel;
    private JLabel lowStockLabel;
    private JLabel totalSuppliersLabel;
    private JLabel inventoryValueLabel;

    public Reports() {

        setTitle("Inventory Reports");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Heading
        JLabel heading = new JLabel(
                "INVENTORY REPORTS", SwingConstants.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 26));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Report cards
        JPanel cardsPanel = new JPanel(
                new GridLayout(2, 2, 20, 20));

        totalItemsLabel = new JLabel("0", SwingConstants.CENTER);
        lowStockLabel = new JLabel("0", SwingConstants.CENTER);
        totalSuppliersLabel = new JLabel("0", SwingConstants.CENTER);
        inventoryValueLabel = new JLabel("₹0.00", SwingConstants.CENTER);

        cardsPanel.add(createCard("Total Items", totalItemsLabel));
        cardsPanel.add(createCard("Low Stock Items", lowStockLabel));
        cardsPanel.add(createCard("Total Suppliers", totalSuppliersLabel));
        cardsPanel.add(createCard("Inventory Value", inventoryValueLabel));

        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton generateButton = new JButton("Generate Report");
        JButton backButton = new JButton("Back");

        buttonPanel.add(generateButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Generate Report
        generateButton.addActionListener(e -> loadReports());

        // Back
        backButton.addActionListener(e -> dispose());

        add(mainPanel);

        loadReports();
    }

    // Create report card
    private JPanel createCard(String title, JLabel valueLabel) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel titleLabel = new JLabel(
                title, SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 18));

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 28));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // Load report data from MySQL
    private void loadReports() {

        try {
            List<InventoryItem> items = service.getAllItems();

            int totalItems = items.size();
            int lowStockCount = service.getLowStockItems().size();

            Set<String> suppliers = new HashSet<>();

            double totalValue = 0;

            for (InventoryItem item : items) {

                if (item.getSupplier() != null &&
                        !item.getSupplier().trim().isEmpty()) {

                    suppliers.add(item.getSupplier().trim());
                }

                totalValue += item.getQuantity() * item.getPrice();
            }

            totalItemsLabel.setText(String.valueOf(totalItems));

            lowStockLabel.setText(String.valueOf(lowStockCount));

            totalSuppliersLabel.setText(
                    String.valueOf(suppliers.size()));

            inventoryValueLabel.setText(
                    String.format("₹%.2f", totalValue));

            JOptionPane.showMessageDialog(
                    this, "Inventory report generated successfully!");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate report: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Reports reports = new Reports();
            reports.setVisible(true);
        });
    }
}
