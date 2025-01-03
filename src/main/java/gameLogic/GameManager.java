package gameLogic;

import entities.Card;
import entities.Deck;
import entities.Player;
import userInterface.gameUI;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    // member variables
    private Deck deck;
    private Player player;
    private List<Card> discardedCards;
    private Card computerCard;
    private int currentScore;
    private boolean isGameOver = false;

    public GameManager(Player player, gameUI gameUi) {
        this.deck = new Deck();
        this.player = player;
        this.discardedCards = new ArrayList<>();
        this.currentScore = currentScore;

    }

    public void initGame() {

    }

    private void dealFirstCards(){
        for (int i = 0; i < 5; i++){
            player.addCardToHand(deck.dealCard());
    }

    public void gameLoop() {

    }
}



