package src.gui;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import src.logic.GameManager;
import src.logic.Player;
import src.logic.Question;
import src.logic.QuestionBank;
import src.logic.characters.*;
import src.logic.tools.DebugTools;

public class GameScreen extends JPanel {
        private GameManager gameManager;
        private Question currentQuestion;
        private JLabel questionLabel;
        private JLabel scoreLabel;
        private JLabel characterLabel;
        private JLabel compileLabel;
        private boolean questionAnswered;
        private boolean refactorUsed;
        private List<Integer> refactorChoices;
        private DebugTools debugTools = new DebugTools();
        private int questionsAnswered = 0;
        private static final int TOTAL_QUESTIONS = 10;

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/img/InitialImg/GameScreen.png");
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

        private JLabel topicLabel;
        JPanel character = new JPanel();

        public GameScreen() {
                setLayout(null);
                setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setLayout(null);

                // Display Topic
                topicLabel = new JLabel();
                topicLabel.setBounds(556, 64, 746, 87);   // same position as labels before
                add(topicLabel);
                displayTopic();

                // Initialize state
                questionAnswered = false;
                refactorUsed = false;

                // Create question and score labels
                questionLabel = new JLabel("", SwingConstants.CENTER);
                questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
                questionLabel.setForeground(Color.WHITE);
                questionLabel.setBounds(200, 300, 1200, 100);

                scoreLabel = new JLabel("Score: 0", SwingConstants.RIGHT);
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setBounds(1400, 100, 400, 50);

                // Compile animation label
                compileLabel = new JLabel("", SwingConstants.CENTER);
                compileLabel.setFont(new Font("Courier New", Font.BOLD, 18));
                compileLabel.setForeground(Color.GREEN);
                compileLabel.setBounds(200, 200, 1200, 50);
                compileLabel.setVisible(false);

                // Character label for displaying character icon (must be initialized before
                // initializeGameManager)
                characterLabel = new JLabel();
                characterLabel.setBounds(1501, 213, 411, 426);

                // Initialize GameManager (after characterLabel is created)
                initializeGameManager();

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
                AutoDebug.addActionListener(e -> useAutoDebug());

                Return.setBounds(1531, 966, 324, 50);
                Return.addActionListener(e -> returnToTopics());

                // Other Buttons
                RetryBtn.setBounds(44, 64, 93, 93);
                RetryBtn.addActionListener(e -> {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        topFrame.setContentPane(new PlayPanel());
                        //Add reset conditions here
                        topFrame.validate();
                        topFrame.repaint();
                });

                MenuBtn.setBounds(172, 64, 93, 93);
                MenuBtn.addActionListener(e -> {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        topFrame.setContentPane(new Menu());
                        topFrame.validate();
                        topFrame.repaint();
                });
                MuteBtn.setBounds(300, 64, 93, 93);
                MuteBtn.addActionListener(e -> toggleMute());

                display();

                // Load and display current question
                loadCurrentQuestion();

                add(questionLabel);
                add(scoreLabel);
                add(compileLabel);
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
                add(characterLabel);
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

                // Set up classmate for debug tools
                String characterName = GameState.getCharacter();
                if (characterName != null) {
                        Classmate classmate = createClassmate(characterName);
                        if (classmate != null) {
                                gameManager.setSelectedClassmate(classmate);
                        }
                }

                // Store in GameState for access by other methods
                GameState.setGameManager(gameManager);

                // Display character icon
                displayCharacter();
        }

        private Classmate createClassmate(String name) {
                switch (name) {
                        case "Geoff":
                                return new Geoff();
                        case "Yvonne":
                                return new Yvonne();
                        case "Anon":
                                return new Anon();
                        case "Elmer":
                                return new Elmer();
                        case "Merry":
                                return new Merry();
                        default:
                                return null;
                }
        }

        private void displayTopic() {
                ImageIcon topicIcon = GameState.getTopicIcon();

                if (topicIcon != null) {
                        topicLabel.setIcon(topicIcon);
                } else {
                        topicLabel.setIcon(null);
                }
        }


