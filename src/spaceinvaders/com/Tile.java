
package spaceinvaders.com;

import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.interfaces.WinDefs;
import spaceinvaders.com.resources.LoadResources;

public class Tile {
    
    private BufferedImage tile; //imagem do tileset
    private int width,height; //dimensões da imagem
    private int x, y; //posição na tela
    private LoadResources load = new LoadResources();
    
    /**
     * pega imagem do arquivo
     * @param pathImg nome do arquivo
     */
    public Tile(String pathImg) throws IOException{
        tile = load.resourceImageLoader(pathImg);
        
        width = tile.getWidth();
        height = tile.getHeight();
    }
    /**
     * Método que movimenta o tile
     */
    public void move(){
        if(y > WinDefs.GHEIGHT) y = -1600;
        else y++;
    }
    /**
     * retorna o tileset
     * @return tile
     */
    public BufferedImage getTileSet(){
        return tile;
    }
    
    /**
     * retorna largura da imagem
     * @return width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * retorna altura da imagem
     * @return height
     */
    public int getHeight(){
        return height;
    }
    /**
     * instala e retorna posição na tela do tileset
     * @param x nova posição em X
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * 
     * @param y nova posição em Y
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * 
     * @return X
     */
    public int getX(){
        return x;
    }
    /**
     * 
     * @return Y
     */
    public int getY(){
        return y;
    }
    
}
