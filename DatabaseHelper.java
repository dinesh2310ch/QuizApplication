import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/QuizApp";
    private static final String USER = "root";
    private static final String PASS = ""; // Use your actual password

    public DatabaseHelper() {
        // Attempt to establish a connection to the database and create the feedback table if it doesn't exist
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // SQL statement now includes user_id as a FOREIGN KEY
            String sqlCreateFeedbackTable = "CREATE TABLE IF NOT EXISTS feedback (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "score INT NOT NULL, " +
                    "feedback_text TEXT NOT NULL, " +
                    "user_id INT, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id) " +
                    ");";

            try (PreparedStatement pstmt = conn.prepareStatement(sqlCreateFeedbackTable)) {
                pstmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFeedback(int score, String feedback, int userId) {
        // Updated to include user_id in the INSERT statement
        String sqlInsertFeedback = "INSERT INTO feedback(score, feedback_text, user_id) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsertFeedback)) {
            pstmt.setInt(1, score);
            pstmt.setString(2, feedback);
            pstmt.setInt(3, userId); // Setting user_id
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
