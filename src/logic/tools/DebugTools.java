package src.logic.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import src.logic.Question;
import src.logic.characters.Classmate;

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

    public void consoleLog(Classmate classmate, Question question) {
        int chosenIndex = classmate.chooseAnswerIndex(question);
        String chosenAnswer = question.getOptions()[chosenIndex];

        System.out.println("You used Console Log!" + classmate.getName() + " thinks the answer is: " + chosenAnswer);
    }

    public boolean ctrlC(Classmate classmate, Question question) {
        int chosenIndex = classmate.chooseAnswerIndex(question);
        String chosenAnswer = question.getOptions()[chosenIndex];

        System.out.println(classmate.getName() + " suggests you choose: " + chosenAnswer);

        boolean isCorrect = (chosenIndex == question.getCorrectChoice());
        if (isCorrect) {
            System.out.println("That's correct! You copied the right answer!");
        } else {
            System.out.println("Uh oh... " + classmate.getName() + " got it wrong.");
        }
        return isCorrect;
    }
}
