
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Creditos {

    private BufferedImage credito; //imagem de creditos do jogo
    private LoadResources resource = new LoadResources();
    
    public Creditos() throws IOException{
        //carrega imagem
        credito = resource.resourceImageLoader("creditos.png");
    }
    /**
     * 
     * @param g referência para imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        if(credito != null)
            g.drawImage(credito, 0, 0, null);
    }
}
