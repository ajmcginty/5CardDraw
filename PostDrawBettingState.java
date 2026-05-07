import java.util.List;

public class PostDrawBettingState implements HandState {
    public void execute(PokerGame game) {
        for (Player player : game.getPlayers()) {
            if (player.isFolded()) continue;
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
                    new CallCommand(player, game.getCurrentBet(), game).execute();
                }
                else if (choice ==2) {
                    new RaiseCommand(player, game.getCurrentBet() * 2, game).execute();
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
                PokerCommand command = player.getPlayerStrategy().decideAction(
                    player, 
                    player.getCurrentHand().evaluateStrength(), 
                    game.getPot(), 
                    game.getCurrentBet(), 
                    game
                );
                command.execute();
                System.out.println(command.getDescription());
            }
        }
    }
    
    public HandState nextState() {
        return new ShowdownState();
    }
}

