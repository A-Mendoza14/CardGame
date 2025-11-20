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
        System.out.println("Hello!");
    }

    public void playGame(){
        ArrayList<Card> hand  = new ArrayList<Card>();
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

        System.out.println("Player 1: Pick a card");
        System.out.println(player1.getHand());

        int pick = scanner.nextInt() - 1;
        System.out.println(player1.getHand().get(pick));
        player1.placeCard(player1.getHand().get(pick));
        System.out.println(player1.getHand());

        while (player1.getHand().size() != 0 && player2.getHand().size() != 0){

            // Player places card
            System.out.println();
            // Check if card matches the previous
            // Check for empty hand
            // Player wins
            // If it doesn't, player has to draw

        }

    }


    public static void main(String[] args){
       Game g = new Game();
       g.playGame();
    }
}
