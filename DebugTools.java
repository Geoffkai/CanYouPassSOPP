import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Debug Tools Class logic
public class DebugTools {

    // Refactor logic
    // So ang logic ko dito is kukunin nya yung correct index as parameter, then
    // magrereturn siya ng index of 2 choices 1 correct and 1 wrong
    public List<Integer> refactor(int correctIndex) {
        List<Integer> indices = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

        List<Integer> correctIndices = new ArrayList<>();
        correctIndices.add(correctIndex);

        indices.remove(correctIndex);
        Collections.shuffle(indices);

        correctIndices.add(indices.get(0));

        Collections.shuffle(correctIndices);
        return correctIndices;
    }

    public void consoleLog(Question question) {
        Classmate classmate = new Classmate();
        System.out.println("\n[Debug Tool - Console Log] Here's a hint from your classmate:");
        System.out.println(classmate.answer);
    }

    public boolean ctrlC(boolean playerAnswer, boolean classmateCorrect) {
        if (!playerAnswer && classmateCorrect) {
            System.out.println("[Ctrl+C] You were saved!");
            return true;
        }
        return playerAnswer;
    }
}
