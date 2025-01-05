package entities;
import adt.Queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


// Methods that the Deck class must implement
interface DeckInterface{
    public void shuffle();
    public Card dealCard();
    public void resetDeck();
}


public class Deck implements DeckInterface{
    private final Queue<Card> allCards;

    public Deck(){
        allCards = new Queue<Card>();
        // Create the deck of cards - 52 cards - O(n^2);
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                allCards.enqueue(new Card(rank, suit));
            }
        }

        // Shuffle the deck
        this.shuffle();
        // For debugging purposes
        //System.out.println(allCards);
    }

    @Override
    public void shuffle() {
        // Create Array list rep for cards
        int lengthOfCardQueue = allCards.size();

        List<Card> listOfCards = new ArrayList<>();

        for (int i = 0; i < lengthOfCardQueue; i++) {
            if (!allCards.isEmpty()) {
                // removing all cards and adding to list
                listOfCards.add(allCards.dequeue());
            }
        }
            // Shuffling the deck
            Collections.shuffle(listOfCards);

            // Add them back in a randomized order
            for (Card card : listOfCards) {
                allCards.enqueue(card);
            }

    }

    @Override
    public Card dealCard() {
        return allCards.dequeue();
    }

    public void resetDeck(){
        while (!allCards.isEmpty()) {
            allCards.dequeue();
        }

        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                allCards.enqueue(new Card(rank, suit));
            }
        }

        this.shuffle();
        }

    // Getters
    public boolean isEmpty(){
        return allCards.isEmpty();
    }

    public Card topCard(){
        // peeking at the top card without dequeue
        return allCards.peek();
    }

    public int getSizeOfDeck(){
        return allCards.size();
    }

    // For debugging
    public void showTheDeck(){
        allCards.printQueue();
    }

    // For testing purposes
    public List<Card> getAllCardsAsList(){
        List<Card> listOfCards = new ArrayList<>();
        int lengthOfCardQueue = allCards.size();
        for (int i = 0; i < lengthOfCardQueue; i++) {
            if (!allCards.isEmpty()) {
                // removing all cards and adding to list
                listOfCards.add(allCards.dequeue());
            }
        }
        return listOfCards;
    }
}
