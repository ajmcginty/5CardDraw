import java.util.*;

public class AggressiveStrategy implements PlayerStrategy {
    
    public PokerCommand decideAction(Player player, int handStrength, int potSize, int callAmount, PokerGame game) {
        if (handStrength < 2) {
            return new FoldCommand(player, game);
        }
        else if (Math.random() > 0.5 && game.getRaisesThisRound() < 3 && player.getChipCount() >= callAmount * 2) {
            return new RaiseCommand(player, callAmount * 2, game);
        } else {
            return new CallCommand(player, callAmount, game);
        }
    }
    public List<Card> decideDiscards(Hand hand, int handStrength){
        
        List<Card> discards = new ArrayList<>();
        HashMap<Rank, Integer> freqMap = new HashMap<>();
        for (Card card : hand.getCards()) {   
            if (freqMap.containsKey(card.getRank())) {
                freqMap.put(card.getRank(), freqMap.get(card.getRank()) + 1);
            } else {
                freqMap.put(card.getRank(), 1);
            }
        }
        
        if (handStrength >= 5) {
            // Straight or better, keep all cards
        }
        else if (handStrength == 4) {
            // Three of a kind, get rid of other two
            for (Card card : hand.getCards()) {
                if (freqMap.get(card.getRank()) != 3) {
                    discards.add(card);
                }
            }
        }
        else if (handStrength == 2 || handStrength == 3) {
            // One pair/two pair, get rid of other three/one
            for (Card card : hand.getCards()) {
                if (freqMap.get(card.getRank()) != 2) {
                    discards.add(card);
                }
            }
        }
        else {
            // The player had no pairs, player keeps all cards higher than 10, and if nothing >10,
            // keep the highest two cards and get rid of the rest
            List<Card> keep = new ArrayList<>();
            for (Card card : hand.getCards()) {
                if (card.getRank().ordinal() > Rank.TEN.ordinal()) {
                    keep.add(card);
                }
            }
            if (keep.isEmpty()) {
                Rank highest = hand.getCards().get(0).getRank();
                Rank secondHighest = hand.getCards().get(0).getRank();
                
                for (Card card : hand.getCards()) {
                    if (card.getRank().ordinal() > highest.ordinal()) {
                        secondHighest = highest;
                        highest = card.getRank();
                    }
                    else if (card.getRank().ordinal() > secondHighest.ordinal()) {
                        secondHighest = card.getRank();
                    }
                }
                for (Card card : hand.getCards()) {
                    if (card.getRank() != highest && card.getRank() != secondHighest) {
                        discards.add(card);
                    }
                }
            } else {
                for (Card card : hand.getCards()) {
                    if (!keep.contains(card)) {
                        discards.add(card);
                    }
                }
            }
        }
        return discards;
    }
}
