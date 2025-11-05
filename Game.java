import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Quiz Game!");
        QuestionBank ques = new QuestionBank();
        int score = 0;

        for (int i = 0; i < ques.questions.size(); i++) {
            char choice;
            System.out.println("Question: " + ques.tanong.get(i).getQuestionText());
            for (String option : ques.tanong.get(i).getOptions()) {
                System.out.println(option);
            }

            System.out.print("Enter your choice: ");
            choice = scanner.next().charAt(0);

            if (ques.tanong.get(i).isCorrect(choice)) {
                System.out.println("Congratulations you got the correct answer!");

                score += ques.questions.get(ques.tanong.get(i));
                System.out.println("Score:" + score);
            } else {
                System.out.println("Wrong answer!");
                System.out.println("Score:" + score);
            }
        }

        scanner.close();

    }
}
