package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import logic.GameManager;
import logic.Player;
import logic.Question;
import logic.QuestionBank;
import logic.characters.Anon;
import logic.characters.Classmate;
import logic.characters.Elmer;
import logic.characters.Geoff;
import logic.characters.Merry;
import logic.characters.Yvonne;
import logic.tools.DebugTools;

public class GameScreen extends JPanel {
        private GameManager gameManager;
        private Question currentQuestion;
        private JTextPane questionLabel;
        private JLabel scoreLabel;
        private JLabel characterLabel;
        private JLabel compileLabel;
        private boolean questionAnswered;
        private boolean refactorUsed;
        private List<Integer> refactorChoices;
        private DebugTools debugTools = new DebugTools();
        private static int questionsAnswered = 0; // ⭐ STATIC so it persists across GameScreen instances
        private static int consecutiveCorrectAnswers = 0; // ⭐ Track consecutive correct answers for winning condition
        private static final int TOTAL_QUESTIONS = 10;
        private boolean answeredWrong = false; // Track if player answered wrong (can be saved by AutoDebug)
        private int lastQuestionPoints = 0; // Store points from last question for AutoDebug restoration
        private JLabel choiceAText;
        private JLabel choiceBText;
        private JLabel choiceCText;
        private JLabel choiceDText;
        private String[] currentChoiceTexts = new String[4];
        private boolean autoDebugActive = false;

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

        ImageIcon MR = new ImageIcon(new ImageIcon("src/img/Feedback/MainRed.png").getImage().getScaledInstance(595, 97,
                        java.awt.Image.SCALE_SMOOTH));
        JButton MainRed = new JButton(MR);

        ImageIcon PR = new ImageIcon(new ImageIcon("src/img/Feedback/PlayRed.png").getImage().getScaledInstance(595, 97,
                        java.awt.Image.SCALE_SMOOTH));
        JButton PlayRed = new JButton(PR);
        ImageIcon MB = new ImageIcon(
                        new ImageIcon("src/img/Feedback/MainBlue.png").getImage().getScaledInstance(595, 97,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton MainBlue = new JButton(MB);

        ImageIcon PB = new ImageIcon(
                        new ImageIcon("src/img/Feedback/PlayBlue.png").getImage().getScaledInstance(595, 97,
                                        java.awt.Image.SCALE_SMOOTH));
        JButton PlayBlue = new JButton(PB);

        private JLabel topicLabel;
        JPanel character = new JPanel();

        public GameScreen() {
                setLayout(null);
                setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height);
                backgroundPanel.setLayout(null);

                // Display Topic
                topicLabel = new JLabel();
                topicLabel.setBounds(556, 64, 746, 87); // same position as labels before
                add(topicLabel);
                displayTopic();

                // Initialize state
                questionAnswered = false;
                refactorUsed = false;

                // DEBUG: Log the current static counter when creating GameScreen
                System.out.println("[GameScreen CONSTRUCTOR] questionsAnswered=" + questionsAnswered);

                // Create question and score labels
                questionLabel = new JTextPane();
                questionLabel.setContentType("text/html");
                questionLabel.setEditable(false);
                questionLabel.setOpaque(false);
                questionLabel.setBounds(200, 250, 1200, 300);

                scoreLabel = new JLabel("SCORE: 0", SwingConstants.RIGHT);
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 35));
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setBounds(1400, 100, 400, 50);

                // Choices label
                choiceAText = createChoiceTextLabel();
                choiceAText.setBounds(200, 580, 1200, 40);

                choiceBText = createChoiceTextLabel();
                choiceBText.setBounds(200, 630, 1200, 40);

                choiceCText = createChoiceTextLabel();
                choiceCText.setBounds(200, 680, 1200, 40);

                choiceDText = createChoiceTextLabel();
                choiceDText.setBounds(200, 730, 1200, 40);

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
                Return.setEnabled(false);

                // Other Buttons
                RetryBtn.setBounds(44, 64, 93, 93);
                RetryBtn.addActionListener(e -> {
                        questionsAnswered = 0; // Reset counter when restarting
                        consecutiveCorrectAnswers = 0; // Reset consecutive correct answers
                        GameState.resetGame();
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        topFrame.setContentPane(new PlayPanel());
                        // Add reset conditions here
                        topFrame.validate();
                        topFrame.repaint();
                });

