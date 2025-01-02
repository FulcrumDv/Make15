package entities;

import java.util.ArrayList;
import java.util.List;

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

    // Getters
    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public int getHighestScore(){
        return this.highestScore;
    }

    public void showHand(){
        System.out.println(hand);
    }


}
