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
                Hello and Welcome to Crazy 8s!\s
                 You must match the suit or rank of the previous card played. \
                If you don't have a card that works, you must draw, and your turn gets skipped.\s
                 You can place down an 8 \
                at any time. Placing down an 8 also lets you pick the next suit. Good Luck!""");
    }

    public void playGame(){
        printInstructions();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Player 1 name: ");
        String name = scanner.nextLine();
        Player player1 = new Player(name);
        players.add(player1);

        System.out.println("Player 2 name: ");
        name = scanner.nextLine();
        Player player2 = new Player(name);
        players.add(player2);

        for (Player p : players){
            for (int i = 0; i < 7; i++){
                p.addCard(deck.deal());
            }
        }

        Card prevCard = null;
        Card chosen = null;
        while (!player1.getHand().isEmpty() && !player2.getHand().isEmpty()){
            int pick;
            for (int i = 0; i < 2; i++) {
                // Player places card
                System.out.println(players.get(i).getName() + ": Pick a card");
                System.out.println(players.get(i).getHand());

                pick = scanner.nextInt() - 1;
                scanner.nextLine();

                while (pick < 0 || pick >= players.get(i).getHand().size()){
                    System.out.println("Invalid pick. Try again.");
                    pick = scanner.nextInt() - 1;
                    scanner.nextLine();
                }
                chosen = players.get(i).getHand().get(pick);

                // Check if card matches the previous
                if (prevCard == null || chosen.getRank().equals(prevCard.getRank())
                        || chosen.getSuit().equals(prevCard.getSuit()) || chosen.getRank().equals("8")){
                    System.out.println(players.get(i).getName() + " placed a " + chosen);
                    prevCard = chosen;
                    players.get(i).placeCard(chosen);
                    if (prevCard.getRank().equals("8")){
                        System.out.println(players.get(i).getName() + " pick a new suit!");
                        String newSuit = scanner.nextLine();
                        prevCard.setSuit(newSuit);
                    }
                    System.out.println();
                }
                else {
                    System.out.println("Card does not match, you must draw! \n");
                    players.get(i).addCard(deck.deal());

                }
                // Check for empty hand
                if (players.get(i).getHand().isEmpty()){
                    System.out.println(players.get(i).getName() + " wins!");
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
