public interface PokerCommand {
    void execute();
    String getDescription();
    boolean isRaise();
}
