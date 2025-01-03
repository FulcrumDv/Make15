package gameLogic;


import entities.Card;

import java.util.List;

public class Rules {
    static final int maxCardsInHand = 5;
    static final int maxCardsInDeck = 52;

    // checks if the player has a valid move to play, if not the game will end
    public boolean isValidMove(List<Card> playerHand, Card computerCard){
        // Iterate through all elements in List and check if any make 15 with computer card or has same suit
        for (int i = 0; i < maxCardsInHand; i++) {
            Card card = playerHand.get(i);
            if (card.getRank().getValue() + computerCard.getRank().getValue() == 15
                    || matchesSuit(card, computerCard)){
            return true;
        }
    }
        return false;
    }


    // Checks if player achieves make 15
    public static boolean sumIs15(Card computerCard, Card playerCard){
        return computerCard.getRank().getValue() + playerCard.getRank().getValue() == 15;
    }

    // checks if a same suit play is allowed
    public static boolean matchesSuit(Card computerCard, Card playerCard){
        return computerCard.getSuit().equals(playerCard.getSuit());
    }

    // checks if a picture card play is allowed

    public static boolean isPictureCard(Card playerCard){
        return playerCard.getRank().getValue() == 11;
    }




}
