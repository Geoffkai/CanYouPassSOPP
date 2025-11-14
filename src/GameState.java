package src;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;
    private static Set<String> completedTopics = new HashSet<>();

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

    public static void markCompleted(String topicName) {
        completedTopics.add(topicName);
    }

    public static boolean isCompleted(String topicName) {
        return completedTopics.contains(topicName);
    }
}
