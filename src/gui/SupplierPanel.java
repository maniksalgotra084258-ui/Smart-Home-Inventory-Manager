package gui;

import service.SupplierService;
import model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierPanel extends JPanel {

    private JTextField nameField, contactField, emailField, addressField;
    private JTable supplierTable;
    private DefaultTableModel tableModel;

    private SupplierService supplierService = new SupplierService();

    public SupplierPanel() {

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Supplier Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        form.add(new JLabel("Supplier Name:"));
        form.add(nameField);

        form.add(new JLabel("Contact:"));
        form.add(contactField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Address:"));
        form.add(addressField);

        JButton addButton = new JButton("Add Supplier");
        JButton refreshButton = new JButton("Refresh");

        form.add(addButton);
        form.add(refreshButton);

        add(form, BorderLayout.WEST);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Contact", "Email", "Address"}, 0
        );

        supplierTable = new JTable(tableModel);
        add(new JScrollPane(supplierTable), BorderLayout.CENTER);

        addButton.addActionListener(e -> addSupplier());
        refreshButton.addActionListener(e -> loadSuppliers());

        loadSuppliers();
    }

    private void addSupplier() {

        Supplier supplier = new Supplier(
                0,
                nameField.getText(),
                contactField.getText(),
                emailField.getText(),
                addressField.getText()
        );

        if (supplierService.addSupplier(supplier)) {
            JOptionPane.showMessageDialog(this, "Supplier added successfully!");
            clearFields();
            loadSuppliers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add supplier!");
        }
    }

    private void loadSuppliers() {

        tableModel.setRowCount(0);

        for (Supplier s : supplierService.getAllSuppliers()) {
            tableModel.addRow(new Object[]{
                    s.getSupplierId(),
                    s.getSupplierName(),
                    s.getContact(),
                    s.getEmail(),
                    s.getAddress()
            });
        }
    }

    private void clearFields() {
        nameField.setText("");
        contactField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}