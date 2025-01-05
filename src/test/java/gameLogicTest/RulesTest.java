package gameLogicTest;

import entities.Card;
import entities.Rank;
import entities.Suit;
import gameLogic.Rules;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulesTest {

    @Test
    void testIsValidMove_Valid15() {
        Card computerCard = new Card(Rank.FIVE, Suit.HEARTS);
        List<Card> playerHand = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.JACK, Suit.HEARTS),
                new Card(Rank.KING, Suit.HEARTS)
        );

        assertTrue(Rules.isValidMove(playerHand, computerCard), "Player has a valid move to make 15.");
    }

    @Test
    void testIsValidMove_ValidSuitMatch() {
        Card computerCard = new Card(Rank.TWO, Suit.CLUBS);
        List<Card> playerHand = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.KING, Suit.HEARTS)
        );

        assertTrue(Rules.isValidMove(playerHand, computerCard), "Player has a valid move by matching the suit.");
    }

    @Test
    void testIsValidMove_NoValidMoves() {
        Card computerCard = new Card(Rank.TWO, Suit.CLUBS);
        List<Card> playerHand = List.of(
                new Card(Rank.FOUR, Suit.HEARTS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.HEARTS),
                new Card(Rank.KING, Suit.HEARTS)
        );

        assertFalse(Rules.isValidMove(playerHand, computerCard), "Player has no valid moves.");
    }

    @Test
    void testSumIs15_True() {
        Card computerCard = new Card(Rank.EIGHT, Suit.HEARTS);
        Card playerCard = new Card(Rank.SEVEN, Suit.SPADES);

        assertTrue(Rules.sumIs15(computerCard, playerCard), "Sum of card values should be 15.");
    }

    @Test
    void testSumIs15_False() {
        Card computerCard = new Card(Rank.NINE, Suit.HEARTS);
        Card playerCard = new Card(Rank.FIVE, Suit.SPADES);

        assertFalse(Rules.sumIs15(computerCard, playerCard), "Sum of card values should not be 15.");
    }

    @Test
    void testMatchesSuit_True() {
        Card computerCard = new Card(Rank.EIGHT, Suit.HEARTS);
        Card playerCard = new Card(Rank.SEVEN, Suit.HEARTS);

        assertTrue(Rules.matchesSuit(computerCard, playerCard), "Cards should have the same suit.");
    }

    @Test
    void testMatchesSuit_False() {
        Card computerCard = new Card(Rank.EIGHT, Suit.HEARTS);
        Card playerCard = new Card(Rank.SEVEN, Suit.CLUBS);

        assertFalse(Rules.matchesSuit(computerCard, playerCard), "Cards should not have the same suit.");
    }

    @Test
    void testHasTheSameSuit_True() {
        Card computerCard = new Card(Rank.FIVE, Suit.DIAMONDS);
        List<Card> playerHand = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.THREE, Suit.DIAMONDS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.HEARTS)
        );

        assertTrue(Rules.hasTheSameSuit(playerHand, computerCard), "Player has a card with the same suit as the computer card.");
    }

    @Test
    void testHasTheSameSuit_False() {
        Card computerCard = new Card(Rank.FIVE, Suit.DIAMONDS);
        List<Card> playerHand = List.of(
                new Card(Rank.TEN, Suit.CLUBS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.QUEEN, Suit.CLUBS),
                new Card(Rank.KING, Suit.CLUBS)
        );

        assertFalse(Rules.hasTheSameSuit(playerHand, computerCard), "Player does not have a card with the same suit as the computer card.");
    }

    @Test
    void testIsPictureCard_True() {
        Card playerCard = new Card(Rank.KING, Suit.CLUBS);
        assertTrue(Rules.isPictureCard(playerCard), "The card is a picture card.");
    }

    @Test
    void testIsPictureCard_False() {
        Card playerCard = new Card(Rank.SIX, Suit.CLUBS);
        assertFalse(Rules.isPictureCard(playerCard), "The card is not a picture card.");
    }
}
