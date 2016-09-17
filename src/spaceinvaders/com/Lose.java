
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Lose {

    private BufferedImage lose; //imagem de vc perdeu
    private LoadResources resource;
    /**
     * 
     * @param File nome da imagem
     * @throws IOException 
     */
    public Lose(String File) throws IOException{
        resource = new LoadResources();
        lose = resource.resourceImageLoader(File); //carrega imagem
        resource = null;
    }
    /**
     * 
     * @param g referência da imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        if(lose != null){
            g.drawImage(lose, 0, 0, null);

            for(int i = 0; i < 100000000L; i++);
        }
    }
}
