public class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public void setSuit(String suit){
        this.suit = suit
    }
}
