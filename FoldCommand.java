public class FoldCommand implements PokerCommand {

    private Player player;

    public FoldCommand(Player p, PokerGame g) {
        player = p;
    }
    public boolean isRaise() {
        return false;
    }

    public void execute() {
        player.fold();
    }
    public String getDescription() {
        return player.getName() + " folded.";
    }
}
