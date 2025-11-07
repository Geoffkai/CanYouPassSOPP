
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    private QuestionBank questionBank;
    private Player player;
    private Question currentQuestion;
    private boolean debugUsed;
    private QuestionBank.Category chosenCategory;
    private List<Question> questionStore;
    private String chosenTopic;
    private int currentQuestionIndex;
    private Map<DebugTools, Integer> debugToolUsage;
    private boolean isGameActive;
    private Record recordManager = new Record();
    Scanner scanner = new Scanner(System.in);

    public GameManager(QuestionBank questionBank, Player currentPlayer) {
        this.questionBank = (questionBank != null) ? questionBank : new QuestionBank();
        this.player = currentPlayer;
        this.currentQuestionIndex = 0;
        this.debugUsed = false;
        this.isGameActive = false;
        this.questionStore = new ArrayList<>();
        this.debugToolUsage = new HashMap<>();
        this.currentQuestion = null;
        startGame();
    }

    public void startGame() {
        System.out.println("[GameManager] Starting game...");
        isGameActive = true;
        chooseCategory();
        getQuestions();
        gameLoop();
    }

    public QuestionBank.Category chooseCategory() {
        System.out.println("[GameManager] Choosing category...");
        System.out.println("1. Theoretical");
        System.out.println("2. Programming");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            chosenCategory = QuestionBank.Category.Theoretical;
            questionBank.loadCategory(QuestionBank.Category.Theoretical);
        } else if (choice == 2) {
            chosenCategory = QuestionBank.Category.Programming;
            questionBank.loadCategory(QuestionBank.Category.Programming);
        }
        return chosenCategory;
    }

    public Classmate selectClassmate() {
        return null;
    }

    public void getQuestions() {
        questionStore.clear();
        for (int i = 1; i <= 5; i++) {
            List<Question> levelQuestions = questionBank.getQuestionsByLevel("L" + i, 2);
            questionStore.addAll(levelQuestions);
        }
        System.out.println("[GameManager] Loaded " + questionStore.size() + " questions into the game.");
    }

    public Question pickTopicandLevel(String topic, String difficulty) {
        for (Question q : questionStore) {
            if (q.getTopic().equals(topic)) {
                return q;
            }
        }
        return null;
    }

    public void gameLoop() {
        while (isGameActive && currentQuestionIndex < questionStore.size()) {
            currentQuestion = questionStore.get(currentQuestionIndex);
            presentQuestion(currentQuestion);
            currentQuestionIndex++;
        }
        endGame();
    }

    public void presentQuestion(Question question) {
        System.out.println("\n[Level " + question.getDifficulty() + "] " + question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();

        boolean correct = submitAnswer(choice);
        if (correct) {
            System.out.println("Correct answer!");
        } else {
            System.out.println("Wrong answer!");

        }
    }

    public boolean submitAnswer(int choiceIndex) {
        boolean correct = currentQuestion.isCorrect(choiceIndex);
        int points = currentQuestion.getDifficultyPoints();

        if (correct) {
            player.addScore(points);
            player.incrementCorrect();
            System.out.println("Correct! +" + points + " pts");
        } else {
            player.deductScore(points);
            System.out.println("Wrong! -" + points + " pts");
        }
        return correct;
    }

    public void endGame() {
        isGameActive = false;

        System.out.println("\n[GameManager] Game Over!");
        System.out.println("Final Score: " + player.getScore());
        System.out.println("Correct Answers: " + player.getCorrectCount());
        // recordManager.saveRecord(player);
    }
}
