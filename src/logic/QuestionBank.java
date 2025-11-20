package logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionBank {
    public enum Category {
        Theoretical, Programming
    }

    private List<Question> activeQuestions = new ArrayList<>();

    public void loadCategory(Category category) {
        activeQuestions.clear();

        String fileName = (category == Category.Theoretical)
                ? "theoretical.json"
                : "programming.json";

        try (

                FileReader fr = new FileReader(fileName)) {
            // Read the JSON file and convert it to a string, then later icoconvert to
            // JSONObject which is readable by the program
            StringBuilder jsonContent = new StringBuilder();
            int character;
            // Ito na yung loop to convert the json file to a string
            while ((character = fr.read()) != -1) {
                jsonContent.append((char) character);
            }

            // Parse the JSON
            JSONObject jsonObject = new JSONObject(jsonContent.toString());

            // Iterate through the topics, each topic is a key in the json object
            for (String topic : jsonObject.keySet()) {
                // Ito hawak nito yung array of questions for the topic
                JSONArray questionsArray = jsonObject.getJSONArray(topic);

                // Iterate through the questions in the topic
                for (int i = 0; i < questionsArray.length(); i++) {
                    JSONObject questionObject = questionsArray.getJSONObject(i);

                    // Extract the data from the questions array
                    String questionText = questionObject.getString("question");
                    JSONArray choicesArray = questionObject.getJSONArray("choices");
                    String[] choices = new String[choicesArray.length()];
                    for (int j = 0; j < choicesArray.length(); j++) {
                        choices[j] = choicesArray.getString(j);
                    }
                    int correctIndex = questionObject.getInt("correctIndex");
                    String difficulty = questionObject.getString("level");

                    // Create the question base from the JSON
                    Question q = new Question(topic, questionText, choices, correctIndex, difficulty);
                    activeQuestions.add(q);
                }
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