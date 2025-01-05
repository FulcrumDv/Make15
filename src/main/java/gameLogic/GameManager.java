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
        // Display welcome, leaderboard, etc.
        gameUi.initialMessage(player);
        leaderboard.displayScores();

        // Deal initial cards (5 to player)
        dealFirstCards();
        gameUi.displayHand(player);

        // Computer’s first card
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
                // Check if a valid move is even possible
                if (!Rules.isValidMove(player.getPlayerHand(), computerCard)) {
                    Rules.invalidPlay();
                    break;
                }

                // Ask the player to play a card
                Card playerCard = gameUi.askPlayerForCard(player);

                // Removes the card from the player's hand and add it to discarded cards
                player.dequeueCardInHand(playerCard);
                discardedCards.add(playerCard);

                // outcome will determine what the result of the round is
                String outcome;

                // If the player managers to makes 15
                if (Rules.sumIs15(playerCard, computerCard)) {
                    currentScore++;
                    gameUi.displayMaking15();

                    // Deal a replacement card for the played card
                    if (!deck.isEmpty()) {
                        player.addCardToHand(deck.dealCard());
                        gameUi.displayHand(player);
                    } else {
                        gameUi.displayDeckEmpty();
                        outcome = "Made 15";
                        replayGame.logRound(
                                new ArrayList<>(player.getPlayerHand()),
                                computerCard,
                                List.of(playerCard),
                                outcome
                        );
                        break;
                    }

                    // Replace picture cards
                    List<Card> pictureCards = gameUi.askToReplacePictureCards(player);
                    for (Card pic : pictureCards) {
                        if (Rules.isPictureCard(pic)) {
                            player.dequeueCardInHand(pic);
                            discardedCards.add(pic);

                            if (!deck.isEmpty() && player.getPlayerHand().size() < Rules.maxCardsInHand) {
                                player.addCardToHand(deck.dealCard());
                                gameUi.displayHand(player);
                            } else {
                                gameUi.displayDeckEmpty();
                                outcome = "Made 15";
                                replayGame.logRound(
                                        new ArrayList<>(player.getPlayerHand()),
                                        computerCard,
                                        List.of(playerCard),
                                        outcome
                                );
                                break;
                            }
                        } else {
                            System.out.println("Not a picture card!");
                        }
                    }
                    outcome = "Made 15";

                    // Deal a new computer card
                    if (!deck.isEmpty()) {
                        computerCard = deck.dealCard();
                        gameUi.displayComputerCard(computerCard);
                    } else {
                        gameUi.displayDeckEmpty();
                        break;
                    }

                } else if (Rules.matchesSuit(playerCard, computerCard)) {
                    gameUi.displayCardSameSuit(playerCard);

                    // Replace the player's card with a new one
                    if (!dealReplacementCard()) {
                        break;
                    }

                    // Deal a new computer card
                    if (!deck.isEmpty()) {
                        computerCard = deck.dealCard();
                        gameUi.displayComputerCard(computerCard);
                    } else {
                        gameUi.displayDeckEmpty();
                        break;
                    }

                    outcome = "Same Suit";

                } else {
                    gameUi.displayInvalidMove();
                    outcome = "Invalid Move";
                    isGameOver = true;
                }

                // Log this stage/user interaction
                outcome = "Made 15";
                replayGame.logRound(
                        new ArrayList<>(player.getPlayerHand()),
                        computerCard,
                        List.of(playerCard),
                        outcome
                );
                    if ("Invalid Move".equals(outcome) || isGameOver) {
                      break;
                    }

                    } catch (Exception e) {
                      System.out.println("An error occurred, Game over!");
                      gameUi.displayFinalScore(currentScore);
                      isGameOver = true;
                    }
        }

        // Displays the final score and offer a replay
        gameUi.displayFinalScore(currentScore);
        System.out.println("Would you like to watch the replay? (Y/N)");
        String choice = scanner.nextLine().trim().toUpperCase();
        if (choice.equals("Y")) {
            replayGame.displayReplay(false); 
        }

        // asks the user for the their name and will update the leaderboard
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

    public boolean replacePictureCards() {
        // Ask the player which picture cards they want to replace
        List<Card> cardsToReplace = gameUi.askToReplacePictureCards(player);

        if (cardsToReplace.isEmpty()) {
            System.out.println("No picture cards selected for replacement. The game will continue.");
            return true;
        }

        // Replace all of the selected picture cards
        for (Card card : cardsToReplace) {
            // Remove the card from the player's hand
            player.dequeueCardInHand(card);
            discardedCards.add(card);

            // Replace the card with a new one from the deck if there is one available
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

        return true;
    }

    public void changeAndDisplayLeaderboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name for the leaderboard: ");
        String name = scanner.nextLine();

        leaderboard.addEntry(name, currentScore);
        leaderboard.saveScores();

        leaderboard.displayScores();
    }

    // For testing purposes
    public boolean isGameOver() {
        return isGameOver;
    }
}
