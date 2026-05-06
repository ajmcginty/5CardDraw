public class FoldCommand implements PokerCommand {

    private Player player;
    private PokerGame game;

    public FoldCommand(Player p, PokerGame g) {
        player = p;
        game = g;
    }

    public void execute() {
        player.fold();
    }
    public String getDescription() {
        return player.getName() + " folded.";
    }
}
