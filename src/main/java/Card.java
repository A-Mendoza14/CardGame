public class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    // Getter Methods
    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    //Setter Methods
    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean sameCard(Card c){
        if(this.getSuit().equals(c.getSuit()) && this.getRank().equals(c.getRank()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return suit + " of " + rank;
    }
}
