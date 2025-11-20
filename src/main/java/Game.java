import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Deck deck;


    public Game(){
        deck = makeDeck();
        for (int i = 0; i < deck.getCardsLeft(); i++){
            System.out.println(deck.deal());
        }
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

        System.out.println("Player 2 name: ");
        name = scanner.nextLine();
        Player player2 = new Player(name);
    }

    public Deck makeDeck(){
        String[] ranks = {"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int[] values = {1, 10, 10, 10, 10, 9, 50, 7, 6, 5, 4, 3, 2, 1};

        return new Deck(ranks, suits, values);
    }

    public static void main(String[] args){
       Game g = new Game();
    }
}
