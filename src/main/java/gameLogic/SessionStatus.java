package gameLogic;

import entities.Deck;
import entities.Player;
import entities.Card;

import java.util.ArrayList;
import java.util.List;

/* This class will represent the current state of the game
   It will include all necessary information such as players, hands, deck etc.
 */

public class SessionStatus {
    private List<Player> players;
    private List<Deck> deck;
    private List<Card> usedCards;

    public SessionState(List<Player> players, List<Deck> deck, List<Card> usedCards){
        this.players = players;
        this.deck = deck;
        this.usedCards = usedCards;
    }

    // Getters
    public List<Player> getPlayers(){
        return this.players;
    }

    public List<Deck> getDeck(){
        return this.deck;
    }

    public List<Card> getUsedCards(){
        return this.usedCards;
    }


}
