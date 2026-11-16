package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public Signup() {

        setTitle("Smart Home Inventory Manager - Create Account");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(25, 55, 95));

        JLabel homeIcon = new JLabel("🏠");
        homeIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 65));
        homeIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SMART HOME");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("INVENTORY MANAGER");
        title2.setFont(new Font("Arial", Font.BOLD, 22));
        title2.setForeground(new Color(180, 220, 255));
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "<html><center>Create your account and<br>"
                        + "manage your smart home inventory.</center></html>"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(homeIcon);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(title);
        leftPanel.add(title2);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(subtitle);
        leftPanel.add(Box.createVerticalGlue());

        // RIGHT PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        JLabel signupTitle = new JLabel("Create Account");
        signupTitle.setFont(new Font("Arial", Font.BOLD, 30));
        signupTitle.setForeground(new Color(25, 55, 95));
        signupTitle.setBounds(70, 35, 350, 40);

        JLabel signupSubtitle = new JLabel("Enter your details below");
        signupSubtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        signupSubtitle.setForeground(Color.GRAY);
        signupSubtitle.setBounds(72, 75, 300, 25);

        // USERNAME
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(70, 115, 150, 25);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField.setBounds(70, 142, 350, 40);

        // PASSWORD
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(70, 190, 150, 25);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setBounds(70, 217, 350, 40);

        // SHOW PASSWORD
        JButton showPasswordButton = new JButton("👁");
        showPasswordButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        showPasswordButton.setBounds(375, 222, 40, 30);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setBackground(Color.WHITE);

        showPasswordButton.addActionListener(e -> {

            if (passwordField.getEchoChar() != (char) 0) {
                passwordField.setEchoChar((char) 0);
                showPasswordButton.setText("🙈");
            } else {
                passwordField.setEchoChar('•');
                showPasswordButton.setText("👁");
            }
        });

        // CONFIRM PASSWORD
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setBounds(70, 265, 180, 25);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 15));
        confirmPasswordField.setBounds(70, 292, 350, 40);

        // CREATE ACCOUNT
        JButton createButton = new JButton("CREATE ACCOUNT");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setForeground(Color.WHITE);
        createButton.setBackground(new Color(25, 110, 190));
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.setBounds(70, 355, 210, 45);

        // BACK TO LOGIN
        JButton backButton = new JButton("BACK TO LOGIN");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(new Color(25, 55, 95));
        backButton.setBackground(new Color(230, 235, 240));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setBounds(295, 355, 125, 45);

        createButton.addActionListener(e -> createAccount());

        backButton.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        rightPanel.add(signupTitle);
        rightPanel.add(signupSubtitle);
        rightPanel.add(usernameLabel);
        rightPanel.add(usernameField);
        rightPanel.add(passwordLabel);
        rightPanel.add(passwordField);
        rightPanel.add(showPasswordButton);
        rightPanel.add(confirmLabel);
        rightPanel.add(confirmPasswordField);
        rightPanel.add(createButton);
        rightPanel.add(backButton);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
    }

    private void createAccount() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql = "INSERT INTO users (username, password_hash) " +
                "VALUES (?, SHA2(?, 256))";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new Login().setVisible(true);
            dispose();

        } catch (Exception e) {

            if (e.getMessage() != null &&
                    e.getMessage().contains("Duplicate entry")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists!",
                        "Signup Failed",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Database error!\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Signup().setVisible(true);
        });
    }
}