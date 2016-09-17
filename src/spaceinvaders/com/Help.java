
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Help {

    private BufferedImage help; //imagem dos controles
    private LoadResources resource; //para carregar
    /**
     * Construtor que carrega imagem dos controles do jogo
     * @throws IOException 
     */
    public Help() throws IOException{
        resource = new LoadResources();
        
        help = resource.resourceImageLoader("help.png"); //carrega imagem
        
        resource = null;
    }
    /**
     * 
     * @param g referência da imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        if(help != null)//se existe bota na tela
            g.drawImage(help, 0, 0, null);
    }
}
