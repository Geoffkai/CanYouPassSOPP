package src.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.logic.characters.Classmate;
import src.logic.data.Record;
import src.logic.tools.DebugTools;

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

    // Constructor for GUI - does not auto-start terminal game
    public GameManager(QuestionBank questionBank, Player currentPlayer) {
        this.questionBank = (questionBank != null) ? questionBank : new QuestionBank();
        this.player = currentPlayer;
        this.currentQuestionIndex = 0;
        this.debugUsed = false;
        this.isGameActive = false;
        this.availableQuestions = new ArrayList<>();
        this.debugToolsUsage = new HashMap<>();
        this.currentQuestion = null;
        this.initializeDebugTools();
    }

    // Initialize game with category - called by GUI
    public void initializeGame(QuestionBank.Category category) {
        this.chosenCategory = category;
        this.questionBank.loadCategory(category);
        this.isGameActive = true;
        loadQuestions();
    }

    // Load questions based on the loaded category
    public void loadQuestions() {
        availableQuestions.clear();
        for (int i = 1; i <= 5; i++) {
            List<Question> levelQuestions = questionBank.getQuestionsByLevel("L" + i, 2);
            availableQuestions.addAll(levelQuestions);
        }
    }

    public Classmate selectClassmate() {
        return null;
    }

    // Get question by topic code (e.g., "Prod5", "Func1", etc.)
    public Question getQuestionByTopicCode(String topicCode) {
        // Parse topic code to get difficulty (last character) and topic prefix
        if (topicCode.length() < 2) {
            return null;
        }

        String difficulty = "L" + topicCode.substring(topicCode.length() - 1);
        String topicPrefix = topicCode.substring(0, topicCode.length() - 1);

        // Map topic prefixes to full topic names
        String topicName = mapTopicPrefixToName(topicPrefix);

        // Find matching question
        for (Question q : availableQuestions) {
            if (q.getDifficulty().equals(difficulty) && q.getTopic().toLowerCase().contains(topicName.toLowerCase())) {
                currentQuestion = q;
                return q;
            }
        }

        // If no exact match, get any question with matching difficulty
        for (Question q : availableQuestions) {
            if (q.getDifficulty().equals(difficulty)) {
                currentQuestion = q;
                return q;
            }
        }

        return null;
    }

    private String mapTopicPrefixToName(String prefix) {
        // Map topic codes to topic names
        switch (prefix) {
            case "Prod":
                return "Procedural";
            case "Func":
                return "Functional";
            case "OOP":
                return "Object-Oriented";
            case "Imp":
                return "Imperative";
            case "Dec":
                return "Declarative";
            case "Evdr":
                return "Event-Driven";
            default:
                return prefix;
        }
    }

    // Load a specific question (GUI method)
    public void loadQuestion(Question question) {
        this.currentQuestion = question;
    }

    // Get current question (GUI method)
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    // Get player instance
    public Player getPlayer() {
        return player;
    }

    // Get category
    public QuestionBank.Category getCategory() {
        return chosenCategory;
    }

    public boolean submitAnswer(int choiceIndex) {
        if (currentQuestion == null) {
            return false;
        }

        boolean correct = currentQuestion.isCorrect(choiceIndex);
        int points = currentQuestion.getDifficultyPoints();

        if (correct) {
            player.addScore(points);
            player.incrementCorrect();
        } else {
            player.deductScore(points);
        }

        return correct;
    }

    // Get points for current question (for GUI display)
    public int getCurrentQuestionPoints() {
        if (currentQuestion == null) {
            return 0;
        }
        return currentQuestion.getDifficultyPoints();
    }

    public void endGame() {
        isGameActive = false;
        recordManager.saveRecord(player);
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
        if (debugToolsUsage.isEmpty()) {
            debugToolsUsage.put("Refactor", false);
            debugToolsUsage.put("ConsoleLog", false);
            debugToolsUsage.put("CtrlC", false);
            debugToolsUsage.put("AutoDebug", false);
        }
    }

    // Check if debug tool is available
    public boolean isDebugToolAvailable(String toolName) {
        return !debugToolsUsage.getOrDefault(toolName, true);
    }

    // Get debug tools usage map (for GUI)
    public Map<String, Boolean> getDebugToolsUsage() {
        return new HashMap<>(debugToolsUsage);
    }

    // Set selected classmate (for debug tools)
    public void setSelectedClassmate(Classmate classmate) {
        this.selectedClassmate = classmate;
    }

    public Classmate getSelectedClassmate() {
        return selectedClassmate;
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

    public boolean useCtrlC(Question question) {
        if (debugToolsUsage.get("CtrlC")) {
            System.out.println("Ctrl C tool has already been used.");
            return false;
        }

        if (selectedClassmate == null) {
            System.out.println("No classmate selected for Ctrl C.");
            return false;
        }

        boolean correct = debugTools.ctrlC(selectedClassmate, question);
        return correct;
    }
}
