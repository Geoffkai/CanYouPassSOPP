
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    private QuestionBank questionBank;
    private Classmate selectedClassmate;
    private Player player;
    private Question currentQuestion;
    private boolean debugUsed;
    private QuestionBank.Category chosenCategory;
    private List<Question> availableQuestions;
    private String chosenTopic;
    private int currentQuestionIndex;
    private Map<String, Boolean> debugToolsUsage;
    private boolean isGameActive;
    private Record recordManager = new Record();
    private DebugTools debugTools = new DebugTools();
    Scanner scanner = new Scanner(System.in);

    // Start Game
    public GameManager(QuestionBank questionBank, Player currentPlayer) {
        this.questionBank = (questionBank != null) ? questionBank : new QuestionBank();
        this.player = currentPlayer;
        this.currentQuestionIndex = 0;
        this.debugUsed = false;
        this.isGameActive = false;
        this.availableQuestions = new ArrayList<>();
        this.debugToolsUsage = new HashMap<>();
        this.currentQuestion = null;
        startGame();
    }

    public void startGame() {
        System.out.println("[GameManager] Starting game...");
        isGameActive = true;

        chooseCategory();
        loadQuestions();
        gameLoop();
    }

    // Choose category (theoretical/programming)
    public QuestionBank.Category chooseCategory() {
        System.out.println("\nChoose a category:");
        System.out.println("1. Theoretical");
        System.out.println("2. Programming");
        int choice = scanner.nextInt();
        scanner.nextLine();

        chosenCategory = (choice == 1) ? QuestionBank.Category.Theoretical : QuestionBank.Category.Programming;
        questionBank.loadCategory(chosenCategory);

        return chosenCategory;
    }

    public Classmate selectClassmate() {
        return null;
    }

    // Load 10 questions from each level L1-L5
    public void loadQuestions() {
        availableQuestions.clear();
        for (int i = 1; i <= 5; i++) {
            List<Question> levelQuestions = questionBank.getQuestionsByLevel("L" + i, 2);
            availableQuestions.addAll(levelQuestions);
        }
        System.out.println("[GameManager] Loaded " + availableQuestions.size() + " questions into the game.");

    }

    // Show all remaining questions
    private void displayAvailableQuestions() {
        System.out.println("\nAvailable Questions:");

        for (int i = 0; i < availableQuestions.size(); i++) {
            Question q = availableQuestions.get(i);
            System.out.println((i + 1) + ". " + q.getDifficulty() + " - " + q.getTopic());
        }

    }

    public int chooseQuestion() {
        System.out.println("Choose a topic from the above list: (0 to quit)");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            endGame();
        }
        if (choice < 1 || choice >= availableQuestions.size()) {
            System.out.println("Invalid choice. Please try again.");
        }

        return choice;
    }

    public Question getQuestionByTopicByTopic(String topic, String difficulty) {
        for (Question q : availableQuestions) {
            if (q.getTopic().equals(topic)) {
                return q;
            }
        }
        return null;
    }

    public void gameLoop() {
        while (isGameActive && currentQuestionIndex < availableQuestions.size()) {
            displayAvailableQuestions();
            currentQuestion = availableQuestions.get(chooseQuestion() - 1);
            presentQuestion(currentQuestion);
            availableQuestions.remove(currentQuestion);
        }
        endGame();
    }

    // Present question to player
    public void presentQuestion(Question question) {
        System.out.println("\n[" + question.getDifficulty() + " - " + question.getTopic() + "]");
        System.out.println(question.getQuestionText());

        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        boolean correct = submitAnswer(choice);
        if (correct) {
            System.out.println("Correct answer!");
        } else {
            System.out.println("Wrong answer!");

        }
    }

    public boolean submitAnswer(int choiceIndex) {
        if (currentQuestion == null) {
            System.out.println("No current question to answer.");
            return false;
        }

        boolean correct = currentQuestion.isCorrect(choiceIndex);
        int points = currentQuestion.getDifficultyPoints();

        if (correct) {
            player.addScore(points);
            player.incrementCorrect();
            System.out.println("Correct! +" + points + " pts");
        } else {
            player.deductScore(points);
            System.out.println("Wrong! -" + points + " pts");
        }

        System.out.println("Current Score: " + player.getScore());
        return correct;
    }

    public void endGame() {
        isGameActive = false;

        System.out.println("\n[GameManager] Game Over!");
        System.out.println("Final Score: " + player.getScore());
        System.out.println("Correct Answers: " + player.getCorrectCount());
        // recordManager.saveRecord(player);
    }

    // For Future GUI Use
    public Question getQuestionByIndex(int index) {
        if (index >= 0 && index < availableQuestions.size()) {
            return availableQuestions.get(index);
        }
        return null;
    }

    public List<Question> getAvailableQuestions() {
        return availableQuestions;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    // START OF DEBUG TOOLS
    public void initializeDebugTools() {
        debugToolsUsage.put("Refactor", false);
        debugToolsUsage.put("ConsoleLog", false);
        debugToolsUsage.put("CtrlC", false);
        debugToolsUsage.put("AutoDebug", false);
    }

    public void dispayDebugTools() {
        System.out.println("\nAvailable Debug Tools:");
        for (String tool : debugToolsUsage.keySet()) {
            String status = debugToolsUsage.get(tool) ? "USED" : "AVAILABLE";
            System.out.println("- " + tool + ": " + status);
        }
    }

    public void useRefactor(int correctIndex) {
        List<Integer> newChoices;
        if (debugToolsUsage.get("Refactor")) {
            System.out.println("Refactor tool has already been used.");
            return;
        }

        newChoices = debugTools.refactor(correctIndex);

        System.out.println("\n[Debug Tool - Refactor] Here are your two choices:");
        for (int idx : newChoices) {
            System.out.println((idx + 1) + ". " + currentQuestion.getOptions()[idx]);
        }

        debugToolsUsage.put("Refactor", true);
    }

    public void useConsoleLog(Question question) {
        if (debugToolsUsage.get("ConsoleLog")) {
            System.out.println("Console Log tool has already been used.");
            return;
        }

        if (selectedClassmate == null) {
            System.out.println("No classmate selected for Console Log.");
            return;
        }

        debugTools.consoleLog(selectedClassmate, question);
        debugToolsUsage.put("ConsoleLog", true);
    }

    public void useCtrlC(Question question) {
        if (debugToolsUsage.get("CtrlC")) {
            System.out.println("Ctrl C tool has already been used.");
            return;
        }

        if (selectedClassmate == null) {
            System.out.println("No classmate selected for Ctrl C.");
            return;
        }

        boolean correct = debugTools.ctrlC(selectedClassmate, question);
    }
}
