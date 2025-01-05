package entitiesTest;

import entities.Card;
import entities.Deck;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {


    // Ensure the deck is gets filled with 52 cards
    @Test
    void testCreateDeck() {

        Deck deck = new Deck();
        assertEquals(52, deck.getSizeOfDeck());
    }


    @Test
    void testShuffle() {

        Deck deck = new Deck();

        // Get order before shuffling
        List<Card> beginningOrder = deck.getAllCardsAsList();

        // Shuffle the deck
        deck.shuffle();

        // Get thj new order after shuffling
        List<Card> newOrder = deck.getAllCardsAsList();

        assertNotEquals(beginningOrder, newOrder);
    }


    @Test
    void testIsEmpty() {

        Deck deck = new Deck();

        // first check that a fresh deck isn't empty
        assertFalse(deck.isEmpty());

        // Wipe th deck to make it empty
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }

        // check that the deck is now empty
        assertTrue(deck.isEmpty());

    }

    @Test
    void testResetDeck() {

        Deck deck = new Deck();
        // first dequeue 4 cards

        deck.dealCard();
        deck.dealCard();
        deck.dealCard();
        deck.dealCard();

        assertNotEquals(52, deck.getSizeOfDeck());

        // reset the deck
        deck.resetDeck();

        // check that the size is not back to 52
        assertEquals(52, deck.getSizeOfDeck());
    }

    @Test
    void testDealCard(){

        Deck deck = new Deck();

        Card card = deck.dealCard();
        // Chcek that its not null
        assertNotNull(card);

        // Check that deck is now down by 1
        assertEquals(51, deck.getSizeOfDeck());
    }

    @Test
    void testTopCard() {

        Deck deck = new Deck();

        // Get top card from method
        Card topCard = deck.topCard();

        // Check that it's not null, not empty and it's consistent
        assertNotNull(topCard);
        assertEquals(topCard, deck.topCard());
        assertFalse(deck.isEmpty());
    }
}