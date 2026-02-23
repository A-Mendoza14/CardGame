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
        for (int i = 0; i < 52; i++){
            cardImages[i] = new ImageIcon("Cards/" + i + ".png").getImage();
        }
         cardImages[53] = new ImageIcon("Cards/back.png").getImage();
    }

    public void paintInstructions(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.black);

        // Calculate line height
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();

        // Split string into lines
        String instr = backend.printInstructions();
        int y = 100;
        g.setFont(new Font("Serif", Font.BOLD, 20));
        for (String line : instr.split("\n")){
            g.drawString(line, 100, y);
            y += lineHeight + 5;
        }
    }

    public void paintPlayer1(Graphics g){

    }

    public void paint(Graphics g){
        // Paint States
        if (backend.getState() == Game.STATE_INSTR){
            paintInstructions(g);
//            g.setColor(Color.red);
        } else if (backend.getState() == Game.STATE_PLAYER1){
            g.setColor(Color.green);
            g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        } else if (backend.getState() == Game.STATE_PLAYER2){
            g.setColor(Color.blue);
            g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        } else{
            g.setColor(Color.yellow);
            g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        }

    }
}
