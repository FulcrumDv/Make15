package recording;
import entities.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Manages the logging and replay of rounds for the Make 15 game using a Stack.
 */
public class ReplayGame {

    private final Stack<RoundRecord> rounds;

    public ReplayGame() {
        this.rounds = new Stack<>();
    }

    /**
     * Push a single round's details onto the stack so they can be replayed later.
     *
     * @param playerHand    The player's hand at the start of the round.
     * @param computerCard  The computer's card for this round.
     * @param cardsPlayed   The card(s) the player played.
     * @param outcome       A description of what happened (e.g., "Made 15", "Same Suit", etc.).
     */
    public void logRound(List<Card> playerHand, Card computerCard, List<Card> cardsPlayed, String outcome) {
        // Make copies so changes later won't affect old data
        List<Card> copiedHand = new ArrayList<>(playerHand);
        List<Card> copiedPlays = new ArrayList<>(cardsPlayed);

        rounds.push(new RoundRecord(copiedHand, computerCard, copiedPlays, outcome));
    }

    /**
     * Displays the replay in either chronological or reverse order, depending on the 'reverse' flag.
     *
     * @param reverse true for last-to-first, false for first-to-last
     */
    public void displayReplay(boolean reverse) {
        if (rounds.isEmpty()) {
            System.out.println("No rounds have been logged. Nothing to replay.");
            return;
        }

        System.out.println("\n----- MAKE 15 REPLAY -----");
        if (reverse) {
            // Last in, first out (true stack order)
            int roundNum = rounds.size();
            while (!rounds.empty()) {
                RoundRecord record = rounds.pop();
                printRound(record, roundNum--);
            }
        } else {
            // Chronological order (need to invert the stack or read from bottom to top)
            List<RoundRecord> tempList = new ArrayList<>(rounds);
            // Now tempList[0] is the bottom (first round), tempList[tempList.size()-1] is top
            for (int i = 0; i < tempList.size(); i++) {
                int roundNumber = i + 1;
                RoundRecord record = tempList.get(i);
                printRound(record, roundNumber);
            }

            // If you want to keep the stack for future replays, you might want to
            // re-push them back onto 'rounds' in the correct order, or design your code
            // so that you only call displayReplay once at the end.
        }
        System.out.println("----- END OF REPLAY -----\n");
    }

    private void printRound(RoundRecord record, int roundNumber) {
        System.out.println("\nRound " + roundNumber);
        System.out.println("Player's Hand at Start:");
        for (Card card : record.getPlayerHand()) {
            System.out.println("  - " + card);
        }
        System.out.println("Computer's Card: " + record.getComputerCard());
        System.out.println("Player's Move(s):");
        for (Card card : record.getCardsPlayed()) {
            System.out.println("  - " + card);
        }
        System.out.println("Outcome: " + record.getOutcome());
    }

    public boolean isEmpty() {
        return rounds.isEmpty();
    }

    public void clear() {
        rounds.clear();
    }

    public static class RoundRecord {
        private final List<Card> playerHand;
        private final Card computerCard;
        private final List<Card> cardsPlayed;
        private final String outcome;

        public RoundRecord(List<Card> playerHand, Card computerCard, List<Card> cardsPlayed, String outcome) {
            this.playerHand = playerHand;
            this.computerCard = computerCard;
            this.cardsPlayed = cardsPlayed;
            this.outcome = outcome;
        }

        public List<Card> getPlayerHand() {
            return playerHand;
        }

        public Card getComputerCard() {
            return computerCard;
        }

        public List<Card> getCardsPlayed() {
            return cardsPlayed;
        }

        public String getOutcome() {
            return outcome;
        }
    }
}

