package recordingTest;

import entities.Card;
import entities.Rank;
import entities.Suit;
import org.junit.jupiter.api.*;
import recording.ReplayGame;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReplayGameTest {

    private ReplayGame replayGame;

    @BeforeEach
    void setUp() {
        replayGame = new ReplayGame();
    }

    @Test
    void testLogRound() {
        List<Card> playerHand = List.of(
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.TWO, Suit.CLUBS)
        );
        Card computerCard = new Card(Rank.THREE, Suit.DIAMONDS);
        List<Card> cardsPlayed = List.of(new Card(Rank.TWO, Suit.CLUBS));
        String outcome = "Made 15";

        replayGame.logRound(playerHand, computerCard, cardsPlayed, outcome);

        assertFalse(replayGame.isEmpty(), "Replay stack should not be empty after logging a round.");
    }

    @Test
    void testDisplayReplayChronologicalOrder() {
        logSampleRounds();

        // Check replay (chronological)
        System.out.println("Chronological Replay:");
        replayGame.displayReplay(false);

        // After displayReplay, rounds should still exist
        assertFalse(replayGame.isEmpty(), "Rounds should remain after displaying replay in chronological order.");
    }

    @Test
    void testDisplayReplayReverseOrder() {
        logSampleRounds();

        // Check replay (reverse)
        System.out.println("Reverse Replay:");
        replayGame.displayReplay(true);

        // After displayReplay, rounds should be empty
        assertTrue(replayGame.isEmpty(), "Rounds should be empty after displaying replay in reverse order.");
    }

    @Test
    void testIsEmpty() {
        assertTrue(replayGame.isEmpty(), "Replay stack should be empty initially.");
        replayGame.logRound(new ArrayList<>(), new Card(Rank.ACE, Suit.HEARTS), new ArrayList<>(), "Sample Outcome");
        assertFalse(replayGame.isEmpty(), "Replay stack should not be empty after logging a round.");
    }

    @Test
    void testClear() {
        replayGame.logRound(new ArrayList<>(), new Card(Rank.ACE, Suit.HEARTS), new ArrayList<>(), "Sample Outcome");
        replayGame.clear();
        assertTrue(replayGame.isEmpty(), "Replay stack should be empty after calling clear.");
    }

    private void logSampleRounds() {
        List<Card> hand1 = List.of(
                new Card(Rank.FIVE, Suit.HEARTS),
                new Card(Rank.TEN, Suit.SPADES)
        );
        Card computerCard1 = new Card(Rank.FIVE, Suit.SPADES);
        List<Card> played1 = List.of(new Card(Rank.TEN, Suit.SPADES));
        replayGame.logRound(hand1, computerCard1, played1, "Made 15");

        List<Card> hand2 = List.of(
                new Card(Rank.TWO, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.CLUBS)
        );
        Card computerCard2 = new Card(Rank.FOUR, Suit.HEARTS);
        List<Card> played2 = List.of(new Card(Rank.THREE, Suit.CLUBS));
        replayGame.logRound(hand2, computerCard2, played2, "Same Suit");
    }
}
