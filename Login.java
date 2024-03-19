import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    private String url = "jdbc:mysql://localhost:3306/QuizApp?useSSL=false";
    private String userName = "root";
    private String password = ""; // Use your actual password

    public Login() {
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("New Registration");

        // Enhance fields and buttons visually
        enhanceComponentVisual(userField);
        enhanceComponentVisual(passField);
        styleButton(loginButton, new Color(100, 149, 237), Color.WHITE);
        styleButton(registerButton, new Color(255, 69, 0), Color.WHITE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(new Color(245, 245, 245)); // Light gray background

        addCenteredComponent(contentPane, new JLabel("Username:"));
        addCenteredComponent(contentPane, userField);
        addCenteredComponent(contentPane, new JLabel("Password:"));
        addCenteredComponent(contentPane, passField);
        addCenteredComponent(contentPane, loginButton);
        addCenteredComponent(contentPane, registerButton);

        loginButton.addActionListener(e -> authenticateUser(userField.getText(), new String(passField.getPassword())));
        registerButton.addActionListener(e -> {
            new TableCreation().setVisible(true); // Assuming TableCreation is your registration form.
            Login.this.dispose();
        });

        setTitle("User Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addCenteredComponent(Container container, Component component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(container.getBackground()); // Match container background
        panel.add(component);
        container.add(panel);
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
    private void authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(url, this.userName, this.password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, username);
            pstmt.setString(2, password);
    
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    // Store the user ID in SessionManager
                    SessionManager.setCurrentUserId(resultSet.getInt("id"));
    
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    this.dispose();
                    // Proceed to the next part of your application
                    SwingUtilities.invokeLater(() -> {
                        QuizTopicSel quizTopicSel = new QuizTopicSel();
                        quizTopicSel.setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed: Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred.");
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
