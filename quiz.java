import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class quiz {
    private static List<Question> questions;
    private static int currentQuestionIndex = 0;
    private static int score = 0;
    private static Timer timer;
    private static int timeLimit = 10; // Time limit in seconds

    private static JLabel questionLabel;
    private static JRadioButton[] options;
    private static JButton submitButton;
    private static JLabel timerLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(questionLabel);

        ButtonGroup optionGroup = new ButtonGroup();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("");
            options[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionGroup.add(options[i]);
            panel.add(options[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(submitButton);

        timerLabel = new JLabel("Time left: " + timeLimit + " seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(timerLabel);

        frame.add(panel);
        frame.setVisible(true);

        loadQuestions();
        displayQuestion();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        startTimer();
    }

    private static void loadQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[]{"Paris", "Berlin", "Madrid", "Rome"}, 0));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1));
        questions.add(new Question("Who wrote 'Hamlet'?", new String[]{"Shakespeare", "Dante", "Homer", "Chaucer"}, 0));
        questions.add(new Question("What is the smallest prime number?", new String[]{"0", "1", "2", "3"}, 2));
    }

    private static void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestion());
            for (int i = 0; i < options.length; i++) {
                options[i].setText(currentQuestion.getOptions()[i]);
                options[i].setSelected(false);
            }
        } else {
            showResult();
        }
    }

    private static void submitAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int selectedOption = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }
        if (selectedOption == currentQuestion.getCorrectOption()) {
            score++;
        }
        currentQuestionIndex++;
        resetTimer();
        displayQuestion();
    }

    private static void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int timeRemaining = timeLimit;

            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText("Time left: " + timeRemaining + " seconds");
                } else {
                    submitAnswer();
                }
            }
        }, 0, 1000);
    }

    private static void resetTimer() {
        timer.cancel();
        timer.purge();
        startTimer();
    }

    private static void showResult() {
        StringBuilder resultSummary = new StringBuilder();
        resultSummary.append("Your final score: ").append(score).append("/").append(questions.size()).append("\n");
        resultSummary.append("Summary of your answers:\n");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            resultSummary.append("Q").append(i + 1).append(": ").append(question.getQuestion()).append("\n");
            resultSummary.append("Correct answer: ").append(question.getOptions()[question.getCorrectOption()]).append("\n");
        }
        JOptionPane.showMessageDialog(null, resultSummary.toString(), "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
