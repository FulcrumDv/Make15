package entities;

// This is the Suit enum. It will be used to represent the suits of the cards in the deck.
    public enum Suit {
    CLUBS,
    HEARTS,
    DIAMONDS,
    SPADES;

    public String toString() {
        // This will return the name of the suit
        return name();
    }

}