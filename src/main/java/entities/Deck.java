package entities;
import adt.Queue;
import entities.Card;
import entities.Suit;
import entities.Rank;


// Methods that the Deck class must implement
interface DeckInteface{
    public void shuffle();
    public Card dealCards();
    public void resetDeck();
}


public class Deck implements DeckInteface{
    private final Queue<Card> allCards;

    public Deck(){
        allCards = new Queue<Card>();
        // Create the deck of cards - 52 cards - O(n^2);
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                allCards.enqueue(new Card(rank, suit));
            }
        }

        // For debugging purposes
        //System.out.println(allCards);
    }

    @Override
    public void shuffle() {



    }

    @Override
    public Card dealCards() {
        return null;
    }

    @Override
    public void resetDeck() {

    }
}
