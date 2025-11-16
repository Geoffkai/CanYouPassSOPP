package src.gui;

import java.awt.*;
import javax.swing.*;
import src.logic.GameManager;
import src.logic.Player;
import src.logic.Question;
import src.logic.QuestionBank;

public class GameScreen extends JPanel {
        private GameManager gameManager;
        private Question currentQuestion;
        private JLabel questionLabel;
        private JLabel scoreLabel;

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/GameScreen.png");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Choices image
        ImageIcon A = new ImageIcon(
                        new ImageIcon("src/img/Choices/A.png").getImage().getScaledInstance(591, 84,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton Abtn = new JButton(A);
        ImageIcon B = new ImageIcon(
                        new ImageIcon("src/img/Choices/B.png").getImage().getScaledInstance(591, 84,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton Bbtn = new JButton(B);
        ImageIcon C = new ImageIcon(
                        new ImageIcon("src/img/Choices/C.png").getImage().getScaledInstance(591, 84,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton Cbtn = new JButton(C);
        ImageIcon D = new ImageIcon(
                        new ImageIcon("src/img/Choices/D.png").getImage().getScaledInstance(591, 84,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton Dbtn = new JButton(D);

        // Debug Tools image
        ImageIcon Ref = new ImageIcon(new ImageIcon("src/img/Debug Tools/Ref.png").getImage().getScaledInstance(324, 50,
                        java.awt.Image.SCALE_SMOOTH));
        JButton Refactor = new JButton(Ref);
        ImageIcon Cons = new ImageIcon(
                        new ImageIcon("src/img/Debug Tools/Cons.png").getImage().getScaledInstance(324, 50,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton Console = new JButton(Cons);
        ImageIcon Copy = new ImageIcon(
                        new ImageIcon("src/img/Debug Tools/CtrlC.png").getImage().getScaledInstance(324, 50,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton CtrlC = new JButton(Copy);
        ImageIcon AuDeb = new ImageIcon(
                        new ImageIcon("src/img/Debug Tools/AuDeb.png").getImage().getScaledInstance(324, 50,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton AutoDebug = new JButton(AuDeb);
        ImageIcon Ret = new ImageIcon(new ImageIcon("src/img/Debug Tools/Ret.png").getImage().getScaledInstance(324, 50,
                        java.awt.Image.SCALE_SMOOTH));
        JButton Return = new JButton(Ret);

        // Other Buttons
        ImageIcon Menu = new ImageIcon(new ImageIcon("src/img/Buttons/Menu.png").getImage().getScaledInstance(93, 93,
                        java.awt.Image.SCALE_SMOOTH));
        JButton MenuBtn = new JButton(Menu);
        ImageIcon Retry = new ImageIcon(new ImageIcon("src/img/Buttons/Retry.png").getImage().getScaledInstance(93, 93,
                        java.awt.Image.SCALE_SMOOTH));
        JButton RetryBtn = new JButton(Retry);
        ImageIcon Mute = new ImageIcon(new ImageIcon("src/img/Buttons/Mute.png").getImage().getScaledInstance(93, 93,
                        java.awt.Image.SCALE_SMOOTH));
        JButton MuteBtn = new JButton(Mute);

        JPanel character = new JPanel();

        public GameScreen() {
                setLayout(null);
                setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setLayout(null);

                // Initialize GameManager
                initializeGameManager();

                // Create question and score labels
                questionLabel = new JLabel("", SwingConstants.CENTER);
                questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
                questionLabel.setForeground(Color.WHITE);
                questionLabel.setBounds(200, 300, 1200, 100);

                scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setBounds(1400, 100, 400, 50);

                // Choices Buttons with action listeners
                Abtn.setBounds(137, 859, 591, 84);
                Abtn.addActionListener(e -> submitAnswer(0));

                Bbtn.setBounds(774, 859, 591, 84);
                Bbtn.addActionListener(e -> submitAnswer(1));

                Cbtn.setBounds(137, 960, 591, 84);
                Cbtn.addActionListener(e -> submitAnswer(2));

                Dbtn.setBounds(774, 960, 591, 84);
                Dbtn.addActionListener(e -> submitAnswer(3));

                // Debug Tools
                Refactor.setBounds(1531, 671, 324, 50);
                Refactor.addActionListener(e -> useRefactor());

                Console.setBounds(1531, 745, 324, 50);
                Console.addActionListener(e -> useConsoleLog());

                CtrlC.setBounds(1531, 818, 324, 50);
                CtrlC.addActionListener(e -> useCtrlC());

                AutoDebug.setBounds(1531, 892, 324, 50);
                Return.setBounds(1531, 966, 324, 50);

                // Other Buttons
                RetryBtn.setBounds(44, 64, 93, 93);
                MenuBtn.setBounds(172, 64, 93, 93);
                MenuBtn.addActionListener(e -> {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        topFrame.setContentPane(new Menu());
                        topFrame.validate();
                        topFrame.repaint();
                });
                MuteBtn.setBounds(300, 64, 93, 93);

                display();

                // Load and display current question
                loadCurrentQuestion();

                add(questionLabel);
                add(scoreLabel);
                add(Abtn);
                add(Bbtn);
                add(Cbtn);
                add(Dbtn);
                add(Refactor);
                add(Console);
                add(CtrlC);
                add(AutoDebug);
                add(Return);
                add(RetryBtn);
                add(MenuBtn);
                add(MuteBtn);
                add(character);
                add(backgroundPanel);
                validate();
                repaint();
        }

        private void initializeGameManager() {
                // Get Player and Category from GameState
                Player player = GameState.getPlayer();
                QuestionBank.Category category = GameState.getCategory();

                if (player == null || category == null) {
                        JOptionPane.showMessageDialog(null, "Error: Player or Category not set!");
                        return;
                }

                // Create QuestionBank and load category
                QuestionBank bank = new QuestionBank();

                // Create GameManager
                gameManager = new GameManager(bank, player);
                gameManager.initializeGame(category);

                // Store in GameState for access by other methods
                GameState.setGameManager(gameManager);
        }

        private void loadCurrentQuestion() {
                if (gameManager == null) {
                        return;
                }

                // Get topic from GameState
                String topicCode = GameState.getLevel();
                if (topicCode != null) {
                        currentQuestion = gameManager.getQuestionByTopicCode(topicCode);
                }

                if (currentQuestion != null) {
                        displayQuestion(currentQuestion);
                        updateScore();
                } else {
                        questionLabel.setText("No question available for this topic.");
                }
        }

        private void displayQuestion(Question question) {
                if (question != null) {
                        questionLabel.setText("<html><div style='text-align: center;'>" +
                                        question.getQuestionText() + "</div></html>");
                }
        }

        private void submitAnswer(int choiceIndex) {
                if (currentQuestion == null || gameManager == null) {
                        return;
                }

                boolean correct = gameManager.submitAnswer(choiceIndex);
                updateScore();

                // Show feedback
                String message = correct ? "Correct! +" + gameManager.getCurrentQuestionPoints() + " pts"
                                : "Wrong! -" + gameManager.getCurrentQuestionPoints() + " pts";
                JOptionPane.showMessageDialog(this, message);

                // Load next question or end game
                // TODO: Implement question flow logic
        }

        private void updateScore() {
                if (gameManager != null && scoreLabel != null) {
                        Player player = gameManager.getPlayer();
                        if (player != null) {
                                scoreLabel.setText("Score: " + player.getScore());
                        }
                }
        }

        private void useRefactor() {
                if (currentQuestion != null && gameManager != null) {
                        int correctIndex = currentQuestion.getCorrectChoice();
                        gameManager.useRefactor(correctIndex);
                        // TODO: Update UI to show only 2 choices
                }
        }

        private void useConsoleLog() {
                if (currentQuestion != null && gameManager != null) {
                        gameManager.useConsoleLog(currentQuestion);
                }
        }

        private void useCtrlC() {
                if (currentQuestion != null && gameManager != null) {
                        gameManager.useCtrlC(currentQuestion);
                }
        }

        public void display() {
                // Character Display
                character.setBounds(1501, 213, 411, 426);
                character.setOpaque(true);
        }
}
