import java.util.List;

public class DrawState implements HandState{
    public void execute(PokerGame game) {
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
            if (player.isHuman()) {
                // print choices and get input here
                System.out.println("Its your turn to draw.");
                System.out.println("Your hand:");
                List<Card> cards = player.getCurrentHand().getCards();
                for (int i = 0; i < cards.size(); i++) {
                    System.out.println((i + 1) + ". " + cards.get(i));
                }
                System.out.println("Select cards to replace from your hand. If none, press 0");
                System.out.println("(Separate your input with spaces");
                game.getScanner().nextLine(); // clear buffer
                String input = game.getScanner().nextLine();
                String[] parts = input.split(" ");
                if (parts[0].equals("0")) {
                    continue;
                }
                for (String part : parts) {
                    int index = Integer.parseInt(part) - 1; // convert to 0 index
                    Card toDiscard = cards.get(index);
                    player.getCurrentHand().removeCard(toDiscard);
                    player.getCurrentHand().addCard(game.getDeck().deal());
                }
            } else {
                List<Card> discards = player.getPlayerStrategy().decideDiscards(player.getCurrentHand(), player.getCurrentHand().evaluateStrength());
                for (Card card : discards) {
                    player.getCurrentHand().removeCard(card);
                    player.getCurrentHand().addCard(game.getDeck().deal());
                }
            }
        }
    }
    
    public HandState nextState() {
        return new PostDrawBettingState();
    }
}

