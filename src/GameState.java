package src;
import javax.swing.*;

public class GameState {
    private static String selectedCharacter;
    private static String selectedLevel;

    ImageIcon Geoff = new ImageIcon(new ImageIcon("src/img/Character/Geoff.png").getImage().getScaledInstance(411, 426, java.awt.Image.SCALE_SMOOTH));
    ImageIcon Yvonne = new ImageIcon(new ImageIcon("src/img/Character/Yvonne.png").getImage().getScaledInstance(411, 426, java.awt.Image.SCALE_SMOOTH));

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
