// Card Game by Alexis Mendoza

import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;

    String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    int[] values = {1, 10, 10, 10, 10, 9, 50, 7, 6, 5, 4, 3, 2};
    private Deck deck;
    private Card prevCard;
    private Player winner;
    private boolean newSuitChosen;

    // States for the game
    private int state;
    public static final int STATE_INSTR = 0;
    public static final int STATE_PLAYER1 = 1;
    public static final int STATE_PLAYER2 = 2;
    public static final int STATE_END = 3;

    private GameView window;

    private int turnCounter;


    public Game(){
        deck = new Deck(ranks, suits, values);
        players = new ArrayList<>();

        this.window = new GameView(this);
        state = STATE_INSTR;

        turnCounter = 1;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public int getState() {
        return state;
    }

    public Card getPrevCard(){
        return prevCard;
    }

    public Player getWinner(){
        return winner;
    }

    public boolean getNewSuitChosen(){
        return newSuitChosen;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public String printInstructions(){
        return  "Hello and Welcome to Crazy 8s!\n " +
                "You must match the suit or rank of the previous card played. \n " +
                "If you don't have a card that works, you must draw, and your turn gets skipped.\n " +
                "You can place down an 8 \n " +
                "at any time. Placing down an 8 also lets you pick the next suit. \n " +
                "When typing your suit, please  make \n " +
                "sure to spell suits correctly. \n " +
                "To place a card, type 1 to place the first, 2 to place the second, \n " +
                "and so on. Good Luck!";
    }
    // Check if the card can be played
    public boolean canPlay(Card chosen, Card prev){
    return  chosen.getRank().equals(prev.getRank())
            || chosen.getSuit().equals(prev.getSuit()) || chosen.getRank().equals("8");
    }

    //Deal hands to each player
    public void dealStartingHands(){
        for (Player p : players){
            for (int i = 0; i < 7; i++){
                p.addCard(deck.deal());
            }
        }
    }

    // Draw a valid starting card, that is not an 8
    public Card drawStartingCard(){
        Card c = deck.deal();
        // Prevent 8 from being starting card
        while (c.getRank().equals("8")){
            c = deck.deal();
        }
        return c;
    }

    public void setUpPlayers(Scanner scanner){
        System.out.println("Player 1 name: ");
        String name = scanner.nextLine();
        Player player1 = new Player(name);
        players.add(player1);

        System.out.println("Player 2 name: ");
        name = scanner.nextLine();
        Player player2 = new Player(name);
        players.add(player2);
    }

    public void playGame(){
        System.out.println(printInstructions());
        window.repaint();

        Scanner scanner = new Scanner(System.in);

        setUpPlayers(scanner);

        dealStartingHands();

        prevCard = drawStartingCard();

        System.out.println("Starting Card: " + prevCard);
        Card chosen = null;

        while (true){
            int pick;
            // Turn for each player
            for (Player p : players) {
                newSuitChosen = false;
                // Switch state depending on player
                if (players.indexOf(p) == 0) {
                    state = STATE_PLAYER1;
                    // Repaint for states
                    window.repaint();
                } else {
                    state = STATE_PLAYER2;
                    // Repaint for states
                    window.repaint();
                }

                // Player places card
                System.out.println(p.getName() + ": Pick a card");
                System.out.println(p.getHand());

                pick = scanner.nextInt() - 1;
                scanner.nextLine();

                // Method to skip to the end
                if (pick == 99){
                    winner = p;
                    state = STATE_END;
                    window.repaint();
                    return;
                }

                // Make sure the player picks a valid card number
                while (pick < 0 || pick >= p.getHand().size()){
                    System.out.println("Invalid pick. Try again.");
                    pick = scanner.nextInt() - 1;
                    scanner.nextLine();
                }
                chosen = p.getHand().get(pick);

                // Check if card matches the previous
                if (canPlay(chosen, prevCard)){
                    System.out.println(p.getName() + " placed a " + chosen);
                    prevCard = chosen;
                    p.placeCard(chosen);
                    // Let player pick a new suit when 8 is placed down
                    if (prevCard.getRank().equals("8")){
                        System.out.println(p.getName() + " pick a new suit!");
                        String newSuit = scanner.nextLine();

                        prevCard.setSuit(newSuit);
                        newSuitChosen = true;
                        window.repaint();
                    }
                    System.out.println();
                }
                // Player draws if card does not work
                else {
                    System.out.println("Card does not match, you must draw! \n");
                    p.addCard(deck.deal());

                }
                // Check for empty hand
                if (p.getHand().isEmpty()){
                    System.out.println(p.getName() + " wins!");
                    winner = p;
                    state = STATE_END;
                    // Repaint for states
                    window.repaint();
                    return;
                }
            }
            turnCounter++;

        }

    }


    public static void main(String[] args){
       Game g = new Game();
       g.playGame();
    }
}
