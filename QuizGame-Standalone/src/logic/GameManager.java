package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.characters.Classmate;
import logic.data.Record;
import logic.tools.DebugTools;

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
        // Reset and load questions for the new category
        this.availableQuestions.clear();
        this.debugToolsUsage.clear();
        initializeDebugTools();
        loadQuestions();
    }

    // Load questions based on the loaded category
    public void loadQuestions() {
        availableQuestions.clear();

        // Unified logic: both categories load 2 randomized questions per level.
        // (Programming still distinguishes topics via JSON keys; mapping handled
        // elsewhere.)
        for (int i = 1; i <= 5; i++) {
            List<Question> levelQuestions = questionBank.getQuestionsByLevel("L" + i, 2);
            availableQuestions.addAll(levelQuestions);
        }

        // Log the selected questions for debugging/verification (topic + level)
        System.out.println("[GameManager] Selected questions (" + availableQuestions.size() + "):");
        int idx = 1;
        for (Question q : availableQuestions) {
            String text = q.getQuestionText();
            String preview = text.length() > 120 ? text.substring(0, 120) + "..." : text;
            System.out.println(
                    String.format("  %02d) [Level=%s Topic=%s] %s", idx++, q.getDifficulty(), q.getTopic(), preview));
            System.out.println(q.getCorrectChoice());
        }
    }

    public Classmate selectClassmate() {
        return null;
    }

    // Get question by topic code (e.g., "Prod5", "Func1", etc.)
    public Question getQuestionByTopicCode(String topicCode) {
        // Parse topic code to get difficulty (last character) and topic prefix
        if (topicCode == null || topicCode.length() < 2) {
            return null;
        }

        // If topicCode looks like "L5" (fallback from GameScreen), just return any
        // question of that difficulty (prefer one not currently set).
        if (topicCode.startsWith("L") && topicCode.length() == 2) {
            String difficulty = topicCode; // e.g. "L5"
            // Try to find a question of this difficulty that is not the currentQuestion
            for (Question q : availableQuestions) {
                if (q.getDifficulty().equals(difficulty) && q != currentQuestion) {
                    currentQuestion = q;
                    return q;
                }
            }
            // Fallback: return any with matching difficulty
            for (Question q : availableQuestions) {
                if (q.getDifficulty().equals(difficulty)) {
                    currentQuestion = q;
                    return q;
                }
            }
            return currentQuestion;
        }

        // Normal case: topicCode like "Prod5" or "Func3"
        String difficulty = "L" + topicCode.substring(topicCode.length() - 1);
        String topicPrefix = topicCode.substring(0, topicCode.length() - 1);
        String topicName = mapTopicPrefixToName(topicPrefix);

        // Collect all matching questions for this topic+level
        List<Question> matches = new ArrayList<>();
        for (Question q : availableQuestions) {
            if (q.getDifficulty().equals(difficulty)
                    && q.getTopic() != null
                    && q.getTopic().toLowerCase().contains(topicName.toLowerCase())) {
                matches.add(q);
            }
        }

        // Prefer a match that is not the currentQuestion (so two buttons don't get the
        // same instance)
        for (Question q : matches) {
            if (q != currentQuestion) {
                currentQuestion = q;
                return q;
            }
        }

        // If all matches are the currentQuestion or there is only one match, return the
        // first match
        if (!matches.isEmpty()) {
            currentQuestion = matches.get(0);
            return currentQuestion;
        }

        // No topic-specific match: pick any question of the same difficulty not equal
        // to currentQuestion
        for (Question q : availableQuestions) {
            if (q.getDifficulty().equals(difficulty) && q != currentQuestion) {
                currentQuestion = q;
                return q;
            }
        }

        // As a last resort, return whatever the currentQuestion is (could be null)
        return currentQuestion;
    }

    private String mapTopicPrefixToName(String prefix) {
        // Map topic codes to topic names
        switch (prefix) {
            case "CTO":
                return "code_to_output"; // programming.json key
            case "OTC":
                return "output_to_code"; // programming.json key
            case "ML":
                return "fill_in_blank"; // programming.json key
            case "Prod":
                return "procedural";
            case "Func":
                return "functional";
            case "OOP":
                return "object";
            case "EVDR":
                return "event";
            case "Intro":
                return "intro";
            case "IVD":
                return "imperative"; // or declarative
            case "MAP":
                return "mapping";
            default:
                return prefix.toLowerCase();
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

        // After answering, remove the question from available questions so it won't be
        // served again and clear currentQuestion to avoid accidental reuse.
        availableQuestions.remove(currentQuestion);
        currentQuestion = null;

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
        // Mark the tool as used regardless of outcome
        debugToolsUsage.put("CtrlC", true);
        return correct;
    }

    // Mark an auto-debug usage (GUI calls this when AutoDebug is triggered)
    public void useAutoDebugTool() {
        debugToolsUsage.put("AutoDebug", true);
    }
}
