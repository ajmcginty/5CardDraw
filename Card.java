public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank r, Suit s) {
        this.rank = r;
        this.suit = s;
    }
    public Rank getRank() {
        return this.rank;
    }
    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public String toString() {
        String suitEmoji;
        if (suit == Suit.HEARTS) suitEmoji = "♥";
        else if (suit == Suit.DIAMONDS) suitEmoji = "♦";
        else if (suit == Suit.CLUBS) suitEmoji = "♣";
        else suitEmoji = "♠";

        String rankDisplay;
        if (rank == Rank.ACE) rankDisplay = "A";
        else if (rank == Rank.KING) rankDisplay = "K";
        else if (rank == Rank.QUEEN) rankDisplay = "Q";
        else if (rank == Rank.JACK) rankDisplay = "J";
        else if (rank == Rank.TEN) rankDisplay = "10";
        else if (rank == Rank.NINE) rankDisplay = "9";
        else if (rank == Rank.EIGHT) rankDisplay = "8";
        else if (rank == Rank.SEVEN) rankDisplay = "7";
        else if (rank == Rank.SIX) rankDisplay = "6";
        else if (rank == Rank.FIVE) rankDisplay = "5";
        else if (rank == Rank.FOUR) rankDisplay = "4";
        else if (rank == Rank.THREE) rankDisplay = "3";
        else rankDisplay = "2";

        return rankDisplay + suitEmoji;
    }
}
