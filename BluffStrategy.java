import java.util.*;

public class BluffStrategy implements PlayerStrategy {
    
    public PokerCommand decideAction(Player player, int handStrength, int potSize, int callAmount, PokerGame game) {
        if (Math.random() < 0.1) {
            return new FoldCommand(player, game);
        }
        else if (Math.random() > 0.3 && game.getRaisesThisRound() < 3 && player.getChipCount() >= callAmount * 2) {
            return new RaiseCommand(player, callAmount * 2, game);
        }
        else {
            return new CallCommand(player, callAmount, game);
        }
    }
    public List<Card> decideDiscards(Hand hand, int handStrength){
        
        List<Card> discards = new ArrayList<>();
        
        if (handStrength >= 3) {
            // Two pair or better, keep all cards to make players believe you have better hand
        }
        else if (handStrength == 2) {
            // One pair, only get rid of one to bluff better hand

            HashMap<Rank, Integer> freqMap = new HashMap<>();
            for (Card card : hand.getCards()) {   
                if (freqMap.containsKey(card.getRank())) {
                    freqMap.put(card.getRank(), freqMap.get(card.getRank()) + 1);
                } else {
                    freqMap.put(card.getRank(), 1);
                }
            }

            Card lowest = hand.getCards().get(0);
            for (Card card : hand.getCards()) {
                // If the card is lower than the lowest, and if its not part of the pair
                if (card.getRank().ordinal() < lowest.getRank().ordinal() && freqMap.get(card.getRank()) != 2) {
                    lowest = card;
                }
            }
            discards.add(lowest);
        }
        else {
            // The player had no pairs, player only keeps all cards greater than 10, and if nothing >10,
            // keep the highest three cards and get rid of the rest
            List<Card> keep = new ArrayList<>();
            for (Card card : hand.getCards()) {
                if (card.getRank().ordinal() > Rank.TEN.ordinal()) {
                    keep.add(card);
                }
            }
            if (keep.isEmpty()) {
                Card lowest = hand.getCards().get(0);
                Card secondLowest = hand.getCards().get(0);
                
                for (Card card : hand.getCards()) {
                    if (card.getRank().ordinal() < lowest.getRank().ordinal()) {
                        secondLowest = lowest;
                        lowest = card;
                    }
                    else if (card.getRank().ordinal() < secondLowest.getRank().ordinal()) {
                        secondLowest = card;
                    }
                }
                for (Card card : hand.getCards()) {
                    if (card == lowest || card == secondLowest) {
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
