package userInterface;

import entities.Player;
import gameLogic.GameManager;
import recording.Leaderboard;

import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleManager {
    private static Scanner scanner;
    private Logger logger;
    private static Leaderboard leaderboard;

    public ConsoleManager(Leaderboard leaderboard) {
        this.scanner = new Scanner(System.in);
        this.logger = Logger.getLogger(ConsoleManager.class.getName());
        this.leaderboard = leaderboard; // Ensure leaderboard is properly initialized
    }

    public void startMenu() {
        boolean running = true;

        while (running) {
            MenuDisplay.clearDisplay();
            MenuDisplay.WelcomeMessage();
            MenuDisplay.mainMenu();

            System.out.print("\nEnter choice: ");
            String usersChoice = scanner.nextLine();
            System.out.println("\n");
            MenuDisplay.clearDisplay();

            switch (usersChoice) {
                case "1":
                    StartNewGame();
                    break;
                case "2":
                    clearDisplay();
                    leaderboard.displayScores();
                    System.out.println("Press Enter to go back: ");
                    scanner.nextLine();
                    break;
                case "3":
                    clearDisplay();
                    MenuDisplay.helpMenu();
                    System.out.println("Press Enter to go back: ");
                    scanner.nextLine();
                    break;
                case "4":
                    exitGame();
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    public void StartNewGame() {
        Player player = new Player();
        GameManager gameManager = new GameManager(player, new gameUI());
        gameManager.init();
        gameManager.gameLoop();
    }

    public void viewLeaderboard() {
        clearDisplay();
        leaderboard.displayScores();
    }

    public void getHelp() {
        MenuDisplay.helpMenu();
    }

    public void exitGame() {
        System.exit(0);
    }

    public static void clearDisplay() {
        System.out.println("\r\n".repeat(50));


    }
}

