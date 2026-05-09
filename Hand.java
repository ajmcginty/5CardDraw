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

    private boolean isFlush() {
        Suit handSuit = playerHand.get(0).getSuit();
        for (Card card : playerHand) {
            if (card.getSuit() != handSuit) {
                return false;
            }
        }
        return true;
    }
    private boolean isStraight() {
        // Sort hand
        List<Integer> ordinals = new ArrayList<>();
        for (Card card : playerHand) {
            ordinals.add(card.getRank().ordinal());
        }
        Collections.sort(ordinals);

        // check if cards increment by one
        boolean straight = true;
        for (int i = 0; i < 4; i++) {
            if (ordinals.get(i + 1) != ordinals.get(i) + 1) {
                straight = false;
                break;
            }
        }
        if (straight) {
            return true;
        }
        // Special case where ace can be a 1 
        if (ordinals.equals(List.of(0, 1, 2, 3, 12))) {
            return true;
        }
        return false;
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

        if (isFlush() && isStraight()) {
            // Straight Flush
            return 9;
        }
        
        else if ((freqMap.containsValue(4))) {
            // 4 of a kind
            return 8;
        } 
        else if (freqMap.containsValue(3) && freqMap.containsValue(2)) {
            // Full House
            return 7;
        } 
        else if (isFlush()) {
            // Flush
            return 6;
        } 
        else if (isStraight()) {
            // Straight
            return 5;
        } 
        else if (freqMap.containsValue(3)) {
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
