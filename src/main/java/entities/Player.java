package entities;
import gameLogic.Rules;
import java.util.*;

public class Player {
    private List<Card> hand;
    private int score;
    private int highestScore;


    public Player(){
        this.hand = new ArrayList<>();
        this.score = 0;
        this.highestScore = 0;
    }

    // Getters and Setters

    public int getScore(){
        return this.score;
    }

    public int getHighestScore(){
        return this.highestScore;
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public void showHand(){
        if (hand.isEmpty()){
            System.out.println("Hand is empty");
        } else {
            // compares and sorts based on rank of the cards
            Collections.sort(hand, new Comparator<Card>() {
                @Override
                public int compare(Card card, Card t1) {
                    return card.getRank().compareTo(t1.getRank());
                }
            });
            for (int i = 0; i < hand.size(); i++){
                System.out.println("(" + (i + 1) + ") " + hand.get(i) + " ");
            }
        }
    }

    public List<Card> getPlayerHand(){
        return hand;
    }
  
    // Removes card from the deck
    public void dequeueCardInHand(Card card){
        hand.remove(card);
    }
  
    // Retriveing the card that will be replaced
    public List<Card> getCardsToExchange(String[] cardsToExchange){
        List<Card> cards = new ArrayList<>();
        for (String card : cardsToExchange){
            int index = Integer.parseInt(card);
            if (index > 0 && index <= hand.size()) {
                Card cardIndex = hand.get(index - 1);
                if (Rules.isPictureCard(cardIndex)){
                    cards.add(cardIndex);
                }
            }
        }
        return cards;
    }




}
