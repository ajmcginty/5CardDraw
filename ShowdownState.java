import java.util.*;

public class ShowdownState implements HandState {
    public void execute(PokerGame game) {
        System.out.println("\n=== SHOWDOWN ===");
        int highestStrength = 0;
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            int strength = player.getCurrentHand().evaluateStrength();
            if (strength > highestStrength) highestStrength = strength;
            
            StringBuilder reveal = new StringBuilder();
            String displayName = player.isHuman() ? "You" : player.getName();
            reveal.append(displayName).append(" shows: ");
            for (Card card : player.getCurrentHand().getCards()) {
                reveal.append(card).append("  ");
            }
            
            reveal.append("— ").append(handName(strength));
            System.out.println(reveal);
            try { Thread.sleep(700); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println();
        List<Player> bestHands = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            if (player.getCurrentHand().evaluateStrength() == highestStrength) {
                bestHands.add(player);
            }
        }
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        if (bestHands.isEmpty()) {
            System.out.println("Everyone folded — the pot is lost.");
        } else {
            int share = game.getPot() / bestHands.size();
            for (Player player : bestHands) {
                player.addChips(share);
                String displayName = player.isHuman() ? "You" : player.getName();
                System.out.println(displayName + " wins " + share + " chips with " + handName(player.getCurrentHand().evaluateStrength()) + "!");
            }
        }
        game.setPot(0);
        for (Player player : game.getPlayers()) {
            player.resetForNewHand();
        }
        game.setCurrentBet(20);

        System.out.println();
        StringBuilder chipLine = new StringBuilder("Chips  —");
        for (Player player : game.getPlayers()) {
            chipLine.append("  ").append(player.isHuman() ? "You" : player.getName())
                    .append(": ").append(player.getChipCount());
        }
        System.out.println(chipLine);
    }
    public HandState nextState() {
        return new PreDrawBettingState();
    }
    private String handName(int strength) {
        if (strength == 9) return "straight flush";
        else if (strength == 8) return "four of a kind";
        else if (strength == 7) return "full house";
        else if (strength == 6) return "flush";
        else if (strength == 5) return "straight";
        else if (strength == 4) return "three of a kind";
        else if (strength == 3) return "two pair";
        else if (strength == 2) return "one pair";
        else return "high card";
    }
}
