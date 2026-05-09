import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostDrawBettingState implements HandState{
    public void execute(PokerGame game) {
        System.out.println("\n=== POST-DRAW BETTING ===");
        boolean raiseOccurred = true;
        HashMap<Player, Integer> contributed = new HashMap<>(Map.of(
            game.getPlayers().get(0), 0, 
            game.getPlayers().get(1), 0,
            game.getPlayers().get(2), 0,
            game.getPlayers().get(3), 0
        ));

        while (raiseOccurred) {
            raiseOccurred = false;
            for (Player player : game.getPlayers()) {
            if (player.getCurrentHand() == null) continue;
            if (player.isFolded() || contributed.get(player).equals(game.getCurrentBet())) continue;
            if (player.isHuman()) {
                if (player.getChipCount() < game.getCurrentBet()) {
                    System.out.println("You can't afford to call. You have been folded.");
                    new FoldCommand(player, game).execute();
                    continue;
                }
                System.out.println("\nIt's your turn to bet.");
                System.out.println("Your hand:");
                List<Card> cards = player.getCurrentHand().getCards();
                StringBuilder handDisplay = new StringBuilder(" ");
                for (Card card : cards) handDisplay.append(card).append("  ");
                System.out.println(handDisplay);
                System.out.println();
                System.out.println("Pot: " + game.getPot() + "  |  Bet to call: " + game.getCurrentBet() + "  |  Your chips: " + player.getChipCount());
                System.out.println();
                System.out.println("1. Call (" + game.getCurrentBet() + " chips)");
                if (player.getChipCount() >= game.getCurrentBet() * 2) {
                    System.out.println("2. Raise (" + game.getCurrentBet() * 2 + " chips)");
                }
                System.out.println("3. Fold");
                System.out.print("> ");

                int choice = game.getScanner().nextInt();

                if (choice == 1) {
                    int toCall = game.getCurrentBet() - contributed.get(player);
                    new CallCommand(player, toCall, game).execute();
                    contributed.put(player, game.getCurrentBet());
                }
                else if (choice ==2) {
                    int newBet = game.getCurrentBet() * 2;
                    int toPay = newBet - contributed.get(player);
                    new RaiseCommand(player, toPay, game).execute();
                    contributed.put(player, newBet);
                    game.setCurrentBet(newBet);
                    raiseOccurred = true;
                }
                else {
                    new FoldCommand(player, game).execute();
                }
            } else {
                if (player.getChipCount() < game.getCurrentBet()) {
                    new FoldCommand(player, game).execute();
                    System.out.println(player.getName() + " folded.");
                    try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    continue;
                }
                int toCall = game.getCurrentBet() - contributed.get(player);
                PokerCommand command = player.getPlayerStrategy().decideAction(
                    player, 
                    player.getCurrentHand().evaluateStrength(), 
                    game.getPot(), 
                    toCall, 
                    game
                );
                command.execute();
                contributed.put(player, game.getCurrentBet());
                if (command.isRaise()) {
                    raiseOccurred = true;
                }
                System.out.println(command.getDescription());
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
        }
    }

    public HandState nextState() {
        return new ShowdownState();
    }
}
