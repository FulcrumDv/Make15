package gameLogic;


import entities.Card;
import entities.Rank;

import java.util.List;

public class Rules {
    static final int maxCardsInHand = 5;
    static final int maxCardsInDeck = 52;

    // checks if the player has a valid move to play, if not the game will end
    public static boolean isValidMove(List<Card> playerHand, Card computerCard){
        // Iterate through all elements in List and check if any make 15 with computer card or has same suit
        for (int i = 0; i < maxCardsInHand; i++) {
            Card card = playerHand.get(i);
            if (card.getRank().getValue() + computerCard.getRank().getValue() == 15
                    || matchesSuit(card, computerCard)){
            return true;
        }
    }
        System.out.println("You have no valid moves left. Game over.");
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

    // will check if the player has a card that matches the suit of the computer's card
    public static boolean hasTheSameSuit(List<Card> playerHand, Card computerCard){
        for (int i = 0; i < maxCardsInHand; i++){
            if (playerHand.get(i).getSuit().equals(computerCard.getSuit())){
                return true;
            }
        }
        return false;
    }

    // Displays that there was an invalidPlay and game will end
    public static void invalidPlay(){
        System.out.println("Invalid play. Game over.");
    }


    // checks if a picture card play is allowed

    public static boolean isPictureCard(Card playerCard){
        return playerCard.getRank() == Rank.JACK || playerCard.getRank() == Rank.QUEEN
                || playerCard.getRank() == Rank.KING;

    }




}
