import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PokerGame {
    private List<Player> players;
    private Deck deck;
    private int pot;
    private int currentBet;
    private HandState currentState;
    private Scanner scanner = new Scanner(System.in);
    public Scanner getScanner() {
        return scanner;
    }
    private int raisesThisRound = 0;

    public PokerGame() {
        pot = 0;
        currentBet = 20;
        deck = Deck.makeDeck();
        players = new ArrayList<>();
        currentState = new PreDrawBettingState();
    }

    // Getters
    public List<Player> getPlayers() {
        return players;
    }
    public int getPot() {
        return pot;
    }
    public int getCurrentBet() {
        return currentBet;
    }
    public Deck getDeck() {
        return deck;
    }

    // Setters
    public void setPot(int p) {
        pot = p;
    }
    public void setCurrentBet(int b) {
        currentBet = b;
    }

    // Other methods
    public int getRaisesThisRound() {
    return raisesThisRound;
    }
    public void incrementRaises() {
        raisesThisRound++;
    }
    public void resetRaises() {
        raisesThisRound = 0;
    }
    
    public void playHand() {
        deck.reset();
        for (Player player : players) {
            if (player.getChipCount() < 20) continue;
            List<Card> dealtCards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                dealtCards.add(deck.deal());
            }
            player.setHand(new Hand(dealtCards));
        }
        currentState = new PreDrawBettingState();
        currentState.execute(this);
        currentState = currentState.nextState();
        currentState.execute(this);
        currentState = currentState.nextState();
        currentState.execute(this);
        currentState = currentState.nextState();
        currentState.execute(this);
    }
    public void play() {
        System.out.println("Welcome to Five Card Draw Poker!");
        System.out.println("Enter your name: ");
        String humanName = scanner.nextLine();

        List<PlayerStrategy> strategies = new ArrayList<>();
        strategies.add(new AggressiveStrategy());
        strategies.add(new PassiveStrategy());
        strategies.add(new BluffStrategy());
        Collections.shuffle(strategies);
        
        List<String> faculty = new ArrayList<>(List.of(
            "Prof. Alvarez",
            "Prof. Aviram",
            "Prof. Bento",
            "Prof. Bolotin",
            "Prof. Biswas",
            "Prof. Creiner",
            "Prof. Finocchiaro",
            "Prof. Griffith",
            "Prof. Hurley",
            "Prof. Khan",
            "Prof. Kim",
            "Prof. Levear",
            "Prof. Maier",
            "Prof. Marmolejo Cossio",
            "Prof. McTague",
            "Prof. Mohler",
            "Prof. Prud'hommeaux",
            "Prof. Samary",
            "Prof. Straubing",
            "Prof. Stump",
            "Prof. Su",
            "Prof. Volkovich",
            "Prof. Wei",
            "Prof. Wiseman",
            "Prof. Yuan",
            "Prof. Yun"
        ));
        Collections.shuffle(faculty);
        players.add(new Player(humanName, 500, null, true));
        players.add(new Player(faculty.get(0), 500, strategies.get(0), false));
        players.add(new Player(faculty.get(1), 500, strategies.get(1), false));
        players.add(new Player(faculty.get(2), 500, strategies.get(2), false));
    
        while (true) {
            playHand();
            // check if human is out
            if (players.get(0).getChipCount() < 20) {
                System.out.println("You're out of chips. Game over!");
                break;
            }
            // check if all AI are out
            else if (players.get(1).getChipCount() < 20 && players.get(2).getChipCount() < 20 && players.get(3).getChipCount() < 20) {
                System.out.println("You win! All opponents have been eliminated!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        PokerGame game = new PokerGame();
        game.play();
    }
}
