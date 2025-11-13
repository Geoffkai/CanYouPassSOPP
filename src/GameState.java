package src;
import javax.swing.*;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;

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
}
