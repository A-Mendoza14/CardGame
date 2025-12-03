import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values){
        cards = new ArrayList<Card>();
        for(int i = 0; i < ranks.length; i++){
            for (int j = 0; j < suits.length; j++) {
                Card c = new Card(ranks[i], suits[j], values[i]);
                cards.add(c);
            }
        }
        cardsLeft = cards.size();

        shuffle();
    }

    public boolean isEmpty(){
        return cardsLeft == 0;
    }

    public int getCardsLeft(){
        return cardsLeft;
    }

    public Card deal() {
        if (cardsLeft == 0) {
            return null;
        }
        cardsLeft--;
        return cards.get(cardsLeft);
    }

    public void shuffle() {
        cardsLeft = cards.size();
        for (int i = cardsLeft - 1; i > 0; i--) {
            int r = (int) (Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, temp);
        }
    }
}
