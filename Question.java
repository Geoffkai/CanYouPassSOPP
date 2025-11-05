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

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(char choice) {
        return correctChoice == choice;
    }
}
