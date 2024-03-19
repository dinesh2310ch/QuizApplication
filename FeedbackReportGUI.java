import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackReportGUI extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/QuizApp?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Sw@rn@l777";

    private JTable table;
    private DefaultTableModel tableModel;

    public FeedbackReportGUI() {
        setTitle("Feedback Report");
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(800, 75));
        JLabel headerLabel = new JLabel("Feedback Report");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Score", "Feedback"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make table cells non-editable
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true); // Enable sorting
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadData();
    }

    private void loadData() {
        String query = "SELECT users.name, feedback.score, feedback.feedback_text " +
                       "FROM users " +
                       "JOIN feedback ON users.id = feedback.user_id";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                String feedback = rs.getString("feedback_text");

                // Add data to table model
                tableModel.addRow(new Object[]{name, score, feedback});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeedbackReportGUI gui = new FeedbackReportGUI();
            gui.setVisible(true);
        });
    }
}
