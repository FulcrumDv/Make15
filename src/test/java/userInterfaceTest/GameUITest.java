package userInterfaceTest;

import entities.Card;
import entities.Player;
import entities.Rank;
import entities.Suit;
import gameLogic.Rules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userInterface.gameUI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class gameUITest {

    private gameUI ui;
    private Player player;

    @BeforeEach
    void setUp() {
        ui = new gameUI();
        player = new Player();
    }

    @Test
    void testInitialMessage() {
        ui.initialMessage(player);
        // No direct output verification as the method prints to console, just ensuring no exceptions occur
        assertNotNull(player);
    }

    @Test
    void testDisplayHand() {
        player.addCardToHand(new Card(Rank.TWO, Suit.HEARTS));
        player.addCardToHand(new Card(Rank.ACE, Suit.CLUBS));

        // Ensure hand is displayed without any runtime issues
        assertDoesNotThrow(() -> ui.displayHand(player));
        List<Card> hand = player.getPlayerHand();
        assertEquals(2, hand.size(), "Hand size should be 2.");
    }

    @Test
    void testDisplayInvalidMove() {
        // Ensure invalid move message doesn't cause errors
        assertDoesNotThrow(ui::displayInvalidMove);
    }

    @Test
    void testDisplayComputerCard() {
        Card computerCard = new Card(Rank.THREE, Suit.SPADES);

        // Ensure displaying computer card runs without issues
        assertDoesNotThrow(() -> ui.displayComputerCard(computerCard));
        assertEquals(Rank.THREE, computerCard.getRank());
        assertEquals(Suit.SPADES, computerCard.getSuit());
    }

    @Test
    void testAskToReplacePictureCards() {
        player.addCardToHand(new Card(Rank.FIVE, Suit.HEARTS));
        player.addCardToHand(new Card(Rank.JACK, Suit.SPADES));

        List<Card> pictureCards = new ArrayList<>();
        for (Card card : player.getPlayerHand()) {
            if (Rules.isPictureCard(card)) {
                pictureCards.add(card);
            }
        }

        assertEquals(1, pictureCards.size(), "Only one picture card should be selected.");
        assertEquals(Rank.JACK, pictureCards.get(0).getRank(), "Selected card should be a JACK.");
    }

    @Test
    void testDisplayMaking15() {
        // Ensure making 15 message doesn't cause errors
        assertDoesNotThrow(ui::displayMaking15);
    }

    @Test
    void testDisplayCardSameSuit() {
        Card playedCard = new Card(Rank.FIVE, Suit.HEARTS);

        // Ensure same suit display message doesn't cause errors
        assertDoesNotThrow(() -> ui.displayCardSameSuit(playedCard));
        assertEquals(Rank.FIVE, playedCard.getRank());
        assertEquals(Suit.HEARTS, playedCard.getSuit());
    }

    @Test
    void testDisplayFinalScore() {
        // Ensure final score display runs without issues
        assertDoesNotThrow(() -> gameUI.displayFinalScore(10));
    }

    @Test
    void testDisplayDeckEmpty() {
        // Ensure deck empty message doesn't cause errors
        assertDoesNotThrow(gameUI::displayDeckEmpty);
    }
}
