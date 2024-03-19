import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class QuizTopicSel extends JFrame implements ActionListener {

    private JLabel instructionLabel;
    private String fullText = "Please select the type of quiz you want to take..... ";
    private int pos = 0;

    public QuizTopicSel() {
        super("Select Topic for the Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon("kl1.jpeg");
        Image backgroundImage = backgroundIcon.getImage();

        // Create a panel that can display the background image
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        setContentPane(backgroundPanel); // Use the background panel as the content pane

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Make the panel transparent to show the background image
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        instructionLabel = new JLabel();
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setForeground(Color.BLACK); 
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        addButton("Python Quiz", panel, new Color(100, 149, 237));
        addButton("HTML Quiz", panel, new Color(255, 165, 0));
        addButton("JAVA Quiz", panel, new Color(34, 139, 34));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        backgroundPanel.add(panel, gbc);

        initMovingText();
        setVisible(true);
    }

        private void addButton(String text, JPanel panel, Color bgColor) {
                JButton button = new JButton(text);
                button.addActionListener(this);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setFont(new Font("Arial", Font.PLAIN, 14));
                button.setMinimumSize(new Dimension(200, 50)); // Ensures all buttons have the same size
                button.setPreferredSize(new Dimension(200, 50)); // Preferable size of the buttons
                button.setMaximumSize(new Dimension(200, 50)); // Maximum size
                button.setMargin(new Insets(10, 20, 10, 20)); // Button padding
                button.setBackground(bgColor);
                button.setForeground(Color.WHITE); // White text
                panel.add(button);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        private void initMovingText() {
                Timer timer = new Timer(200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Update the displayed text by moving the starting position
                        String displayText = fullText.substring(pos) + fullText.substring(0, pos);
                        instructionLabel.setText(displayText);
                        pos = (pos + 1) % fullText.length();
                    }
                });
                timer.start();
            }

        @Override
        public void actionPerformed(ActionEvent e) {
                if ("Python Quiz".equals(e.getActionCommand())) {
                        this.dispose(); // Close the current window
                        ArrayList<Question> questions = new ArrayList<>();
                        questions.add(new Question("What is Python?",
                                        new String[] { "Programming Language", "Snake", "Game", "Software" }, 0));
                        questions.add(new Question("Which of these data types is not built-in in Python?",
                                        new String[] { "List", "Dictionary", "Tree", "Tuple" }, 2));
                        questions.add(new Question("What keyword is used to define a function in Python?",
                                        new String[] { "fun", "define", "def", "function" }, 2));
                        questions.add(new Question("How do you insert COMMENTS in Python code?",
                                        new String[] { "// comment", "# comment", "/* comment */", "<!-- comment -->" },
                                        1));
                        questions.add(new Question(
                                        "Which of the following is the correct extension of the Python file?",
                                        new String[] { ".pyth", ".pt", ".py", ".p" }, 2));
                        questions.add(new Question(
                                        "What does the len() function do?",
                                        new String[] { "Returns the length of an object",
                                                        "Counts the number of times an object appears",
                                                        "Calculates the sum", "None of the above" },
                                        0));
                        questions.add(new Question(
                                        "Which of the following is used to define a block of code in Python language?",
                                        new String[] { "Indentation", "Key", "Brackets", "None of the above" }, 0));
                        questions.add(new Question(
                                        "Which of the following statements is correct regarding the object-oriented programming concept in Python?",
                                        new String[] { "Classes are real-world entities while objects are not real",
                                                        "Objects are real-world entities while classes are not real",
                                                        "Both classes and objects are real-world entities",
                                                        "Classes and objects do not exist in real-world" },
                                        1));
                        questions.add(new Question("What is the method inside the class in python language?",
                                        new String[] { "Object", "Function", "Attribute", "Argument" }, 1));
                        questions.add(new Question("Which of the following is not a keyword in Python language?",
                                        new String[] { "val", "raise", "try", "with" }, 0));

                        SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                        new PythonQuiz(questions).setVisible(true);
                                }
                        });
                } else if ("HTML Quiz".equals(e.getActionCommand())) {
                        this.dispose(); // Close the current window
                        ArrayList<Question> questions = new ArrayList<>();
                        questions.add(new Question("What does HTML stand for?",
                                        new String[] { "Hyper Trainer Marking Language",
                                                        "Hyper Text Marketing Language", "Hyper Text Markup Language",
                                                        "Hyper Text Markup Leveler" },
                                        2));
                        questions.add(new Question("Which HTML tag is used to define an internal style sheet?",
                                        new String[] { "<script>", "<css>", "<style>", "<link>" }, 2));
                        questions.add(new Question("Which HTML attribute is used to define inline styles?",
                                        new String[] { "class", "style", "styles", "font" }, 1));
                        questions.add(new Question(
                                        "Which HTML element is used for specifying a footer for a document or section?",
                                        new String[] { "<bottom>", "<footer>", "<section>", "<div>" }, 1));
                        questions.add(
                                        new Question("In HTML, which attribute is used to specify that an input field must be filled out?",
                                                        new String[] { "placeholder", "required", "validate",
                                                                        "important" },
                                                        1));
                        questions.add(new Question(
                                        "Which HTML element is used to specify a header for a document or section?",
                                        new String[] { "<header>", "<head>", "<top>", "<section>" }, 0));
                        questions.add(new Question(
                                        "Which of the following HTML elements is used to define navigation links?",
                                        new String[] { "<nav>", "<navigation>", "<navigate>", "<links>" }, 0));
                        questions.add(new Question("Which HTML element defines the title of a document?",
                                        new String[] { "<meta>", "<title>", "<head>", "<header>" }, 1));
                        questions.add(
                                        new Question("Which HTML attribute is used to define the character encoding for an HTML document?",
                                                        new String[] { "charset", "encoding", "setchar", "char" }, 0));
                        questions.add(new Question(
                                        "Which HTML element is used to display a scalar measurement within a range?",
                                        new String[] { "<gauge>", "<measure>", "<range>", "<meter>" }, 3));

                        SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                        new htmlQuiz(questions).setVisible(true);
                                }
                        });

                } else if ("JAVA Quiz".equals(e.getActionCommand())) {
                        this.dispose(); // Close the current window
                        ArrayList<Question> questions = new ArrayList<>();
                        questions.add(new Question("What is Java?",
                                        new String[] { "A coffee brand", "A programming language", "An island",
                                                        "A game" },
                                        1));
                        questions.add(new Question("Which of these data types is not built-in in Java?",
                                        new String[] { "int", "float", "string", "byte" }, 2));
                        questions.add(new Question("How do you declare a variable in Java?",
                                        new String[] { "var name", "int name", "variable name", "name int" }, 1));
                        questions.add(new Question("Which keyword is used for inheritance in Java?",
                                        new String[] { "extends", "implements", "inherits", "super" }, 0));
                        questions.add(new Question("How do you instantiate an object in Java?",
                                        new String[] { "new ClassName()", "ClassName.new()", "create ClassName()",
                                                        "ClassName.create()" },
                                        0));
                        questions.add(new Question("Which of the following is an interface in Java?",
                                        new String[] { "ArrayList", "List", "LinkedList", "Vector" }, 1));
                        questions.add(new Question("What does the 'final' keyword signify in Java?",
                                        new String[] { "The value can be changed",
                                                        "The value cannot be changed after initialization",
                                                        "It is used to finalize objects",
                                                        "It indicates the end of a loop" },
                                        1));
                        questions.add(new Question("Which of these is used to handle exceptions in Java?",
                                        new String[] { "try-catch", "error-check", "exception-block", "fault-segment" },
                                        0));
                        questions.add(new Question("What is the entry point of a Java application?",
                                        new String[] { "start()", "main()", "JavaMain()", "ApplicationStart()" }, 1));
                        questions.add(new Question("Which of the following is not a valid Java identifier?",
                                        new String[] { "_name", "$name", "2name", "name2" }, 2));

                        SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                        new javaQuiz(questions).setVisible(true);
                                }
                        });

                } else {
                        JOptionPane.showMessageDialog(null, "No quiz selected or quiz type unknown!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }

        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> new QuizTopicSel());
        }
}
