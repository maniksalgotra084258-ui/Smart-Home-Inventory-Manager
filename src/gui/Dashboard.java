package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Smart Home Inventory Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();

        JLabel title = new JLabel(
                "SMART HOME INVENTORY MANAGER"
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        headerPanel.add(title);

        // Sidebar
        JPanel sidePanel = new JPanel(
                new GridLayout(7, 1, 5, 5)
        );

        sidePanel.setPreferredSize(
                new Dimension(200, 0)
        );

        JButton dashboardBtn =
                new JButton("Dashboard");

        JButton inventoryBtn =
                new JButton("Inventory");

        JButton addItemBtn =
                new JButton("Add Item");

        JButton suppliersBtn =
                new JButton("Suppliers");

        JButton lowStockBtn =
                new JButton("Low Stock Alert");

        JButton reportsBtn =
                new JButton("Reports");

        JButton exitBtn =
                new JButton("Exit");

        sidePanel.add(dashboardBtn);
        sidePanel.add(inventoryBtn);
        sidePanel.add(addItemBtn);
        sidePanel.add(suppliersBtn);
        sidePanel.add(lowStockBtn);
        sidePanel.add(reportsBtn);
        sidePanel.add(exitBtn);

        // Dashboard cards
        JPanel centerPanel = new JPanel(
                new GridLayout(2, 2, 20, 20)
        );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        centerPanel.add(
                createCard("Total Items", "10")
        );

        centerPanel.add(
                createCard("Low Stock Items", "3")
        );

        centerPanel.add(
                createCard("Suppliers", "5")
        );

        centerPanel.add(
                createCard("Inventory Value", "₹25,500")
        );

        // Inventory button
        inventoryBtn.addActionListener(e -> {
            new Inventory().setVisible(true);
        });

        // Add Item button
        addItemBtn.addActionListener(e -> {
            new AddItem().setVisible(true);
        });

        // Suppliers button
        suppliersBtn.addActionListener(e -> {
            new Suppliers().setVisible(true);
        });

        // Low Stock button
        lowStockBtn.addActionListener(e -> {
            new LowStockAlert().setVisible(true);
        });

        // Reports button
        reportsBtn.addActionListener(e -> {
            new Reports().setVisible(true);
        });

        // Exit button
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                sidePanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);
    }

    private JPanel createCard(
            String title,
            String value) {

        JPanel card = new JPanel(
                new BorderLayout()
        );

        card.setBorder(
                BorderFactory.createLineBorder(
                        Color.GRAY,
                        1
                )
        );

        JLabel titleLabel = new JLabel(
                title,
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        JLabel valueLabel = new JLabel(
                value,
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Dashboard dashboard =
                    new Dashboard();

            dashboard.setVisible(true);
        });
    }
}