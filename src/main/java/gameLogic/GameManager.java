package gameLogic;

import entities.Card;
import entities.Deck;
import entities.Player;
import recording.Leaderboard;
import userInterface.gameUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private final Deck deck;
    private final Player player;
    private final gameUI gameUi;
    private final Leaderboard leaderboard;
    private final List<Card> discardedCards;
    private Card computerCard;
    private int currentScore;
    private boolean isGameOver = false;

    public GameManager(Player player, gameUI gameUi) {
        this.deck = new Deck();
        this.player = player;
        this.gameUi = gameUi;
        // Provide a correct file path for your environment:
        this.leaderboard = new Leaderboard("src/main/java/resources/highscores.json");
        this.discardedCards = new ArrayList<>();
        this.currentScore = 0;
    }

    public void init() {
        // 1. Display welcome, leaderboard, etc.
        gameUi.initialMessage(player);
        leaderboard.displayScores();

        // 2. Deal initial cards (5 to player)
        dealFirstCards();
        gameUi.displayHand(player);

        // 3. Computer’s first card
        computerCard = deck.dealCard();
        gameUi.displayComputerCard(computerCard);
    }

    private void dealFirstCards() {
        for (int i = 0; i < 5; i++) {
            if (!deck.isEmpty()) {
                player.addCardToHand(deck.dealCard());
            }
        }
    }

    public void gameLoop() {
        // Main loop until game isOver
        while (!isGameOver) {
            try {
                // If no valid moves, break
                if (!Rules.isValidMove(player.getPlayerHand(), computerCard)) {
                    break;
                }

                // Show the hand
                gameUi.displayHand(player);

                // Ask for a card
                Card playerCard = gameUi.askPlayerForCard(player);

                // Remove from player's hand, add to discard
                player.dequeueCardInHand(playerCard);
                discardedCards.add(playerCard);

                // Check if the user made 15
                if (Rules.sumIs15(playerCard, computerCard)) {
                    currentScore++;
                    gameUi.displayMaking15();

                    if (!dealReplacementCard()) {
                        break; // deck empty
                    }

                    // Optional: replace picture cards
                    if (!replacePictureCards()) {
                        break; // deck empty inside that method
                    }

                    // Else if same suit
                } else if (Rules.matchesSuit(playerCard, computerCard)) {
                    // No point, but we still replace the player's card
                    gameUi.displayCardSameSuit(playerCard);

                    if (!dealReplacementCard()) {
                        break;
                    }

                    // Computer’s new card
                    if (!deck.isEmpty()) {
                        computerCard = deck.dealCard();
                        gameUi.displayComputerCard(computerCard);
                    } else {
                        gameUi.displayDeckEmpty();
                        gameUi.displayFinalScore(currentScore);
                        isGameOver = true;
                        break;
                    }

                    // Otherwise invalid
                } else {
                    gameUi.displayInvalidMove();
                    isGameOver = true;
                    break;
                }

            } catch (Exception e) {
                System.out.println("An error occurred, Game over!");
                gameUi.displayFinalScore(currentScore);
                isGameOver = true;
            }
        } // end while

        // 4. Once the loop ends, finalize the game and update leaderboard
        if (!isGameOver) {
            // If we exit the loop because no valid moves remain (not from an exception)
            gameUi.displayFinalScore(currentScore);
        } else {
            // Already displayed final score inside the exception or forced end
            gameUi.displayFinalScore(currentScore);
        }

        // 5. Prompt user for name, update scoreboard
        changeAndDisplayLeaderboard();
    }

    /**
     * Attempt to deal a replacement card to the player and display updated hand.
     *
     * @return false if the deck is empty so the game ends, true otherwise.
     */
    private boolean dealReplacementCard() {
        if (!deck.isEmpty()) {
            player.addCardToHand(deck.dealCard());
            gameUi.displayHand(player);
            return true;
        } else {
            gameUi.displayDeckEmpty();
            gameUi.displayFinalScore(currentScore);
            isGameOver = true;
            return false;
        }
    }

    /**
     * Let the user replace any picture cards, and display updated hand each time.
     *
     * @return false if deck empties, true otherwise
     */
    private boolean replacePictureCards() {
        List<Card> cardsToReplace = gameUi.askToReplacePictureCards(player);
        for (Card card : cardsToReplace) {
            player.dequeueCardInHand(card);
            discardedCards.add(card);
            if (!deck.isEmpty()) {
                player.addCardToHand(deck.dealCard());
                gameUi.displayHand(player);
            } else {
                gameUi.displayDeckEmpty();
                gameUi.displayFinalScore(currentScore);
                isGameOver = true;
                return false;
            }
        }
        return true;
    }

    /**
     * Called at the end of the game to record final score, update leaderboard, and show it.
     */
    public void changeAndDisplayLeaderboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name for the leaderboard: ");
        String name = scanner.nextLine();

        leaderboard.addEntry(name, currentScore);
        leaderboard.saveScores();

        leaderboard.displayScores();
    }
}
