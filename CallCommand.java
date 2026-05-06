public class CallCommand implements PokerCommand {

    private Player player;
    private int amount;
    private PokerGame game;

    public CallCommand(Player p, int a, PokerGame g) {
        player = p;
        amount = a;
        game = g;
    }
    public void execute() {
        player.deductChips(amount);
        game.setPot(game.getPot() + amount);
    }
    public String getDescription() {
        return player.getName() + " called " + amount + ".";
    }
}