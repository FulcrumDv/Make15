package recording;
import entities.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * This class will log each interaction between the player and the computer during the game.
 * It will allow the user to replay the game in either chronological order (i.e.) the order it actually happened.
 * Can also be played in reverse order.
 */

public class ReplayGame {

    // Uses a stack to store the rounds
    private final Stack<RoundRecord> rounds;

    public ReplayGame() {
        this.rounds = new Stack<>();
    }

    public void logRound(List<Card> playerHand, Card computerCard, List<Card> cardsPlayed, String outcome) {
        // Make copies so changes to the original lists don't affect the replay
        List<Card> copiedHand = new ArrayList<>(playerHand);
        List<Card> copiedPlays = new ArrayList<>(cardsPlayed);

        rounds.push(new RoundRecord(copiedHand, computerCard, copiedPlays, outcome));
    }


     // Displays the replay in either chronological or reverse order, depending on the flag.

    public void displayReplay(boolean reverse) {
        if (rounds.isEmpty()) {
            System.out.println("No rounds have been logged. Nothing to replay.");
            return;
        }

        System.out.println("\n------- START REPLAY -------");
        if (reverse) {
            // Last in, first out (true stack order)
            int roundNum = rounds.size();
            while (!rounds.empty()) {
                RoundRecord record = rounds.pop();
                printRound(record, roundNum--);
            }
        } else {
            List<RoundRecord> tempList = new ArrayList<>(rounds);
            for (int i = 0; i < tempList.size(); i++) {
                int roundNumber = i + 1;
                RoundRecord record = tempList.get(i);
                printRound(record, roundNumber);
            }
        }
        System.out.println("----- END -----\n");
    }

    private void printRound(RoundRecord record, int roundNumber) {
        System.out.println("\nRound " + roundNumber);
        System.out.println("Player's Hand at Start:");
        for (Card card : record.playerHand()) {
            System.out.println("  - " + card);
        }
        System.out.println("Computer's Card: " + record.computerCard());
        System.out.println("Player's Move(s):");
        for (Card card : record.cardsPlayed()) {
            System.out.println("  - " + card);
        }
        System.out.println("Outcome: " + record.outcome());
    }

    public boolean isEmpty() {
        return rounds.isEmpty();
    }

    public void clear() {
        rounds.clear();
    }

    // This Record represents a single round of the game (i.e each stage)
        public record RoundRecord(List<Card> playerHand, Card computerCard, List<Card> cardsPlayed, String outcome) {
    }
}

