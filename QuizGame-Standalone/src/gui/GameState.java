package gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import logic.GameManager;
import logic.Player;
import logic.Question;
import logic.QuestionBank;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;
    private static String selectedTopic;
    private static Player currentPlayer;
    protected static QuestionBank.Category selectedCategory;
    private static GameManager gameManager;
    private static Question currentQuestion;

    // savedTopicsByCategory: categoryKey -> (slot -> topicCode)
    private static Map<String, Map<String, String>> savedTopicsByCategory = new HashMap<>();
    // usedTopicsByCategory: categoryKey -> set of topicCodes used
    private static Map<String, java.util.Set<String>> usedTopicsByCategory = new HashMap<>();
    // usedSlotsByCategory: categoryKey -> set of slot ids used (e.g., "2a")
    private static Map<String, java.util.Set<String>> usedSlotsByCategory = new HashMap<>();
    // selectedSlotForCategory: categoryKey -> slot (the slot player opened)
    private static Map<String, String> selectedSlotForCategory = new HashMap<>();

    private static final ImageIcon Geoff = new ImageIcon(
            new ImageIcon("src/img/Character/Geoff.png").getImage().getScaledInstance(411, 426,
                    java.awt.Image.SCALE_SMOOTH));
    private static final ImageIcon Yvonne = new ImageIcon(
            new ImageIcon("src/img/Character/Yvonne.png").getImage().getScaledInstance(411,
                    426, java.awt.Image.SCALE_SMOOTH));
    private static final ImageIcon Anon = new ImageIcon(
            new ImageIcon("src/img/Character/Anon.png").getImage().getScaledInstance(411,
                    426, java.awt.Image.SCALE_SMOOTH));
    private static final ImageIcon Elmer = new ImageIcon(
            new ImageIcon("src/img/Character/Elmer.png").getImage().getScaledInstance(411,
                    426, java.awt.Image.SCALE_SMOOTH));
    private static final ImageIcon Merry = new ImageIcon(
            new ImageIcon("src/img/Character/Merry.png").getImage().getScaledInstance(411,
                    426, java.awt.Image.SCALE_SMOOTH));
    private static ImageIcon topicIcon;

    // --- Topic icon ---
    public static void setTopicIcon(ImageIcon icon) {
        topicIcon = icon;
    }

    public static void resetGame() {
        selectedCharacter = null;
        selectedLevel = null;
        selectedTopic = null;
        selectedCategory = null;
        currentPlayer = null;
        currentQuestion = null;
        gameManager = null;
        topicIcon = null;

        savedTopicsByCategory.clear();
        usedTopicsByCategory.clear();
        usedSlotsByCategory.clear();
        selectedSlotForCategory.clear();
    }

    public static ImageIcon getTopicIcon() {
        return topicIcon;
    }

    // --- Character ---
    public static String getCharacter() {
        return selectedCharacter;
    }

    public static void setCharacter(String character) {
        selectedCharacter = character;
    }

    // --- Level ---
    public static String getLevel() {
        return selectedLevel;
    }

    public static void setLevel(String level) {
        selectedLevel = level;
    }

    // --- Topic string ---
    public static void setTopic(String topic) {
        selectedTopic = topic;
    }

    public static String getTopic() {
        return selectedTopic;
    }

    // --- Current Question ---
    public static void setQuestion(Question q) {
        currentQuestion = q;
    }

    public static Question getQuestion() {
        return currentQuestion;
    }

    // --- Player management ---
    public static Player getPlayer() {
        return currentPlayer;
    }

    public static void setPlayer(Player player) {
        currentPlayer = player;
    }

    // --- Category management ---
    public static QuestionBank.Category getCategory() {
        return selectedCategory;
    }

    public static void setCategory(QuestionBank.Category category) {
        selectedCategory = category;
    }

    // --- GameManager management ---
    public static GameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(GameManager manager) {
        gameManager = manager;
    }

    // --- Character icons ---
    public static ImageIcon getCharacterIcon(String characterName) {
        if ("Geoff".equals(characterName))
            return Geoff;
        else if ("Yvonne".equals(characterName))
            return Yvonne;
        else if ("Anon".equals(characterName))
            return Anon;
        else if ("Elmer".equals(characterName))
            return Elmer;
        else if ("Merry".equals(characterName))
            return Merry;
        else
            return null;
    }

    public static String getCategoryKey() {
        logic.QuestionBank.Category cat = getCategory();
        return (cat == null) ? "Default" : cat.name();
    }

    // Saved topic methods
    public static boolean hasSavedTopicsForCategory(String categoryKey) {
        Map<String, String> m = savedTopicsByCategory.get(categoryKey);
        return m != null && !m.isEmpty();
    }

    public static void saveTopicForCategory(String categoryKey, String slot, String topicCode) {
        savedTopicsByCategory.computeIfAbsent(categoryKey, k -> new HashMap<>()).put(slot, topicCode);
    }

    public static String getSavedTopicForCategory(String categoryKey, String slot) {
        Map<String, String> m = savedTopicsByCategory.get(categoryKey);
        return (m == null) ? null : m.get(slot);
    }

    // Optional: store icon for topic per category if you want (kept for
    // compatibility)
    public static void setTopicIconForCategory(String categoryKey, javax.swing.ImageIcon icon) {
        setTopicIcon(icon);
    }

    // Used-topic methods
    public static void markTopicUsedForCategory(String categoryKey, String topicCode) {
        usedTopicsByCategory.computeIfAbsent(categoryKey, k -> new java.util.HashSet<>()).add(topicCode);
    }

    public static boolean isTopicUsedForCategory(String categoryKey, String topicCode) {
        java.util.Set<String> s = usedTopicsByCategory.get(categoryKey);
        return s != null && s.contains(topicCode);
    }

    // --- Slot-based usage (prevents disabling sibling button when same topicCode
    // used)
    public static void markSlotUsedForCategory(String categoryKey, String slot) {
        usedSlotsByCategory.computeIfAbsent(categoryKey, k -> new java.util.HashSet<>()).add(slot);
    }

    public static boolean isSlotUsedForCategory(String categoryKey, String slot) {
        java.util.Set<String> s = usedSlotsByCategory.get(categoryKey);
        return s != null && s.contains(slot);
    }

    public static void setSelectedSlotForCategory(String categoryKey, String slot) {
        if (categoryKey == null || slot == null)
            return;
        selectedSlotForCategory.put(categoryKey, slot);
    }

    public static String getSelectedSlotForCategory(String categoryKey) {
        return selectedSlotForCategory.get(categoryKey);
    }

    // Convenience clear on retry (resets saved topics and used topics for all
    // categories)
    public static void clearAllSavedTopicsAndUsed() {
        savedTopicsByCategory.clear();
        usedTopicsByCategory.clear();
        usedSlotsByCategory.clear();
        selectedSlotForCategory.clear();
    }

    // Next-category logic (two categories)
    public static QuestionBank.Category getNextCategory() {
        if (selectedCategory == QuestionBank.Category.Theoretical) {
            return QuestionBank.Category.Programming;
        }
        if (selectedCategory == QuestionBank.Category.Programming) {
            return QuestionBank.Category.Theoretical;
        }
        return null;
    }

    // === SAFE reset: clear only the slot usage (do NOT remove saved layout/icons)
    public static void resetSlotUsageForCategory(String categoryKey) {
        usedSlotsByCategory.remove(categoryKey);
        usedTopicsByCategory.remove(categoryKey);
        selectedSlotForCategory.remove(categoryKey);
    }
}
