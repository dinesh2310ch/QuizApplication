import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class htmlQuiz extends JFrame {
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private ButtonGroup optionsGroup;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton nextButton, backButton, submitButton, closeButton;
    private ArrayList<String> userAnswers;

    public htmlQuiz(ArrayList<Question> questions) {
        this.questions = questions;
        this.userAnswers = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add(""); // Initialize user answers with empty strings
        }
        
        try {
            // Set Nimbus look and feel for a modern UI appearance
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the system look and feel or another look and feel
        }

        setTitle("HTML Quiz");
        setSize(900, 600); // Adjusted for a more standard aspect ratio
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Updated font

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(new Color(235, 245, 251)); // A soft blue background

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        nextButton = new JButton("Next");
        backButton = new JButton("Back");
        submitButton = new JButton("Submit");
        closeButton = new JButton("Close");

        // Updating fonts and button colors
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        nextButton.setFont(buttonFont);
        backButton.setFont(buttonFont);
        submitButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);

        // Setting button background colors
        Color buttonColor = new Color(220, 240, 255); // A light blue
        nextButton.setBackground(buttonColor);
        backButton.setBackground(buttonColor);
        submitButton.setBackground(buttonColor);
        closeButton.setBackground(buttonColor);

        // Setting hover effects for buttons
        JButton[] buttons = {nextButton, backButton, submitButton, closeButton};
        for (JButton button : buttons) {
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(buttonColor.brighter());
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(buttonColor);
                }
            });
        }

        setupButtonActions();

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(closeButton);

        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        navigateTo(0); // Start with the first question

        setLocationRelativeTo(null); // Center the application window
    }

    private void setupButtonActions() {
        backButton.addActionListener(e -> {
            saveAnswer();
            navigateTo(currentQuestionIndex - 1);
        });

        nextButton.addActionListener(e -> {
            saveAnswer();
            navigateTo(currentQuestionIndex + 1);
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAnswer(); // Save the answer for the last question
                int score = calculateScore();
                JOptionPane.showMessageDialog(htmlQuiz.this, "Your score is: " + score + "/" + questions.size());
        
                // Collect feedback
                String feedback = JOptionPane.showInputDialog(htmlQuiz.this, "Please provide your feedback:");
                if (feedback != null && !feedback.trim().isEmpty()) {
                    // Store feedback in database
                    DatabaseHelper dbHelper = new DatabaseHelper();
                    // Pass the currentUserId along with score and feedback
                    int currentUserId = SessionManager.getCurrentUserId();
                    dbHelper.insertFeedback(score, feedback, currentUserId);
                    
                   
                    JOptionPane.showMessageDialog(htmlQuiz.this, "Thank you for your feedback!");
                }
            }
        });
        
        
        // Inside the HTMLQuiz constructor after initializing other components

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Serif", Font.BOLD, 16));

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This will close the current window
                htmlQuiz.this.dispose();
            }
        });

        closeButton.addActionListener(e -> {
            htmlQuiz.this.dispose(); // Close the application window
        });
    }

    private void navigateTo(int index) {
        if (index < 0 || index >= questions.size())
            return;
        currentQuestionIndex = index;
        Question currentQuestion = questions.get(currentQuestionIndex);

        questionLabel.setText((currentQuestionIndex + 1) + ". " + currentQuestion.getQuestionText());
        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();
        for (String option : currentQuestion.getOptions()) {
            JRadioButton optionButton = new JRadioButton(option);
            optionButton.setActionCommand(option);
            optionButton.setFont(new Font("Serif", Font.PLAIN, 18)); // Set font for options
            optionsGroup.add(optionButton);
            optionsPanel.add(optionButton);
            if (option.equals(userAnswers.get(currentQuestionIndex))) {
                optionButton.setSelected(true);
            }
        }

        backButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
        submitButton.setEnabled(currentQuestionIndex == questions.size() - 1);

        validate();
        repaint();
    }

    private void saveAnswer() {
        if (optionsGroup != null && optionsGroup.getSelection() != null) {
            String selectedAnswer = optionsGroup.getSelection().getActionCommand();
            System.out.println("Saving answer: " + selectedAnswer); // Debugging line
            userAnswers.set(currentQuestionIndex, selectedAnswer);
        }
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex(); // Assume this method exists and returns
                                                                              // the index of the correct answer
            String[] options = currentQuestion.getOptions(); // Assume this method exists and returns the array of
                                                             // options
            String correctAnswer = options[correctAnswerIndex]; // Fetch the correct answer text using its index
            String userAnswer = userAnswers.get(i);

            if (userAnswer.equals(correctAnswer)) { // Now correctly comparing the text of the answers
                score++;
            }
        }
        return score;
    }

    public static void main(String[] args) {
        ArrayList<Question> questions = new ArrayList<>();
        // HTML questions
        questions.add(new Question(
                "What does HTML stand for?", new String[] { "Hyper Trainer Marking Language",
                        "Hyper Text Marketing Language", "Hyper Text Markup Language", "Hyper Text Markup Leveler" },
                2));
        questions.add(new Question("Which HTML tag is used to define an internal style sheet?",
                new String[] { "<script>", "<css>", "<style>", "<link>" }, 2));
        questions.add(new Question("Which HTML attribute is used to define inline styles?",
                new String[] { "class", "style", "styles", "font" }, 1));
        questions.add(new Question("Which HTML element is used for specifying a footer for a document or section?",
                new String[] { "<bottom>", "<footer>", "<section>", "<div>" }, 1));
        questions
                .add(new Question("In HTML, which attribute is used to specify that an input field must be filled out?",
                        new String[] { "placeholder", "required", "validate", "important" }, 1));
        questions.add(new Question("Which HTML element is used to specify a header for a document or section?",
                new String[] { "<header>", "<head>", "<top>", "<section>" }, 0));
        questions.add(new Question("Which of the following HTML elements is used to define navigation links?",
                new String[] { "<nav>", "<navigation>", "<navigate>", "<links>" }, 0));
        questions.add(new Question("Which HTML element defines the title of a document?",
                new String[] { "<meta>", "<title>", "<head>", "<header>" }, 1));
        questions
                .add(new Question("Which HTML attribute is used to define the character encoding for an HTML document?",
                        new String[] { "charset", "encoding", "setchar", "char" }, 0));
        questions.add(new Question("Which HTML element is used to display a scalar measurement within a range?",
                new String[] { "<gauge>", "<measure>", "<range>", "<meter>" }, 3));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new htmlQuiz(questions).setVisible(true);
            }
        });
    }
}
