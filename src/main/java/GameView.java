import javax.swing.*;
import java.awt.*;
public class GameView extends JFrame{
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 500;

    private Game backend;

    public GameView(Game backend){
        this.backend = backend;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Crazy Eights");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        // Paint States
        if (backend.getState() == Game.STATE_INSTR){
            g.setColor(Color.red);
        } else if (backend.getState() == Game.STATE_PLAYER1){
            g.setColor(Color.green);
        } else if (backend.getState() == Game.STATE_PLAYER2){
            g.setColor(Color.blue);
        } else{
            g.setColor(Color.yellow);
        }

        g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
