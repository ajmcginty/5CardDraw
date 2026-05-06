import java.util.*;

public class Hand {
    private List<Card> playerHand = new ArrayList<>();

    public Hand(List<Card> h) {
        this.playerHand = h;
    }
    public List<Card> getCards() {
        return playerHand;
    }
    public void removeCard(Card c) {
        playerHand.remove(c);
    }
    public void addCard(Card c) {
        playerHand.add(c);
    }
    public int evaluateStrength() {
        HashMap<Rank, Integer> freqMap = new HashMap<>();
        for (Card card : playerHand) {
            if (freqMap.containsKey(card.getRank())) {
                freqMap.put(card.getRank(), freqMap.get(card.getRank()) + 1);
            } else {
                freqMap.put(card.getRank(), 1);
            }
        }

        // Checking number of pairs
        int pairs = 0;
            for (int i : freqMap.values()) {
                if (i == 2) {
                    pairs++;
                }
            }
        
        if ((freqMap.containsValue(4))) {
            // 4 of a kind
            return 5;
        } else if (freqMap.containsValue(3)) {
            // 3 of a kind
            return 4;
        } else if (freqMap.containsValue(2)) {
            // Check if 1 pair or 2 pair
            if (pairs == 2) {
                // 2 pair
                return 3;
            } else {
                // 1 pair
                return 2;
            }
        } else {
            // High card
            return 1;
        }
    }
}
