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

            System.out.println("\n Enter choice: ");
            String usersChoice = scanner.nextLine();
            logger.info("User entered choice: " + usersChoice);

            switch (usersChoice) {
                case "1":
                    logger.info("Starting new game...");
                    StartNewGame();
                    break;
                case "2":
                    logger.info("Viewing leaderboard...");
                    leaderboard.displayScores();
                    break;
                case "3":
                    logger.info("Replaying previous game...");
                    replayGame();
                    break;
                case "4":
                    logger.info("Displaying help menu...");
                    MenuDisplay.helpMenu();
                    break;
                case "5":
                    logger.info("Exiting game...");
                    exitGame();
                    break;
                default:
                    logger.info("Invalid choice, please try again");
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

    public void replayGame() {
        System.out.println("Not done!");
    }

    public void exitGame() {
        System.exit(0);
    }

    public static void clearDisplay() {
        System.out.println("\r\n".repeat(50));


    }
}

