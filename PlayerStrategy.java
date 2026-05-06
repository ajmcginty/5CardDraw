import java.util.List;

public interface PlayerStrategy {
    public PokerCommand decideAction(Player player, int handStrength, int potSize, int callAmount, PokerGame game);
    public List<Card> decideDiscards(Hand hand, int handStrength);
}