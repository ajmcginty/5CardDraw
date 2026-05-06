public class Player {
    private String name;
    private int chipCount;
    private Hand currentHand;
    private PlayerStrategy strategy;
    private boolean isHuman;
    private boolean isFolded = false;

    public Player (String n, int c, PlayerStrategy s, boolean h) {
        name = n;
        chipCount = c;
        strategy = s;
        isHuman = h;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getChipCount() {
        return chipCount;
    }
    public Hand getCurrentHand() {
        return currentHand;
    }
    public PlayerStrategy getPlayerStrategy() {
        return strategy;
    }
    public boolean isHuman() {
        return isHuman;
    }
    public boolean isFolded() {
        return isFolded;
    }

    // Other methods
    public void addChips(int amount) {
        chipCount += amount;
    }
    public void setHand(Hand h) {
        this.currentHand = h;
    }
    public void fold() {
        isFolded = true;
    }
    public void deductChips(int amount) {
        chipCount -= amount;
    }
    public void resetForNewHand() {
        isFolded = false;
        currentHand = null;
    }
}
