
package spaceinvaders.com.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author hismahil
 */
public class LoadResources {

    /**
     * 
     * @param FileName arquivo de imagem
     * @return referência da imagem
     * @throws IOException 
     */
    public BufferedImage resourceImageLoader(String FileName) throws IOException{
        URL url = this.getClass().getResource("imgs/"+FileName);
        return ImageIO.read(url);
    }
    /**
     * 
     * @param FileName arquivo de som
     * @return retorna URL do som
     * @throws IOException 
     */
    public URL resourceSoundLoader(String FileName) throws IOException{
        URL url = this.getClass().getResource("sounds/"+FileName);
        return url;
    }
}
