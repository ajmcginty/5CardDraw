public class RaiseCommand implements PokerCommand {
    private Player player;
    private int amount;
    private PokerGame game;

    public RaiseCommand(Player p, int a, PokerGame g) {
        player = p;
        amount = a;
        game = g;
    }
    public void execute() {
        player.deductChips(amount);
        game.setPot(game.getPot() + amount);
        game.setCurrentBet(amount);
        game.incrementRaises();
    }
    public String getDescription() {
        return player.getName() + " raised to " + amount + ".";
    }
}
