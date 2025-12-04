import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;

    String[] ranks = {"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    int[] values = {1, 10, 10, 10, 10, 9, 50, 7, 6, 5, 4, 3, 2};
    private Deck deck;


    public Game(){
        deck = new Deck(ranks, suits, values);
        players = new ArrayList<>();
    }

    public void printInstructions(){
        System.out.println("""
                Hello and Welcome to Crazy 8s!\n
                You must match the suit or rank of the previous card played. \n
                If you don't have a card that works, you must draw, and your turn gets skipped.\n
                You can place down an 8 \n
                at any time. Placing down an 8 also lets you pick the next suit. \n
                When typing your suit, please  make \n
                sure to spell suits correctly. \n
                To place a card, type 1 to place the first, 2 to place the second, \n
                and so on. Good Luck!""");
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
        printInstructions();

        Scanner scanner = new Scanner(System.in);

        setUpPlayers(scanner);

        dealStartingHands();

        Card prevCard = drawStartingCard();

        System.out.println("Starting Card: " + prevCard);
        Card chosen = null;

        while (true){
            int pick;
            // Turn for each player
            for (Player p : players) {
                // Player places card
                System.out.println(p.getName() + ": Pick a card");
                System.out.println(p.getHand());

                pick = scanner.nextInt() - 1;
                scanner.nextLine();

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
                    return;
                }
            }

        }

    }


    public static void main(String[] args){
       Game g = new Game();
       g.playGame();
    }
}
