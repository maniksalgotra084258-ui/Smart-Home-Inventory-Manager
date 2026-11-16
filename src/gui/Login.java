
package gui;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton showButton;
    private JButton loginButton;
    private boolean passwordVisible = false;

    private static final Color NAVY = new Color(15, 35, 65);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color BLUE_HOVER = new Color(29, 78, 216);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER = new Color(203, 213, 225);

    public Login() {

        setTitle("Smart Home Inventory Manager");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel background = new BackgroundPanel();
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        ShadowPanel card = new ShadowPanel();
        card.setLayout(new GridLayout(1, 2));
        card.setPreferredSize(new Dimension(900, 530));

        background.add(card);

        JPanel brandingPanel = createBrandingPanel();
        JPanel loginPanel = createLoginPanel();

        card.add(brandingPanel);
        card.add(loginPanel);
    }

    private JPanel createBrandingPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(NAVY);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(40, 45, 40, 45));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("⌂");
        icon.setFont(new Font("Arial", Font.PLAIN, 65));
        icon.setForeground(Color.WHITE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SMART HOME");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title2 = new JLabel("INVENTORY MANAGER");
        title2.setFont(new Font("Arial", Font.BOLD, 18));
        title2.setForeground(new Color(147, 197, 253));
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Manage your household inventory<br>"
                        + "easily and intelligently."
                        + "</div></html>"
        );

        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setForeground(new Color(226, 232, 240));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(icon);
        content.add(Box.createVerticalStrut(10));
        content.add(title);
        content.add(title2);
        content.add(Box.createVerticalStrut(25));
        content.add(description);
        content.add(Box.createVerticalStrut(35));

        JLabel feature1 = createFeature("✓ Track household items");
        JLabel feature2 = createFeature("✓ Monitor stock levels");
        JLabel feature3 = createFeature("✓ Get low-stock alerts");
        JLabel feature4 = createFeature("✓ Manage everything easily");

        content.add(feature1);
        content.add(Box.createVerticalStrut(12));
        content.add(feature2);
        content.add(Box.createVerticalStrut(12));
        content.add(feature3);
        content.add(Box.createVerticalStrut(12));
        content.add(feature4);

        panel.add(content);

        return panel;
    }

    private JLabel createFeature(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    private JPanel createLoginPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setFont(new Font("Arial", Font.BOLD, 30));
        welcome.setForeground(TEXT);
        welcome.setBounds(55, 55, 350, 40);
        panel.add(welcome);

        JLabel subtitle = new JLabel("Login to manage your home inventory");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);
        subtitle.setBounds(55, 98, 350, 25);
        panel.add(subtitle);

        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        usernameLabel.setForeground(TEXT);
        usernameLabel.setBounds(55, 150, 150, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        styleTextField(usernameField);
        usernameField.setBounds(55, 177, 370, 46);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passwordLabel.setForeground(TEXT);
        passwordLabel.setBounds(55, 235, 150, 25);
        panel.add(passwordLabel);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBounds(55, 262, 370, 46);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setForeground(TEXT);
        passwordField.setBackground(Color.WHITE);
        passwordField.setEchoChar('•');

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        BorderFactory.createEmptyBorder(5, 13, 5, 5)
                )
        );

        passwordPanel.add(passwordField, BorderLayout.CENTER);

        showButton = new JButton("SHOW");
        showButton.setFont(new Font("Arial", Font.BOLD, 11));
        showButton.setForeground(BLUE);
        showButton.setBackground(Color.WHITE);
        showButton.setOpaque(true);
        showButton.setContentAreaFilled(true);
        showButton.setBorderPainted(false);
        showButton.setFocusPainted(false);
        showButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showButton.setPreferredSize(new Dimension(65, 44));

        showButton.addActionListener(e -> togglePassword());

        passwordPanel.add(showButton, BorderLayout.EAST);

        panel.add(passwordPanel);

        loginButton = new RoundedButton("LOGIN");
        loginButton.setBounds(55, 340, 370, 48);
        loginButton.addActionListener(e -> loginUser());
        panel.add(loginButton);

        JButton signupButton = new JButton("Don't have an account?  SIGN UP");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 13));
        signupButton.setForeground(BLUE);
        signupButton.setBackground(Color.WHITE);
        signupButton.setBorderPainted(false);
        signupButton.setFocusPainted(false);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.setBounds(105, 410, 270, 35);

        signupButton.addActionListener(e -> {
            new Signup().setVisible(true);
            dispose();
        });

        panel.add(signupButton);

        return panel;
    }

    private void styleTextField(JTextField field) {

        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        BorderFactory.createEmptyBorder(5, 13, 5, 13)
                )
        );
    }

    private void togglePassword() {

        if (passwordVisible) {
            passwordField.setEchoChar('•');
            showButton.setText("SHOW");
            passwordVisible = false;
        } else {
            passwordField.setEchoChar((char) 0);
            showButton.setText("HIDE");
            passwordVisible = true;
        }

        passwordField.requestFocusInWindow();
    }

    private void loginUser() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = SHA2(?, 256)";

        try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new Dashboard().setVisible(true);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    static class BackgroundPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            int width = getWidth();
            int height = getHeight();

            GradientPaint gradient = new GradientPaint(
                    0,
                    0,
                    new Color(239, 246, 255),
                    width,
                    height,
                    new Color(219, 234, 254)
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, width, height);

            g2.dispose();
        }
    }

    static class ShadowPanel extends JPanel {

        public ShadowPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(5, 8, w - 10, h - 10, 25, 25);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, w - 10, h - 10, 25, 25);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    static class RoundedButton extends JButton {

        private boolean hover = false;

        public RoundedButton(String text) {

            super(text);

            setFont(new Font("Arial", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setBackground(BLUE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {

                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {

                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (hover) {
                g2.setColor(BLUE_HOVER);
            } else {
                g2.setColor(BLUE);
            }

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    15,
                    15
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}
