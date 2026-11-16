package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Suppliers extends JFrame {

    private JTable supplierTable;

    public Suppliers() {

        setTitle("Supplier Management");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // Heading
        JLabel heading = new JLabel(
                "SUPPLIER MANAGEMENT",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        mainPanel.add(heading, BorderLayout.NORTH);

        // Table columns
        String[] columns = {
                "Supplier ID",
                "Supplier Name",
                "Contact",
                "Email",
                "Address"
        };

        // Sample data
        Object[][] data = {
                {1, "ABC Electronics", "9876543210",
                        "abc@gmail.com", "Jaipur"},

                {2, "XYZ Suppliers", "9876501234",
                        "xyz@gmail.com", "Delhi"},

                {3, "Tech World", "9876512345",
                        "tech@gmail.com", "Chandigarh"}
        };

        DefaultTableModel model =
                new DefaultTableModel(data, columns);

        supplierTable = new JTable(model);

        supplierTable.setRowHeight(30);

        supplierTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(supplierTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Supplier");
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

        // Add Supplier
        addButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Add Supplier feature will be connected later."
            );
        });

        // Update
        updateButton.addActionListener(e -> {

            int selectedRow =
                    supplierTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a supplier to update."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Update feature will be connected later."
                );
            }
        });

        // Delete
        deleteButton.addActionListener(e -> {

            int selectedRow =
                    supplierTable.getSelectedRow();

            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a supplier to delete."
                );

            } else {

                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(
                        this,
                        "Supplier deleted successfully!"
                );
            }
        });

        // Refresh
        refreshButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier list refreshed!"
            );
        });

        // Back
        backButton.addActionListener(e -> dispose());

        add(mainPanel);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Suppliers suppliers = new Suppliers();

            suppliers.setVisible(true);
        });
    }
}