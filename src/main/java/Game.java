public class Game {
    private Player player;
    private Deck playerDeck;

    public static void main(String[] args){
        System.out.println("Welcome to the game!");

        Card card1 = new Card("Spades", "King", 5);

        System.out.println(card1);

        Player p = new Player("Player");
        p.addCard(card1);

        Card card2 = new Card("Hearts", "9", 0);

        p.addCard(card2);

        Card card3 = new Card("Clubs","8", 2);

        p.addCard(card3);

        System.out.println(p);
    }
}
