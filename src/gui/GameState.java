package src.gui;

import javax.swing.*;
import src.logic.GameManager;
import src.logic.Player;
import src.logic.QuestionBank;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;
    private static String selectedTopic;
    private static Player currentPlayer;
    private static QuestionBank.Category selectedCategory;
    private static GameManager gameManager;

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

    public static void setTopicIcon(ImageIcon icon) {
        topicIcon = icon;
    }

    public static ImageIcon getTopicIcon() {
        return topicIcon;
    }

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

    public static void setTopic(String topic) {
        selectedTopic = topic;
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
        } else if ("Anon".equals(characterName)) {
            return Anon;
        } else if ("Elmer".equals(characterName)) {
            return Elmer;
        } else if ("Merry".equals(characterName)) {
            return Merry;
        }else{
            return null;
        }
    }
}
