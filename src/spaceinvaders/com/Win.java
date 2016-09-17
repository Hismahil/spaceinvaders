
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Win {

    private BufferedImage win; //tela de você venceu
    private LoadResources resource;
    /**
     * 
     * @param FileName arquivo com a tela de vitória
     * @throws IOException 
     */
    public Win(String FileName) throws IOException{
        resource = new LoadResources();
        //carrega tela
        win = resource.resourceImageLoader(FileName);
        
        resource = null;
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        if(win != null)
            g.drawImage(win, 0, 0, null);
        
    }
   
}