        private void displayCharacter() {
                if (characterLabel == null) {
                        return; // Safety check
                }
                String characterName = GameState.getCharacter();
                if (characterName != null) {
                        ImageIcon icon = GameState.getCharacterIcon(characterName);
                        if (icon != null) {
                                characterLabel.setIcon(icon);
                        }
                }
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

                        // Reset button states
                        questionAnswered = false;
                        refactorUsed = false;
                        enableAllChoiceButtons();
                        updateDebugToolButtons();
                }
        }

        private void submitAnswer(int choiceIndex) {
                if (currentQuestion == null || gameManager == null || questionAnswered) {
                        return;
                }


                questionAnswered = true;
                questionsAnswered++;


                boolean correct = gameManager.submitAnswer(choiceIndex);

                // Play sound effect
                playSoundEffect(correct);
        }


        private void playSoundEffect(boolean correct) {
                // TODO: Integrate with SoundManager when audio files are available
                // SoundManager.playSound(correct ? "correct.wav" : "incorrect.wav");
        }

        private void checkGameProgress() {
                Player player = gameManager.getPlayer();
                int correctCount = player != null ? player.getCorrectCount() : 0;

                // Check if all questions are answered
                if (questionsAnswered >= TOTAL_QUESTIONS) {
                        // Check if player won (all correct)
                        boolean won = (correctCount == TOTAL_QUESTIONS);
                        endGame(won);
                } else {
                        // Return to topics for next question
                }
        }

        private void endGame(boolean won) {
                // Save record before ending
                if (gameManager != null) {
                        gameManager.endGame();
                }

                Player player = gameManager != null ? gameManager.getPlayer() : null;
                int correctCount = player != null ? player.getCorrectCount() : 0;

                // Show game over panel
                Timer gameOverTimer = new Timer(500, e -> {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        topFrame.setContentPane(new GameOverPanel(player, TOTAL_QUESTIONS, correctCount, won));
                        topFrame.validate();
                        topFrame.repaint();
                });
                gameOverTimer.setRepeats(false);
                gameOverTimer.start();
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
                if (currentQuestion == null || gameManager == null || refactorUsed || questionAnswered) {
                        if (refactorUsed) {
                                JOptionPane.showMessageDialog(this, "Refactor tool has already been used!");
                        }
                        return;
                }

                if (!gameManager.isDebugToolAvailable("Refactor")) {
                        JOptionPane.showMessageDialog(this, "Refactor tool has already been used!");
                        return;
                }

                int correctIndex = currentQuestion.getCorrectChoice();
                refactorChoices = debugTools.refactor(correctIndex);
                gameManager.useRefactor(correctIndex);
                refactorUsed = true;

                // Hide 2 choices, show only the 2 from refactor
                hideChoicesExcept(refactorChoices);
                updateDebugToolButtons();
        }

        private void hideChoicesExcept(List<Integer> visibleIndices) {
                // Hide all buttons first
                Abtn.setVisible(false);
                Bbtn.setVisible(false);
                Cbtn.setVisible(false);
                Dbtn.setVisible(false);

                // Show only the refactored choices
                for (Integer idx : visibleIndices) {
                        switch (idx) {
                                case 0:
                                        Abtn.setVisible(true);
                                        break;
                                case 1:
                                        Bbtn.setVisible(true);
                                        break;
                                case 2:
                                        Cbtn.setVisible(true);
                                        break;
                                case 3:
                                        Dbtn.setVisible(true);
                                        break;
                        }
                }
        }

        private void useConsoleLog() {
                if (currentQuestion == null || gameManager == null || questionAnswered) {
                        return;
                }

                if (!gameManager.isDebugToolAvailable("ConsoleLog")) {
                        JOptionPane.showMessageDialog(this, "Console Log tool has already been used!");
                        return;
                }

                if (gameManager.getSelectedClassmate() == null) {
                        JOptionPane.showMessageDialog(this, "No classmate selected for Console Log!");
                        return;
                }

                gameManager.useConsoleLog(currentQuestion);
                updateDebugToolButtons();

                // Show what classmate answered
                int classmateAnswer = gameManager.getSelectedClassmate().chooseAnswerIndex(currentQuestion);
                String answerText = currentQuestion.getOptions()[classmateAnswer];
                JOptionPane.showMessageDialog(this,
                                gameManager.getSelectedClassmate().getName() + " thinks the answer is: " + answerText);
        }

        private void useCtrlC() {
                if (currentQuestion == null || gameManager == null || questionAnswered) {
                        return;
                }

                if (!gameManager.isDebugToolAvailable("CtrlC")) {
                        JOptionPane.showMessageDialog(this, "Ctrl C tool has already been used!");
                        return;
                }

                if (gameManager.getSelectedClassmate() == null) {
                        JOptionPane.showMessageDialog(this, "No classmate selected for Ctrl C!");
                        return;
                }

                gameManager.useCtrlC(currentQuestion);
                updateDebugToolButtons();
                int classmateAnswer = gameManager.getSelectedClassmate().chooseAnswerIndex(currentQuestion);
                boolean isCorrect = (classmateAnswer == currentQuestion.getCorrectChoice());
                String message = isCorrect ? "Ctrl C used! The answer might be correct!"
                                : "Ctrl C used! The answer might be wrong.";
                JOptionPane.showMessageDialog(this, message);
        }

        private void useAutoDebug() {
                if (currentQuestion == null || gameManager == null || questionAnswered) {
                        return;
                }

                if (!gameManager.isDebugToolAvailable("AutoDebug")) {
                        JOptionPane.showMessageDialog(this, "Auto Debug tool has already been used!");
                        return;
                }

                // AutoDebug automatically answers using classmate's choice
                if (gameManager.getSelectedClassmate() != null) {
                        int answerIndex = gameManager.getSelectedClassmate()
                                        .chooseAnswerIndex(currentQuestion);
                        boolean classmateCorrect = (answerIndex == currentQuestion.getCorrectChoice());

                        if (classmateCorrect) {
                                // Classmate is correct, auto-submit their answer
                                submitAnswer(answerIndex);
                        } else {
                                // Classmate is wrong, but player can still answer
                                JOptionPane.showMessageDialog(this,
                                                "Auto Debug: " + gameManager.getSelectedClassmate().getName() +
                                                                " suggests: "
                                                                + currentQuestion.getOptions()[answerIndex] +
                                                                "\n(You can still choose your own answer)");
                        }
                } else {
                        // If no classmate, just submit the correct answer
                        submitAnswer(currentQuestion.getCorrectChoice());
                }
        }

        private void returnToTopics() {
                // Save current score without deduction
                if (gameManager != null && gameManager.getPlayer() != null) {
                        // Score is already saved in player object
                }

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.setContentPane(new TopicsPanel());
                topFrame.validate();
                topFrame.repaint();
        }

        private void retryQuestion() {
                // Reset current question state
                questionAnswered = false;
                refactorUsed = false;
                refactorChoices = null;

                // Re-enable buttons
                enableAllChoiceButtons();
                Abtn.setEnabled(true);
                Bbtn.setEnabled(true);
                Cbtn.setEnabled(true);
                Dbtn.setEnabled(true);

                // Reload the same question
                loadCurrentQuestion();
        }

        private void toggleMute() {
                // TODO: Implement mute functionality with SoundManager
                JOptionPane.showMessageDialog(this, "Mute functionality - Toggle sound on/off");
        }

        private void enableAllChoiceButtons() {
                Abtn.setVisible(true);
                Bbtn.setVisible(true);
                Cbtn.setVisible(true);
                Dbtn.setVisible(true);
                Abtn.setEnabled(true);
                Bbtn.setEnabled(true);
                Cbtn.setEnabled(true);
                Dbtn.setEnabled(true);
        }

        private void disableAllChoiceButtons() {
                Abtn.setEnabled(false);
                Bbtn.setEnabled(false);
                Cbtn.setEnabled(false);
                Dbtn.setEnabled(false);
        }

        private void updateDebugToolButtons() {
                if (gameManager == null) {
                        return;
                }

                Map<String, Boolean> usage = gameManager.getDebugToolsUsage();
                Refactor.setEnabled(!usage.getOrDefault("Refactor", false) && !questionAnswered);
                Console.setEnabled(!usage.getOrDefault("ConsoleLog", false) && !questionAnswered);
                CtrlC.setEnabled(!usage.getOrDefault("CtrlC", false) && !questionAnswered);
                AutoDebug.setEnabled(!usage.getOrDefault("AutoDebug", false) && !questionAnswered);
        }

        public void display() {
                // Character Display
                character.setBounds(1501, 213, 411, 426);
                character.setOpaque(false);
        }
}
