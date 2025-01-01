package entities;

// This is the Rank enum. It will be used to represent the ranks of the cards in the deck.
public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    TEN(10), Jack(11), Queen(11), King(11), Ace(12);

    private final int value;

    Rank(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        return name();
    }



}
