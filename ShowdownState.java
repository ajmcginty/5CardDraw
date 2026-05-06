import java.util.*;

public class ShowdownState implements HandState {
    public void execute(PokerGame game) {
        //Find the highest hand
        int highestStrength = 0;
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            if (player.getCurrentHand().evaluateStrength() > highestStrength) {
                highestStrength = player.getCurrentHand().evaluateStrength();
            }
            System.out.println(player.getName() + " has a " + handName(player.getCurrentHand().evaluateStrength()));
        }
        // Find which players have that highest hand
        List<Player> bestHands = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            if (player.getCurrentHand().evaluateStrength() == highestStrength) {
                bestHands.add(player);
            }
        }
        int share = game.getPot() / bestHands.size();
        for (Player player : bestHands) {
            player.addChips(share);
            System.out.println(player.getName() + " wins " + share + " chips with " + handName(player.getCurrentHand().evaluateStrength()) + "!");
        }
        game.setPot(0);
        for (Player player : game.getPlayers()) {
            player.resetForNewHand();
        }
        game.setCurrentBet(20); // reset to big blind
    }
    public HandState nextState() {
        return new PreDrawBettingState();
    }
    private String handName(int strength) {
        if (strength == 5) return "four of a kind";
        else if (strength == 4) return "three of a kind";
        else if (strength == 3) return "two pair";
        else if (strength == 2) return "one pair";
        else return "high card";
    }
}
