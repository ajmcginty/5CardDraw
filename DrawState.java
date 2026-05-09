import java.util.ArrayList;
import java.util.List;

public class DrawState implements HandState{
    public void execute(PokerGame game) {
        System.out.println("\n=== DRAW PHASE ===");
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            if (player.isHuman()) {
                System.out.println("\nIt's your turn to draw.");
                System.out.println("Your hand:");
                List<Card> cards = player.getCurrentHand().getCards();
                for (int i = 0; i < cards.size(); i++) {
                    System.out.println((i + 1) + ". " + cards.get(i));
                }
                System.out.println("Select cards to replace (e.g. 1 3 5), or 0 to keep all:");
                System.out.print("> ");

                game.getScanner().nextLine(); // clear buffer
                String input = game.getScanner().nextLine();
                String[] parts = input.split(" ");
                if (parts[0].equals("0")) {
                    continue;
                }
                List<Card> toDiscard = new ArrayList<>();
                for (String part : parts) {
                    int index = Integer.parseInt(part) - 1; // convert to 0 index
                    toDiscard.add(cards.get(index));
                }
                for (Card card : toDiscard) {
                    player.getCurrentHand().removeCard(card);
                    player.getCurrentHand().addCard(game.getDeck().deal());
                }
            } else {
                List<Card> discards = player.getPlayerStrategy().decideDiscards(player.getCurrentHand(), player.getCurrentHand().evaluateStrength());
                int count = discards.size();
                for (Card card : discards) {
                    player.getCurrentHand().removeCard(card);
                    player.getCurrentHand().addCard(game.getDeck().deal());
                }
                if (count == 0) {
                    System.out.println(player.getName() + " kept all cards.");
                } else {
                    System.out.println(player.getName() + " discarded " + count + " card" + (count == 1 ? "" : "s") + ".");
                }
                try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }
    
    public HandState nextState() {
        return new PostDrawBettingState();
    }
}

