import javax.swing.*;
import java.awt.*;

public class Card {
    private String suit;
    private String rank;
    private int value;
    private int imageIndex;
    private Image cardImage;

    public Card(String rank, String suit, int value, int imageIndex){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.imageIndex = imageIndex;
        this.cardImage = new ImageIcon("src/main/resources/Cards/" + imageIndex + ".png").getImage();
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

    public int getImageIndex(){
        return imageIndex;
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

    public void draw(Graphics g, int x, int y, GameView window){
        g.drawImage(cardImage, x, y, 80, 120, window);
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