                MenuBtn.setBounds(172, 64, 93, 93);
                MenuBtn.addActionListener(e -> {
                        questionsAnswered = 0; // Reset counter when going to menu
                        consecutiveCorrectAnswers = 0; // Reset consecutive correct answers
                        GameState.resetGame();
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        if (topFrame != null) {
                                topFrame.setContentPane(new Menu());
                                topFrame.validate();
                                topFrame.repaint();
                        }
                });
                MuteBtn.setBounds(300, 64, 93, 93);
                MuteBtn.addActionListener(e -> toggleMute());

                display();
                // Do NOT reset questionsAnswered here on every constructor call.
                // It's already reset to 0 by checkGameProgress() when transitioning.
                // This keeps the counter persistent across GameScreen instances.
                // Load and display current question
                loadCurrentQuestion();

                add(questionLabel);
                add(choiceAText);
                add(choiceBText);
                add(choiceCText);
                add(choiceDText);
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

                // Reuse existing GameManager if it already matches the selected category.
                GameManager existing = GameState.getGameManager();
                if (existing != null && existing.getCategory() == category) {
                        gameManager = existing;
                } else {
                        // Create QuestionBank and load category
                        QuestionBank bank = new QuestionBank();

                        // Create GameManager and initialize for category (resets debug tools)
                        gameManager = new GameManager(bank, player);
                        gameManager.initializeGame(category);

                        // Store in GameState for access by other methods
                        GameState.setGameManager(gameManager);
                }

                // Set up classmate for debug tools
                String characterName = GameState.getCharacter();
                if (characterName != null) {
                        Classmate classmate = createClassmate(characterName);
                        if (classmate != null) {
                                gameManager.setSelectedClassmate(classmate);
                        }
                }

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

                // Always pull the topic code, NOT the level
                String topicCode = GameState.getTopic(); // e.g., "Prod2"

                if (topicCode != null) {
                        currentQuestion = gameManager.getQuestionByTopicCode(topicCode);
                }

