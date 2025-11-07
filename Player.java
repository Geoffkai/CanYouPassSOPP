public class Player {
    private String username;
    private int score;
    private int correctCount;

    public Player(String username) {
        this.username = username;
        this.score = 0;
        this.correctCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void deductScore(int points) {
        this.score -= points;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void incrementCorrect() {
        correctCount++;
    }
}
