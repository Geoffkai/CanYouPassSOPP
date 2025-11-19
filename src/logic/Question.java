package src.logic;

public class Question {
    private String questionText;
    private String[] options;
    private int correctChoice;
    private String topic;
    private String difficulty;

    public Question(String topic, String questionText, String[] options, int correctChoice, String difficulty) {
        this.questionText = questionText;
        this.options = options;
        this.correctChoice = correctChoice;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getDifficultyPoints() {
        switch (difficulty) {
            case "L1":
                return 100;
            case "L2":
                return 250;
            case "L3":
                return 500;
            case "L4":
                return 750;
            case "L5":
                return 1000;
            default:
                return 0;
        }
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int choice) {
        return correctChoice == choice;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }
}
