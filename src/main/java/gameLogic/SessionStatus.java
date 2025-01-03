package gameLogic;

import entities.Deck;
import entities.Player;
import entities.Card;

import java.util.List;
import java.util.Map;

/* This class will represent the current state of the game
   It will include all necessary information such as players, hands, deck etc.
 */

public class SessionStatus {
    private final Player player;
    private final Deck deck;
    private final Card faceUpCard;
    private final List<Card> discardedCards;
    private final int currentScore;

    public SessionStatus(Player player, Deck deck, Card faceUpCard, List<Card> discardedCards, int currentScore){
        this.player = player;
        this.deck = deck;
        this.faceUpCard = faceUpCard;
        this.discardedCards = discardedCards;
        this.currentScore = currentScore;
    }

    // Getters
    public Player getPlayer(){
        return this.player;
    }

    public Deck getDeck(){
        return this.deck;
    }

    public Card getFaceUpCard(){
        return this.faceUpCard;
    }

    public List<Card> getDiscardedCards(){
        return this.discardedCards;
    }

        public int getCurrentScores(){
        return this.currentScore;
    }


}
