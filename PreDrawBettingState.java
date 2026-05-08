import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreDrawBettingState implements HandState{
    public void execute(PokerGame game) {
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
            if (player.isFolded() || contributed.get(player).equals(game.getCurrentBet())) continue;
            if (player.isHuman()) {
                if (player.getChipCount() < game.getCurrentBet()) {
                    System.out.println("You can't afford to call. You have been folded.");
                    new FoldCommand(player, game).execute();
                    continue;
                }
                // print choices and get input here
                System.out.println("Its your turn to bet.");
                System.out.println("Your hand:");
                List<Card> cards = player.getCurrentHand().getCards();
                for (int i = 0; i < cards.size(); i++) {
                    System.out.print(cards.get(i) + " ");
                }
                System.out.println("\nBet to call: " + game.getCurrentBet() + ".");
                System.out.println("Current pot: " + game.getPot() + ".");
                System.out.println("Your chips: " + player.getChipCount());
                System.out.println("1. Call (" + game.getCurrentBet() + " chips)");
                if (player.getChipCount() >= game.getCurrentBet() * 2) {
                    System.out.println("2. Raise (" + game.getCurrentBet() * 2 + " chips)");
                }
                System.out.println("3. Fold");
                System.out.println("What do you want to do?");

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
                }
            }
        }
    }

    public HandState nextState() {
        return new DrawState();
    }
}
