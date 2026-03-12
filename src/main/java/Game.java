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
    private boolean isRunning;

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

    public void reset() {
        players.clear();
        deck = new Deck(ranks, suits, values);
        state = STATE_INSTR;
        turnCounter = 1;
        winner = null;
        newSuitChosen = false;
        // We don't recreate the window, just repaint it
        window.repaint();
    }

    // Add this method to Game.java
    public void restart() {
        // Clear the current players list
        players.clear();
        // Re-initialize the deck with the original constants
        deck = new Deck(ranks, suits, values);
        // Reset game state and counters
        state = STATE_INSTR;
        turnCounter = 1;
        winner = null;
        newSuitChosen = false;
        // Reset the loop flag
        isRunning = true;
        System.out.println("\n--- GAME RESTARTED ---\n");
    }

    public void playGame(){
        System.out.println(printInstructions());
        window.repaint();

        Scanner scanner = new Scanner(System.in);

        // OUTER LOOP: Handles restarting the whole game
        while (true) {
            // 1. Reset all game state for a fresh round
            players.clear();
            deck = new Deck(ranks, suits, values);
            state = STATE_INSTR;
            turnCounter = 1;
            winner = null;
            newSuitChosen = false;

            setUpPlayers(scanner);
            dealStartingHands();
            prevCard = drawStartingCard();

            System.out.println("Starting Card: " + prevCard);
            Card chosen = null;
            boolean gameWon = false;

            // INNER LOOP: Handles the current round
            while (!gameWon){
                int pick;
                // Turn for each player
                for (Player p : players) {
                    newSuitChosen = false;

                    if (players.indexOf(p) == 0) {
                        state = STATE_PLAYER1;
                    } else {
                        state = STATE_PLAYER2;
                    }
                    window.repaint();

                    System.out.println(p.getName() + ": Pick a card");
                    System.out.println(p.getHand());

// 1. Get the exact number the user typed first
                    pick = scanner.nextInt();
                    scanner.nextLine(); // consume the newline

// 2. Check for the exit code (99) BEFORE subtracting 1
                    if (pick == 99){
                        winner = p;
                        state = STATE_END;
                        window.repaint();
                        gameWon = true;
                        break;
                    }

                    // 3. Now subtract 1 so it matches the ArrayList index (0-based)
                    pick = pick - 1;

                    // Make sure the player picks a valid card number
                    while (pick < 0 || pick >= p.getHand().size()){
                        System.out.println("Invalid pick. Try again.");
                        pick = scanner.nextInt();
                        scanner.nextLine();

                        // Check for 99 inside the loop too, just in case!
                        if (pick == 99) {
                            winner = p;
                            state = STATE_END;
                            window.repaint();
                            gameWon = true;
                            break;
                        }
                        pick = pick - 1;
                    }

                    // If they typed 99 inside the loop, we need to break out of the outer loop too
                    if (gameWon) break;

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
                        window.repaint();
                        gameWon = true;
                        break; // break out of player loop
                    }
                }
                if (!gameWon) {
                    turnCounter++;
                }
            }

            // --- RESTART PROMPT IN TERMINAL ---
            System.out.println("\nWould you like to play again? (yes/no)");
            String playAgain = scanner.nextLine();

            if (!playAgain.equalsIgnoreCase("yes")) {
                System.out.println("Thanks for playing!");
                break; // Breaks the outer loop, ending the program
            } else {
                System.out.println("\n--- STARTING NEW GAME ---\n");
            }
        }
    }

    public static void main(String[] args){
        Game g = new Game();
        g.playGame();
    }
} // <-- THIS IS THE FINAL BRACE FOR THE GAME CLASS. DO NOT ADD CODE BELOW THIS.