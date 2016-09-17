
package spaceinvaders;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import spaceinvaders.com.GamePanel;
import spaceinvaders.com.interfaces.WinDefs;

public class Main extends JFrame{

    private GamePanel game;
    /**
     * frame onde o gamePanel funciona
     * @throws IOException 
     */
    public Main() throws IOException{
        game = new GamePanel();
        
        setTitle(WinDefs.title);
        setSize(new Dimension(WinDefs.GWIDTH, WinDefs.GHEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container ct = getContentPane();
        ct.add(game);
        pack();
        
        setVisible(true);
        
    }
    /**
     * Método principal que inicia o frame
     * @param x
     * @throws IOException 
     */
    public static void main(String[] x) throws IOException{
        new Main();
        
    }

}
