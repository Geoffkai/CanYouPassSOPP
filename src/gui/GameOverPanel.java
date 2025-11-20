package gui;

import java.awt.*;
import javax.swing.*;
import logic.Player;

public class GameOverPanel extends JPanel {
    private BackgroundPanel backgroundPanel;
    private Dimension screenSize;
    private Player player;
    private int totalQuestions;
    private int correctAnswers;
    private boolean won;

    public GameOverPanel(Player player, int totalQuestions, int correctAnswers, boolean won) {
        this.player = player;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.won = won;

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(null);
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Create semi-transparent dark background
        backgroundPanel = new BackgroundPanel("src/img/InitialImg/GameScreen.png");
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
        backgroundPanel.setLayout(null);

        createGameOverUI();

        add(backgroundPanel);
        validate();
        repaint();
    }

    private void createGameOverUI() {
        // Create a dark overlay panel
        JPanel overlay = new JPanel();
        overlay.setBounds(0, 0, screenSize.width, screenSize.height);
        overlay.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black
        overlay.setLayout(null);

        // Game Over Title
        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 100, screenSize.width, 100);

        // Final Score
        JLabel scoreLabel = new JLabel("Final Score: " + (player != null ? player.getScore() : 0),
                SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 48));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBounds(0, 250, screenSize.width, 60);

        // Correct Answers
        JLabel correctLabel = new JLabel("Correct Answers: " + correctAnswers + " / " + totalQuestions,
                SwingConstants.CENTER);
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        correctLabel.setForeground(Color.WHITE);
        correctLabel.setBounds(0, 330, screenSize.width, 50);

        // Message based on win/loss
        String message = getGameOverMessage();
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 32));
        messageLabel.setForeground(won ? Color.GREEN : Color.ORANGE);
        messageLabel.setBounds(0, 400, screenSize.width, 60);

        // Play Again Button
        JButton playAgainBtn = new JButton("Play Again");
        playAgainBtn.setFont(new Font("Arial", Font.BOLD, 24));
        playAgainBtn.setBounds(screenSize.width / 2 - 200, 500, 180, 60);
        playAgainBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new Menu());
            topFrame.validate();
            topFrame.repaint();
        });

        // Main Menu Button
        JButton mainMenuBtn = new JButton("Main Menu");
        mainMenuBtn.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuBtn.setBounds(screenSize.width / 2 + 20, 500, 180, 60);
        mainMenuBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new Menu());
            topFrame.validate();
            topFrame.repaint();
        });

        overlay.add(titleLabel);
        overlay.add(scoreLabel);
        overlay.add(correctLabel);
        overlay.add(messageLabel);
        overlay.add(playAgainBtn);
        overlay.add(mainMenuBtn);

        backgroundPanel.add(overlay);
    }

    private String getGameOverMessage() {
        if (won) {
            return "Congratulations! You are a Survey of Programming Paradigms Passer!";
        } else if (correctAnswers >= totalQuestions * 0.7) {
            return "Good effort! You're getting there!";
        } else if (correctAnswers >= totalQuestions * 0.5) {
            return "Not bad! Keep practicing!";
        } else {
            return "Better luck next time! Study more and try again!";
        }
    }
}
