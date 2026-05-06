import java.util.List;

public class PokerGame {
    private List<Player> players;
    private Deck deck;
    private int pot;
    private int currentBet;
    private HandState currentState;

    public List<Player> getPlayers() {
        return players;
    }
    public int getPot() {
        return pot;
    }
    public int getCurrentBet() {
        return getCurrentBet();
    }


    public void setPot(int p) {
        pot = p;
    }
    public void setCurrentBet(int b) {
        currentBet = b;
    }

    public static void main(String[] args) {
        Deck deck = Deck.makeDeck();
        deck.shuffle();
        
    }
}
