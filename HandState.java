
public interface HandState {
    void execute(PokerGame game);
    HandState nextState();
}

