package gameLogic;

import entities.Card;
import entities.Deck;
import entities.Player;
import recording.Leaderboard;
import userInterface.gameUI;
import recording.ReplayGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {

    private final Scanner scanner;
    private final Deck deck;
    private final Player player;
    private final gameUI gameUi;
    private final Leaderboard leaderboard;
    private final ReplayGame replayGame;
    private final List<Card> discardedCards;
    private Card computerCard;
    private int currentScore;
    private boolean isGameOver = false;

    public GameManager(Player player, gameUI gameUi) {
        this.scanner = new Scanner(System.in);
        this.deck = new Deck();
        this.player = player;
        this.gameUi = gameUi;
        this.leaderboard = new Leaderboard("src/main/java/resources/highscores.json");
        this.replayGame = new ReplayGame();
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
        while (!isGameOver) {
            try {
                if (!Rules.isValidMove(player.getPlayerHand(), computerCard)) {
                    break;
                }

                Card playerCard = gameUi.askPlayerForCard(player);

                player.dequeueCardInHand(playerCard);
                discardedCards.add(playerCard);

                if (Rules.sumIs15(playerCard, computerCard)) {
                    currentScore++;
                    gameUi.displayMaking15();

                    // Dequeue old card from hand
                    // (You might have done this already, depending on your structure.)
                    // player.dequeueCardInHand(playerCard);

                    // Deal exactly one replacement card
                    if (!deck.isEmpty()) {
                        player.addCardToHand(deck.dealCard());
                        gameUi.displayHand(player);
                    } else {
                        gameUI.displayDeckEmpty();
                        gameUi.displayFinalScore(currentScore);
                        return;
                    }

                    // Now replace picture cards
                    List<Card> pictureCards = gameUi.askToReplacePictureCards(player);
                    for (Card pic : pictureCards) {
                        if (Rules.isPictureCard(pic)) {
                            player.dequeueCardInHand(pic);
                            discardedCards.add(pic);
                            if (!deck.isEmpty() && player.getPlayerHand().size() < Rules.maxCardsInHand) {
                                player.addCardToHand(deck.dealCard());
                                gameUi.displayHand(player);
                            } else {
                                gameUI.displayDeckEmpty();
                                gameUI.displayFinalScore(currentScore);
                                return;
                            }
                        } else {
                            System.out.println("Not a picture card!");
                        }
                    }

                    // Computer will deal a new card
                    if (!deck.isEmpty()) {
                        computerCard = deck.dealCard();
                        gameUi.displayComputerCard(computerCard);
                    } else {
                        gameUi.displayDeckEmpty();
                        gameUi.displayFinalScore(currentScore);
                        isGameOver = true;
                        break;
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

                replayGame.logRound(
                        new ArrayList<>(player.getPlayerHand()),
                        computerCard,
                        List.of(playerCard), // or multiple cards if needed
                        "Made 15" // or "Same Suit", "Invalid Move", etc.
                );


            } catch (Exception e) {
                System.out.println("An error occurred, Game over!");
                gameUi.displayFinalScore(currentScore);
                isGameOver = true;
            }
        } // end while

        // 4. Once the loop ends, finalize the game and update leaderboard
            // If we exit the loop because no valid moves remain (not from an exception)
            if (!isGameOver) {
                gameUi.displayFinalScore(currentScore);
                System.out.println("Would you like to watch the replay? (Y/N)");
                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("Y")) {
                    replayGame.displayReplay(false);
                }
        }


        // 5. Prompt user for name, update scoreboard
        changeAndDisplayLeaderboard();
    }


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


    private boolean replacePictureCards() {
        // Ask the player which picture cards they want to replace
        List<Card> cardsToReplace = gameUi.askToReplacePictureCards(player);

        if (cardsToReplace.isEmpty()) {
            System.out.println("No picture cards selected for replacement. Continuing the game.");
            return true; // No replacement needed, game continues
        }

        // Replace each selected picture card
        for (Card card : cardsToReplace) {
            // Remove the card from the player's hand
            player.dequeueCardInHand(card);
            discardedCards.add(card); // Add the removed card to the discard pile

            // Replace the card with a new one from the deck, if available
            if (!deck.isEmpty()) {
                player.addCardToHand(deck.dealCard());
            } else {
                // End the game if the deck is empty
                gameUi.displayDeckEmpty();
                gameUi.displayFinalScore(currentScore);
                isGameOver = true;
                return false;
            }
        }

        return true; // Replacement successful, game continues
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
