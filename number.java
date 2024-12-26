import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class number {
    private static final int MAX_ATTEMPTS = 5;
    private static int score = 0;
    private static int attempts;
    private static int numberToGuess;
    private static boolean hasGuessedCorrectly;
    private static JLabel feedbackLabel;
    private static JLabel attemptsLabel;
    private static JTextField guessField;
    private static JButton guessButton;
    private static JButton playAgainButton;
    private static JLabel scoreLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to the Guessing Game! Guess the number between 1 and 100.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel);

        guessField = new JTextField();
        guessField.setMaximumSize(new Dimension(Integer.MAX_VALUE, guessField.getPreferredSize().height));
        guessField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(guessField);

        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(guessButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(feedbackLabel);

        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(attemptsLabel);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(scoreLabel);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 14));
        playAgainButton.setVisible(false); // Initially hidden
        panel.add(playAgainButton);

        frame.add(panel);
        frame.setVisible(true);

        startNewGame();

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
    }

    private static void startNewGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1; // Generate random number between 1 and 100
        attempts = 0;
        hasGuessedCorrectly = false;
        guessField.setText("");
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + (MAX_ATTEMPTS - attempts));
        playAgainButton.setVisible(false);
        guessButton.setEnabled(true);
    }

    private static void handleGuess() {
        if (!hasGuessedCorrectly && attempts < MAX_ATTEMPTS) {
            int userGuess;
            try {
                userGuess = Integer.parseInt(guessField.getText());
            } catch (NumberFormatException e) {
                feedbackLabel.setText("Please enter a valid number.");
                return;
            }

            attempts++;

            if (userGuess == numberToGuess) {
                feedbackLabel.setText("Congratulations! You've guessed the correct number.");
                hasGuessedCorrectly = true;
                score++;
                scoreLabel.setText("Score: " + score);
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            } else if (userGuess < numberToGuess) {
                feedbackLabel.setText("Too low! Try again.");
            } else {
                feedbackLabel.setText("Too high! Try again.");
            }

            attemptsLabel.setText("Attempts left: " + (MAX_ATTEMPTS - attempts));

            if (attempts == MAX_ATTEMPTS && !hasGuessedCorrectly) {
                feedbackLabel.setText("You've run out of attempts. The correct number was: " + numberToGuess);
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            }
        }
    }
}

