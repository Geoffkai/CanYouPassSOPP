package src.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    public enum Category {
        Theoretical, Programming
    }

    private List<Question> activeQuestions = new ArrayList<>();

    public void loadCategory(Category category) {
        activeQuestions.clear();

        String fileName = (category == Category.Theoretical)
                ? "C:/Users/LENOVO/OneDrive/Dokumen/2nd 1st/CMSC 13/Machine Problem Files/theoretical_questions.csv"
                : "C:/Users/LENOVO/OneDrive/Dokumen/2nd 1st/CMSC 13/Machine Problem Files/programming_questions.csv";

        try (

                BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String data[] = line.split(",", -1);

                String question = data[0].trim();
                String[] choices = { data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim() };
                int correctIndex = Integer.parseInt(data[5].trim());
                String difficulty = data[6].trim();

                Question q = new Question("Procedural", question, choices, correctIndex, difficulty);
                activeQuestions.add(q);
            }
            System.out.println("[QuestionBank] Loaded " + activeQuestions.size() + " questions from "
                    + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionsByLevel(String level, int count) {
        List<Question> result = new ArrayList<>();

        // This gets all the questions with difficulty na tinawag
        for (Question q : activeQuestions) {
            if (level.equals(q.getDifficulty())) {
                result.add(q);
            }
        }
        Collections.shuffle(result);
        return result.subList(0, Math.min(count, result.size()));
    }
}
