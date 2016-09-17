
package spaceinvaders.com;

import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Sprites {
    
    private LoadResources resource;
    private BufferedImage temp; 
    private BufferedImage[] buffer; //vetor de frame do sprite
    
    /**
     * 
     * @param FileName arquivo do sprite
     * @param hv orientação 0 da esquerda para direita, 1 de cima para baixo
     * @param width largura de cada sub imagem
     * @param height altura de cada sub imagem
     * @param qt quantidade de frames
     * @throws IOException 
     */
    public Sprites(String FileName,int hv,int width,int height,int qt) throws IOException{
        
        resource = new LoadResources();
        temp = resource.resourceImageLoader(FileName);
        buffer = new BufferedImage[qt];
        
        switch(hv){
            case 0:
                    for(int i = 0; i < qt; i++){
                        buffer[i] = temp.getSubimage(i*width, 0, width, height);
                    }
                    break;
            case 1:
                    for(int i = 0; i < qt; i++){
                        buffer[i] = temp.getSubimage(0, i*height, width, height);
                    }
                    break;
        }
        
        resource = null;
    }
    /**
     * 
     * @return buffer
     */
    public BufferedImage[] getSprites(){
        
        return buffer;
    }
}
