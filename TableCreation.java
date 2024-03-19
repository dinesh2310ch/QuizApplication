import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation extends JFrame {
    private String url = "jdbc:mysql://localhost:3306/";
    private String databaseName = "QuizApp";
    private String userName = "root";
    private String password = "Sw@rn@l777";
    private JTextField nameField, userField, collegeField;
    private JPasswordField passField;
    private JButton registerButton;

    public TableCreation() {
        createDatabase();
        createTable();

        

        nameField = new JTextField(20);
        userField = new JTextField(20);
        collegeField = new JTextField(20);
        passField = new JPasswordField(20);
        registerButton = new JButton("Register");

        enhanceComponentVisual(nameField);
        enhanceComponentVisual(userField);
        enhanceComponentVisual(collegeField);
        enhanceComponentVisual(passField);
        styleButton(registerButton, new Color(60, 179, 113), Color.WHITE); // Forest Green

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50); // Adjust for better layout

        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background

        // Add components
        addComponent(new JLabel("Name:"), gbc);
        addComponent(nameField, gbc);
        addComponent(new JLabel("Username:"), gbc);
        addComponent(userField, gbc);
        addComponent(new JLabel("Password:"), gbc);
        addComponent(passField, gbc);
        addComponent(new JLabel("College Name:"), gbc);
        addComponent(collegeField, gbc);
        gbc.weighty = 1;
        addComponent(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String collegeName = collegeField.getText();
            registerUser(name, username, password, collegeName);
        });

        setTitle("User Registration");
        setSize(350, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addComponent(Component component, GridBagConstraints gbc) {
        gbc.anchor = GridBagConstraints.CENTER;
        add(component, gbc);
    }

    private void enhanceComponentVisual(JComponent component) {
        component.setFont(new Font("Arial", Font.PLAIN, 14));
        if (component instanceof JTextField) {
            ((JTextField) component).setColumns(15);
        }
        component.setBorder(BorderFactory.createCompoundBorder(
                component.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement stm = conn.createStatement()) {
            String query = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            stm.executeUpdate(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to create database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createTable() {
        String fullUrl = url + databaseName + "?useSSL=false";
        try (Connection conn = DriverManager.getConnection(fullUrl, userName, password);
             Statement stm = conn.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS users (" +
                           "id INT AUTO_INCREMENT PRIMARY KEY, " +
                           "name VARCHAR(50), " +
                           "username VARCHAR(50) UNIQUE, " +
                           "password VARCHAR(50), " +
                           "collegeName VARCHAR(100))";
            stm.executeUpdate(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to create table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registerUser(String name, String username, String password, String collegeName) {
        String fullUrl = url + databaseName + "?useSSL=false";
        String query = "INSERT INTO users (name, username, password, collegeName) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(fullUrl, this.userName, this.password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password); // Consider hashing the password
            pstmt.setString(4, collegeName);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                // Consider what should happen next, e.g., clear the form or open another window
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    new Login().setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "User registration failed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TableCreation());
    }
}
