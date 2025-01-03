package entities;

import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;
    private int score;
    private int highestScore;


    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.highestScore = 0;
    }

    // Getters and Setters
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

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
                System.out.print("(" + (i + 1) + ") " + hand.get(i) + " ");
            }
        }
    }



}
