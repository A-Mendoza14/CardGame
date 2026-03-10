import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame{
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 500;

    private Game backend;
    private Image[] cardImages;

    public GameView(Game backend){
        this.backend = backend;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Crazy Eights");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        cardImages = new Image[53];
        cardImages[52] = new ImageIcon("src/main/resources/Cards/back.png").getImage();
    }

    public void paintInstructions(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.black);

        // Split string into lines
        String instr = backend.printInstructions();
        int y = 115;
        g.setFont(new Font("Serif", Font.BOLD, 20));
        for (String line : instr.split("\n")){
            g.drawString(line, 100, y);
            y += 25;
        }
    }

    public void paintRoundCounter(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("Turn: " + backend.getTurnCounter(), 5, 55);
    }

    public void paintBackground(Graphics g){
        // Set background for game
        Color darkGreen = new Color(0, 100, 0);
        g.setColor(darkGreen);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);


        // Draw card placed and draw pile
        g.setColor(Color.white);
        g.drawString("Card Placed", 425, 125);
        Card prevCard = backend.getPrevCard();
        prevCard.draw(g, 425, 150, this);
        g.drawString("Draw Pile", 275, 125);
        g.drawImage(cardImages[52], 265, 150, 80, 120, this);
        paintRoundCounter(g);
    }

    public void paintPlayer1(Graphics g){
        paintBackground(g);

        // Draw player hand
        int x = 150;
        int y = 300;

        Player p = backend.getPlayers().get(0);
        for (Card c : p.getHand()){
            c.draw(g, x, y, this);
            x += 20;
        }

        // Draw player name
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g.drawString(p.getName() + " turn", 100, 100);
    }

    public void paintPlayer2(Graphics g){
        paintBackground(g);

        // Draw player hand
        int x = 150;
        int y = 300;

        Player p = backend.getPlayers().get(1);
        for (Card c : p.getHand()){
            c.draw(g, x, y, this);
            x += 20;
        }

        // Draw player name
        g.setColor(Color.white);
        g.drawString(p.getName() + " turn", 100, 100);
    }

    public void paintGameOver(Graphics g){
        // Draw Background
        g.setColor(Color.green);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
        // Get winner and print name out
        Font winner = new Font("SansSerif", Font.BOLD, 50);
        g.setFont(winner);
        g.setColor(Color.black);
        g.drawString(backend.getWinner().getName() + " is the winner!", 225, 250);
    }

    public void paint(Graphics g){
        // Paint States
        if (backend.getState() == Game.STATE_INSTR){
            paintInstructions(g);
        } else if (backend.getState() == Game.STATE_PLAYER1){
            paintPlayer1(g);
        } else if (backend.getState() == Game.STATE_PLAYER2){
            paintPlayer2(g);
        } else{
            paintGameOver(g);
        }

        if (backend.getNewSuitChosen()){
            g.drawString("New Suit: " + backend.getPrevCard().getSuit(), 325, 100);
        }
    }
}
