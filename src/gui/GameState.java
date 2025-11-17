package src.gui;

import javax.swing.*;
import src.logic.GameManager;
import src.logic.Player;
import src.logic.QuestionBank;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;
    private static Player currentPlayer;
    private static QuestionBank.Category selectedCategory;
    private static GameManager gameManager;

    private static final ImageIcon Geoff = new ImageIcon(
            new ImageIcon("src/img/Character/Geoff.png").getImage().getScaledInstance(411, 426,
                    java.awt.Image.SCALE_SMOOTH));
    private static final ImageIcon Yvonne = new ImageIcon(
            new ImageIcon("src/img/Character/Yvonne.png").getImage().getScaledInstance(411,
                    426, java.awt.Image.SCALE_SMOOTH));

    public static String getCharacter() {
        return selectedCharacter;
    }

    public static void setCharacter(String character) {
        selectedCharacter = character;
    }

    public static String getLevel() {
        return selectedLevel;
    }

    public static void setLevel(String level) {
        selectedLevel = level;
    }

    // Player management
    public static Player getPlayer() {
        return currentPlayer;
    }

    public static void setPlayer(Player player) {
        currentPlayer = player;
    }

    // Category management
    public static QuestionBank.Category getCategory() {
        return selectedCategory;
    }

    public static void setCategory(QuestionBank.Category category) {
        selectedCategory = category;
    }

    // GameManager management
    public static GameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(GameManager manager) {
        gameManager = manager;
    }

    // Get character icon
    public static ImageIcon getCharacterIcon(String characterName) {
        if ("Geoff".equals(characterName)) {
            return Geoff;
        } else if ("Yvonne".equals(characterName)) {
            return Yvonne;
        } else if ("Anon".equals(characterName) || "Elmer".equals(characterName) || "Merry".equals(characterName)) {
            // For characters without specific icons, return null (will be handled
            // gracefully)
            return null;
        }
        return null;
    }
}
