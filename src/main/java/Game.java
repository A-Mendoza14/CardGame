import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private Player player;
    private Deck playerDeck;

    public Game(Player p, Deck playerDeck){
        this.player = player;
        this.playerDeck = playerDeck;
    }

    public void printInstructions(){
        System.out.println("Hello!");
    }

    public void playGame(){
        ArrayList<Card> hand  = new ArrayList<Card>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Player 1 name: ");
        String name = scanner.nextLine();
        Player player1 = new Player(name, )

        System.out.println("Player 2 name: ");
        name = scanner.nextLine();
        Player player2 = new Player(name, )
    }

    public void makeDeck(){
        String[] ranks = {"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
    }

    public static void main(String[] args){
       Game g = new Game();
    }
}
