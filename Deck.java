import java.util.*;

public class Deck {
    private static Deck deck;
    
    private List<Card> cards = new ArrayList<>();
    
    private Deck() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    public static Deck makeDeck() {
        if (deck == null) {
            deck = new Deck();
        }
        return deck;
    }
    public static void deleteDeck() {
        deck = null;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        return cards.remove(0);
    }

    public void reset() {
        cards.clear();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
        shuffle();
    }

    public int size() {
        return cards.size();
    }
}