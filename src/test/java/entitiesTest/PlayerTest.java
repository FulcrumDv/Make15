package entitiesTest;

import entities.Card;
import entities.Player;
import entities.Rank;
import entities.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player = new Player();


    @Test
    void testAddCardToHand() {
        Card card = new Card(Rank.TWO, Suit.HEARTS);
        player.addCardToHand(card);

        assertEquals(1, player.getPlayerHand().size());
        assertEquals(card, player.getPlayerHand().get(0));
    }

    @Test
    void testShowHand() {
        // Add cards to the player's hand
        player.addCardToHand(new Card(Rank.ACE, Suit.SPADES));
        player.addCardToHand(new Card(Rank.THREE, Suit.CLUBS));
        player.addCardToHand(new Card(Rank.KING, Suit.HEARTS));

        // No assertions; only verify visually if output is correct
        player.showHand();
    }

    @Test
    void testGetPlayerHand() {
        Card card1 = new Card(Rank.FOUR, Suit.DIAMONDS);
        Card card2 = new Card(Rank.FIVE, Suit.SPADES);

        player.addCardToHand(card1);
        player.addCardToHand(card2);

        List<Card> hand = player.getPlayerHand();
        assertEquals(2, hand.size());
        assertTrue(hand.contains(card1));
        assertTrue(hand.contains(card2));
    }

    @Test
    void testDequeueCardInHand() {
        Card card1 = new Card(Rank.SIX, Suit.HEARTS);
        Card card2 = new Card(Rank.SEVEN, Suit.CLUBS);

        player.addCardToHand(card1);
        player.addCardToHand(card2);

        player.dequeueCardInHand(card1);

        assertEquals(1, player.getPlayerHand().size());
        assertFalse(player.getPlayerHand().contains(card1));
        assertTrue(player.getPlayerHand().contains(card2));
    }

    @Test
    void testGetCardsToExchange() {
        Card pictureCard1 = new Card(Rank.QUEEN, Suit.SPADES);
        Card pictureCard2 = new Card(Rank.KING, Suit.HEARTS);
        Card nonPictureCard = new Card(Rank.THREE, Suit.DIAMONDS);

        player.addCardToHand(pictureCard1);
        player.addCardToHand(pictureCard2);
        player.addCardToHand(nonPictureCard);

        String[] cardsToExchange = {"1", "3"};
        List<Card> cards = player.getCardsToExchange(cardsToExchange);

        // Check if the picture card is returned and the non-picture card is not
        assertEquals(1, cards.size());
        assertTrue(cards.contains(pictureCard1));
        assertFalse(cards.contains(nonPictureCard));
    }

    @Test
    void testGetScore() {
        assertEquals(0, player.getScore());
    }

    @Test
    void testGetHighestScore() {
        assertEquals(0, player.getHighestScore());
    }
}
