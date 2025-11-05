public class Question {
    private String questionText;
    private String[] options;
    private char correctChoice;

    public Question(String questionText, String[] options, char correctChoice) {
        this.questionText = questionText;
        this.options = options;
        this.correctChoice = correctChoice;
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
