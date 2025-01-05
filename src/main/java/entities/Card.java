package entities;

/* Each card has a rank and a suit. This class represnts hte model of a card in a deck of cards. */

public class Card{
  // instance variables for rank and suit
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank(){
        return rank;
    }

    public Suit getSuit(){
        return suit;
    }

    public int getScoreValue(){
        return rank.getValue();
    }

    @Override
    public String toString(){
      return rank + " of " + suit;
    }











}
