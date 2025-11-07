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
                return 200;
            case "L3":
                return 300;
            case "L4":
                return 400;
            case "L5":
                return 500;
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
}
