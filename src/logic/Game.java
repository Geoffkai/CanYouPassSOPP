package src.logic;

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Programming Paradigms Quiz Game!");

        System.out.print("Enter your player name: ");
        String name = scanner.nextLine();
        Player player = new Player(name);
        QuestionBank bank = new QuestionBank();

        // Create and start game using GameManager
        GameManager manager = new GameManager(bank, player);

        // GameManager automatically runs startGame(), category choice, and gameLoop()
        // so we don't need to manually loop questions here.

        System.out.println("\n🎉 Thanks for playing, " + name + "!");
        System.out.println("Your final score: " + player.getScore());
        System.out.println("Correct answers: " + player.getCorrectCount());

        scanner.close();

    }
}
