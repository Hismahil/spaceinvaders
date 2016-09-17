
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Carregando {

    private BufferedImage carga; //buffer para imagem de carregando
    private LoadResources resource = new LoadResources(); 
    /**
     * 
     * @param FileName do arquivo com a imagem
     * @throws IOException 
     */
    public Carregando(String FileName) throws IOException{
        //carrega imagem
        carga = resource.resourceImageLoader(FileName);
        //resource = null;
    }
    /**
     * 
     * @param g referência a imagem em Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        g.drawImage(carga, 0, 0, null);
    }
}