                // If no topic was selected (e.g., after switching categories), fall back
                // to the first available question so gameplay can continue immediately
                if (currentQuestion == null) {
                        List<Question> avail = gameManager.getAvailableQuestions();
                        if (avail != null && !avail.isEmpty()) {
                                currentQuestion = gameManager.getQuestionByIndex(0);
                        }
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
                        questionLabel.setText(formatQuestionText(question.getQuestionText()));

                        // Options
                        String[] opts = question.getOptions();
                        currentChoiceTexts = opts; // save raw text

                        choiceAText.setText(formatChoiceText("A. ", opts[0], "#333333"));
                        choiceBText.setText(formatChoiceText("B. ", opts[1], "#333333"));
                        choiceCText.setText(formatChoiceText("C. ", opts[2], "#333333"));
                        choiceDText.setText(formatChoiceText("D. ", opts[3], "#333333"));

                        // Reset button states
                        questionAnswered = false;
                        refactorUsed = false;
                        enableAllChoiceButtons();
                        updateDebugToolButtons();
                }
        }

        private String formatQuestionText(String text) {
                // Convert literal '\n' from JSON to real newlines
                text = text.replace("\\n", "\n");

                // Replace real newlines with <br> for HTML display
                text = text.replace("\n", "<br>");

                // Find the first <br> safely
                int firstBreak = text.indexOf("<br>");

                String questionPart;
                String codePart;

                if (firstBreak == -1) {
                        // No code section → whole thing is the question text
                        questionPart = text;
                        codePart = "";
                } else {
                        questionPart = text.substring(0, firstBreak);
                        codePart = text.substring(firstBreak + 4);
                }

                // Build code box only if codePart is not empty
                String codeBox = "";
                if (!codePart.isEmpty()) {
                        codeBox = "<div style='background:#2b2b2b; padding:15px; border-radius:10px; "
                                        + "font-family:Consolas, monospace; font-size:18px; color:#e8e8e8; width:90%; "
                                        + "margin:0 auto; text-align:left; max-height:300px; overflow-y:auto;'>"
                                        + codePart
                                        + "</div>";
                }

                // Final styled HTML
                return "<html>"
                                + "<div style='font-family:Arial; font-size:22px; color:#333333; text-align:center; margin-bottom:15px;'>"
                                + "QUESTION:"
                                + "</div>"

                                + "<div style='font-family:Arial; font-size:18px; color:#333333; text-align:center; margin-bottom:20px;'>"
                                + questionPart
                                + "</div>"

                                + codeBox
                                + "</html>";
        }

        private JLabel createChoiceTextLabel() {
                JLabel lbl = new JLabel("", SwingConstants.LEFT);
                lbl.setFont(new Font("Arial", Font.BOLD, 22));
                lbl.setOpaque(false);
                return lbl;
        }

        private String formatChoiceText(String prefix, String text, String colorHex) {
                return "<html><span style='color:" + colorHex + ";'>" + prefix + text + "</span></html>";
        }

        private void submitAnswer(int choiceIndex) {
                if (currentQuestion == null || gameManager == null || questionAnswered)
                        return;
                questionAnswered = true;
                questionsAnswered++;

                System.out.println("[GameScreen] submitAnswer: questionsAnswered INCREMENTED to " + questionsAnswered);

                // Store points before submission (for AutoDebug restoration if needed)
                lastQuestionPoints = currentQuestion.getDifficultyPoints();

                boolean correct = gameManager.submitAnswer(choiceIndex);

                // Auto-save with AutoDebug if answer is wrong and AutoDebug available
                if (!correct && gameManager.isDebugToolAvailable("AutoDebug")) {
                        System.out.println("[GameScreen] Wrong answer but AutoDebug available! Auto-saving...");

                        // Use AutoDebug automatically
                        gameManager.useAutoDebugTool();

                        // Restore points (add back double: undo deduction + add correct score)
                        Player player = gameManager.getPlayer();
                        if (player != null) {
                                player.addScore(lastQuestionPoints * 2);
                                player.incrementCorrect();
                        }

                        // Mark as correct
                        correct = true;
                        consecutiveCorrectAnswers++;
                        answeredWrong = false;

                        System.out.println("[GameScreen] AutoDebug auto-saved! Marked as correct. Consecutive: "
                                        + consecutiveCorrectAnswers);

                        // Show message
                        JOptionPane.showMessageDialog(this,
                                        "AutoDebug automatically saved you!\nQuestion marked as CORRECT and points preserved!");
                }

                // ⭐ Track consecutive correct answers for winning condition
                if (correct) {
                        consecutiveCorrectAnswers++;
                        System.out.println("[GameScreen] Consecutive correct answers: " + consecutiveCorrectAnswers);
                        answeredWrong = false;
                } else {
                        System.out.println("[GameScreen] WRONG ANSWER! Resetting consecutive counter from "
                                        + consecutiveCorrectAnswers + " to 0");
                        consecutiveCorrectAnswers = 0;
                        answeredWrong = true;

                        // No AutoDebug available - game over
                        System.out.println("[GameScreen] Wrong answer and no AutoDebug available! Ending game.");
                }

                // ⭐ Capture category BEFORE any switching, and mark slot as used
                String originalCategory = GameState.getCategoryKey();
                String slot = GameState.getSelectedSlotForCategory(originalCategory);

                if (slot != null && questionsAnswered < TOTAL_QUESTIONS) {
                        // Only mark the slot as used if we haven't finished all 10 questions yet.
                        // After 10 questions, the category switches, so we don't want to mark
                        // slots in the new category.
                        GameState.markSlotUsedForCategory(originalCategory, slot);
                }

                highlightChoices(choiceIndex, currentQuestion.getCorrectChoice());
                updateScore();
                disableAllChoiceButtons();
                updateDebugToolButtons();
                Return.setEnabled(true);

                // Show compiling animation
                showCompilingAnimation();

                // Make final for lambda
                final boolean finalCorrect = correct;

                // Add 3 second delay before showing feedback panel
                Timer feedbackTimer = new Timer(3000, e -> {
                        hideCompilingAnimation();
                        showCustomFeedback(finalCorrect);
                        // Check game progress after feedback is shown (will be triggered by feedback
                        // panel clicks)
                });
                feedbackTimer.setRepeats(false);
                feedbackTimer.start();
        }

        private void showCompilingAnimation() {
                compileLabel.setText("Compiling...");
                compileLabel.setVisible(true);

                // Animate dots
                Timer dotTimer = new Timer(400, null);
                final int[] dotCount = { 0 };
                dotTimer.addActionListener(e -> {
                        dotCount[0] = (dotCount[0] + 1) % 4;
                        String dots = ".".repeat(dotCount[0]);
                        compileLabel.setText("Compiling" + dots);
                });
                dotTimer.start();

                // Change to "Building..." after 1 second
                Timer buildTimer = new Timer(1000, e -> {
                        compileLabel.setText("Building...");
                        dotTimer.stop();

                        // Restart dot animation for building
                        Timer buildDotTimer = new Timer(400, null);
                        final int[] buildDotCount = { 0 };
                        buildDotTimer.addActionListener(ev -> {
                                buildDotCount[0] = (buildDotCount[0] + 1) % 4;
                                String dots = ".".repeat(buildDotCount[0]);
                                compileLabel.setText("Building" + dots);
                        });
                        buildDotTimer.start();

                        // Stop after another second
                        Timer stopTimer = new Timer(1000, ev -> buildDotTimer.stop());
                        stopTimer.setRepeats(false);
                        stopTimer.start();
                });
                buildTimer.setRepeats(false);
                buildTimer.start();
        }

        private void hideCompilingAnimation() {
                compileLabel.setVisible(false);
        }

        private void showCustomFeedback(boolean correct) {
                // Load both feedback images
                String firstImagePath = correct ? "src/img/Feedback/CorrectAnswer.png"
                                : "src/img/Feedback/WrongAnswer.png";
                String secondImagePath = correct ? "src/img/Feedback/CurrentScore.png" : "src/img/Feedback/YouFail.png";

                ImageIcon firstIcon = new ImageIcon(firstImagePath);
                ImageIcon secondIcon = new ImageIcon(secondImagePath);

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

                // Create overlay panel that covers everything
                JPanel overlayPanel = new JPanel(null);
                overlayPanel.setBounds(0, 0, screenSize.width, screenSize.height);
                overlayPanel.setOpaque(true);
                overlayPanel.setBackground(Color.BLACK); // or match your background

                // Labels for username and score
                Player player = gameManager.getPlayer();
                JLabel user = new JLabel(player.getUsername());
                user.setForeground(new Color(255, 230, 66));
                user.setHorizontalAlignment(JLabel.CENTER);
                user.setVisible(false);

                JLabel score = new JLabel(String.valueOf(player.getScore()));
                score.setForeground(Color.WHITE);
                score.setHorizontalAlignment(JLabel.CENTER);
                score.setVisible(false);

                overlayPanel.add(user);
                overlayPanel.add(score);

                // Setup buttons based on correct/incorrect
                JButton mainButton = correct ? new JButton(MainBlue.getIcon()) : new JButton(MainRed.getIcon());
                JButton playButton = correct ? new JButton(PlayBlue.getIcon()) : new JButton(PlayRed.getIcon());

                mainButton.setBounds(662, 758, 595, 97);
                mainButton.addActionListener(e -> {
                        // Remove overlay first
                        JLayeredPane layeredPane = topFrame.getLayeredPane();
                        layeredPane.remove(overlayPanel);
                        layeredPane.revalidate();
                        layeredPane.repaint();

                        GameState.resetGame();
                        topFrame.setContentPane(new Menu());
                        topFrame.revalidate();
                        topFrame.repaint();
                });

                playButton.setBounds(662, 917, 595, 97);
                playButton.addActionListener(e -> {
                        // Remove overlay first
                        JLayeredPane layeredPane = topFrame.getLayeredPane();
                        layeredPane.remove(overlayPanel);
                        layeredPane.revalidate();
                        layeredPane.repaint();

                        GameState.resetGame();
                        topFrame.setContentPane(new PlayPanel());
                        topFrame.revalidate();
                        topFrame.repaint();
                });

                mainButton.setVisible(false);
                playButton.setVisible(false);

                overlayPanel.add(mainButton);
                overlayPanel.add(playButton);

                // Create label with the first image - center it
                JLabel imageLabel = new JLabel(firstIcon);
                int x = (screenSize.width - firstIcon.getIconWidth()) / 2;
                int y = (screenSize.height - firstIcon.getIconHeight()) / 2;
                imageLabel.setBounds(x, y, firstIcon.getIconWidth(), firstIcon.getIconHeight());
                imageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Track which stage we're on
                final boolean[] isFirstImage = { true };

                // Add click listener to swap images
                imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                if (isFirstImage[0]) {
                                        // First click: swap to second image
                                        imageLabel.setIcon(secondIcon);

                                        // Show username and score
                                        user.setVisible(true);
                                        score.setVisible(true);

                                        if (correct) {
                                                // Correct answer layout
                                                user.setBounds(730, 707, 387, 64);
                                                user.setFont(new Font("Arial", Font.BOLD, 40));
                                                score.setBounds(849, 846, 150, 57);
                                                score.setFont(new Font("Arial", Font.BOLD, 35));
                                        } else {
                                                // Incorrect answer layout
                                                user.setBounds(766, 540, 387, 63);
                                                user.setFont(new Font("Arial", Font.BOLD, 45));
                                                score.setBounds(858, 624, 203, 64);
                                                score.setFont(new Font("Arial", Font.BOLD, 40));

                                                // Show buttons (adjust positions)
                                                mainButton.setBounds(662, 758, 595, 97);
                                                playButton.setBounds(662, 917, 595, 97);
                                                mainButton.setVisible(true);
                                                playButton.setVisible(true);

                                                // Check if this is final game over (AutoDebug was already used)
                                                if (!gameManager.isDebugToolAvailable("AutoDebug") && !answeredWrong) {
                                                        // This was a wrong answer after AutoDebug was used - game over
                                                        // Buttons will handle navigation to menu/replay
                                                }

                                                // Disable clicking on image - only buttons should work
                                                imageLabel.setCursor(
                                                                new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                                                imageLabel.removeMouseListener(this);
                                        }

                                        overlayPanel.revalidate();
                                        overlayPanel.repaint();

                                        isFirstImage[0] = false;
                                } else if (correct) {
                                        // Second click (only for correct answers): transition to TopicsPanel
                                        // Remove overlay first
                                        JLayeredPane layeredPane = topFrame.getLayeredPane();
                                        layeredPane.remove(overlayPanel);
                                        layeredPane.revalidate();
                                        layeredPane.repaint();

                                        // Check game progress now (category switch or game end)
                                        checkGameProgress();

                                        // If didn't end game, go to TopicsPanel
                                        if (questionsAnswered < TOTAL_QUESTIONS) {
                                                topFrame.setContentPane(new TopicsPanel());
                                                topFrame.revalidate();
                                                topFrame.repaint();
                                        }
                                }
                        }
                });

                overlayPanel.add(imageLabel);

                // Add overlay on top of everything using JLayeredPane
                JLayeredPane layeredPane = topFrame.getLayeredPane();
                layeredPane.add(overlayPanel, JLayeredPane.POPUP_LAYER);

                overlayPanel.revalidate();
                overlayPanel.repaint();
        }

        private void highlightChoices(int selectedIndex, int correctIndex) {
                JLabel[] labels = { choiceAText, choiceBText, choiceCText, choiceDText };

                for (int i = 0; i < labels.length; i++) {
                        String color;
                        if (i == selectedIndex) {
                                color = (i == correctIndex) ? "#00AA00" : "#AA0000"; // green or red
                        } else if (i == correctIndex) {
                                color = "#00AA00"; // correct answer green
                        } else {
                                color = "#333333"; // dark gray
                        }
                        labels[i].setText(formatChoiceText(getChoicePrefix(i), currentChoiceTexts[i], color));
                }
        }

        private String getChoicePrefix(int index) {
                switch (index) {
                        case 0:
                                return "A. ";
                        case 1:
                                return "B. ";
                        case 2:
                                return "C. ";
                        case 3:
                                return "D. ";
                        default:
                                return "";
                }
        }

        private void playSoundEffect(boolean correct) {
                // TODO: Integrate with SoundManager when audio files are available
                // SoundManager.playSound(correct ? "correct.wav" : "incorrect.wav");
        }

        private void checkGameProgress() {
                Player player = gameManager.getPlayer();
                int correctCount = (player != null) ? player.getCorrectCount() : 0;

                System.out.println("[GameScreen] checkGameProgress: questionsAnswered=" + questionsAnswered
                                + ", TOTAL_QUESTIONS=" + TOTAL_QUESTIONS);

                if (questionsAnswered >= TOTAL_QUESTIONS) {
                        System.out.println("[GameScreen] ✓ Finished 10 questions");

                        // identify next category
                        QuestionBank.Category next = GameState.getNextCategory();
                        System.out.println("[GameScreen] Current category: " + GameState.getCategory()
                                        + ", Next category: " + next);

                        // CASE 1 — next category exists (we finished category 1)
                        if (next != null && next != GameState.getCategory()) {
                                System.out.println("[GameScreen] ✓ Finished category, moving to TopicsPanel");

                                // Notify player
                                JOptionPane.showMessageDialog(this,
                                                "You finished the " + GameState.getCategory().name() + " category!\n"
                                                                + "Now proceeding to " + next.name() + "!");

                                // SWITCH category
                                GameState.setCategory(next);

                                // Reset counter for the new category
                                questionsAnswered = 0;
                                questionAnswered = false;

                                // Go to TopicsPanel to let user choose a topic in the next category
                                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                                topFrame.setContentPane(new TopicsPanel());
                                topFrame.validate();
                                topFrame.repaint();
                                return;
                        }

                        // CASE 2 — no next category, game ends
                        // Player wins only if they answered all 20 questions consecutively correct (10
                        // per category)
                        boolean won = (consecutiveCorrectAnswers == TOTAL_QUESTIONS * 2);
                        System.out.println("[GameScreen] Game Over! Consecutive correct: " + consecutiveCorrectAnswers
                                        + ", Won: " + won);
                        endGame(won);
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
                        if (topFrame != null) {
                                topFrame.setContentPane(new GameOverPanel(player, TOTAL_QUESTIONS, correctCount, won));
                                topFrame.validate();
                                topFrame.repaint();
                        }
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
                Abtn.setEnabled(false);
                Bbtn.setEnabled(false);
                Cbtn.setEnabled(false);
                Dbtn.setEnabled(false);

                // Show only the refactored choices
                for (Integer idx : visibleIndices) {
                        switch (idx) {
                                case 0:
                                        Abtn.setEnabled(true);
                                        break;
                                case 1:
                                        Bbtn.setEnabled(true);
                                        break;
                                case 2:
                                        Cbtn.setEnabled(true);
                                        break;
                                case 3:
                                        Dbtn.setEnabled(true);
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
                if (currentQuestion == null || gameManager == null || questionAnswered)
                        return;

                if (!gameManager.isDebugToolAvailable("CtrlC")) {
                        JOptionPane.showMessageDialog(this, "Ctrl C tool has already been used!");
                        return;
                }

                if (gameManager.getSelectedClassmate() == null) {
                        JOptionPane.showMessageDialog(this, "No classmate selected for Ctrl C!");
                        return;
                }

                // Mark tool as used in GameManager
                gameManager.useCtrlC(currentQuestion);
                updateDebugToolButtons();

                // Classmate decides the answer
                int classmateAnswer = gameManager.getSelectedClassmate().chooseAnswerIndex(currentQuestion);
                String answerText = currentQuestion.getOptions()[classmateAnswer];

                // Show what the classmate picked
                JOptionPane.showMessageDialog(this,
                                gameManager.getSelectedClassmate().getName()
                                                + " chose: " + answerText
                                                + "\nCtrl+C will automatically submit this answer.");

                // Auto-submit the classmate’s answer
                submitAnswer(classmateAnswer);
        }

        private void useAutoDebug() {
                if (currentQuestion == null || gameManager == null)
                        return;

                if (!gameManager.isDebugToolAvailable("AutoDebug")) {
                        JOptionPane.showMessageDialog(this, "Auto Debug has already been used!");
                        return;
                }

                gameManager.useAutoDebugTool();
                updateDebugToolButtons();

                autoDebugActive = true; // Mark AutoDebug ON

                // Reveal correct answer ONLY
                int correctIndex = currentQuestion.getCorrectChoice();

                JLabel[] labels = { choiceAText, choiceBText, choiceCText, choiceDText };
                for (int i = 0; i < labels.length; i++) {
                        if (i == correctIndex)
                                labels[i].setText(
                                                formatChoiceText(getChoicePrefix(i), currentChoiceTexts[i], "#00AA00"));
                }

                // AutoDebug now only reveals the correct answer (auto-save happens in
                // submitAnswer)
                if (!questionAnswered) {
                        JOptionPane.showMessageDialog(this,
                                        "Auto Debug activated!\nThe correct answer is highlighted.\nYou may now answer.");
                } else {
                        JOptionPane.showMessageDialog(this,
                                        "Auto Debug activated!\nThe correct answer is revealed.");
                }
        }

        private void returnToTopics() {
                // Save current score without deduction
                if (gameManager != null && gameManager.getPlayer() != null) {
                        // Score is already saved in player object
                }

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame != null) {
                        topFrame.setContentPane(new TopicsPanel());
                        topFrame.validate();
                        topFrame.repaint();
                }
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
                // ⭐ AutoDebug can be used even after wrong answer (as a life saver)
                AutoDebug.setEnabled(!usage.getOrDefault("AutoDebug", false));
        }

        public void display() {
                // Character Display
                character.setBounds(1501, 213, 411, 426);
                character.setOpaque(false);
        }
}
