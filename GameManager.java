public class GameManager {
    private QuestionBank questionBank;
    private Player currentPlayer;
    private Question currQuestion;
    private int currQuestionIndex;
    private boolean debugUsed;

    public GameManager() {
        questionBank = new QuestionBank();

    }
}
