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

    // member variables
    private Deck deck;
    private Player player;
    private gameUI gameUi;
    private Leaderboard leaderboard;
    private List<Card> discardedCards;
    private Card computerCard;
    private int currentScore;
    private boolean isGameOver = false;

    public GameManager(Player player, gameUI gameUi) {
        this.deck = new Deck();
        this.player = player;
        this.gameUi = gameUi;
        this.leaderboard = new Leaderboard("src/main/java/resources/highscores.json");
        this.discardedCards = new ArrayList<>();
        this.currentScore = 0;
    }

    public void init() {
        gameUi.initialMessage(player);
        leaderboard.displayScores();
        dealFirstCards();
        gameUi.displayHand(player);
        computerCard = deck.dealCard();
        gameUi.displayComputerCard(computerCard);
    }

    private void dealFirstCards() {
        for (int i = 0; i < 5; i++) {
            player.addCardToHand(deck.dealCard());
        }
    }

// src/main/java/gameLogic/GameManager.java

    public void gameLoop() {
        while (!isGameOver) {
            try {
                if (!Rules.isValidMove(player.getPlayerHand(), computerCard)) {
                    isGameOver = true;
                    break;
                }
                Card playerCard = gameUi.askPlayerForCard(player);
                player.dequeueCardInHand(playerCard);
                discardedCards.add(playerCard);
                gameUi.displayHand(player);

                if (Rules.sumIs15(playerCard, computerCard)) {
                    currentScore++;
                    gameUi.displayMaking15();

                    if (!deck.isEmpty()) {
                        player.addCardToHand(deck.dealCard());
                    } else {
                        gameUI.displayDeckEmpty();
                        gameUI.displayFinalScore(currentScore);
                        isGameOver = true;
                        break;
                    }

                    List<Card> cardsToReplace = gameUi.askToReplacePictureCards(player);
                    for (Card card : cardsToReplace) {
                        player.dequeueCardInHand(card);
                        discardedCards.add(card);
                        if (!deck.isEmpty()) {
                            player.addCardToHand(deck.dealCard());
                        } else {
                            gameUI.displayDeckEmpty();
                            gameUI.displayFinalScore(currentScore);
                            isGameOver = true;
                            break;
                        }
                    }
                } else if (Rules.matchesSuit(playerCard, computerCard)) {
                    gameUi.displayCardSameSuit(playerCard);
                    computerCard = deck.dealCard();
                    gameUi.displayComputerCard(computerCard);
                } else {
                    Rules.invalidPlay();
                    isGameOver = true;
                    break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred, Game over!");
                gameUI.displayFinalScore(currentScore);
                isGameOver = true;
            }
        }
    }

    // change and display leaderboard
    public void changeAndDisplayLeaderboard(){
        Scanner scanner = new Scanner(System.in);
        gameUI.displayFinalScore(currentScore);
        System.out.println("Please Enter name for the leaderboard: ");
        String name = scanner.nextLine();

        leaderboard.addEntry(name, currentScore);
        try {
            leaderboard.saveScores();
        }catch(Exception e){
            System.out.println("Error saving scores: " + e);
        }

        // Display the leaderboard to the player
        leaderboard.displayScores();
    }

}
