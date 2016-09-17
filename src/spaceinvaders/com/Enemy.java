
package spaceinvaders.com;

import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;

public class Enemy {
    
    private BufferedImage enemy; //imagem da nave inimiga
    private LoadResources load; //para carregar imagem
    private int x, y, width, height, dx, xw; //dados de cada nave
    /**
     * 
     * @param FileNameEnemy nome do arquivo do sprite da nave
     * @throws IOException 
     */
    public Enemy(String FileNameEnemy) throws IOException{
        load = new LoadResources();
        enemy = load.resourceImageLoader(FileNameEnemy); //carrega imagem 
        //dados da largura e altura
        width = enemy.getWidth();
        height = enemy.getHeight();
        load = null;
    }
    /**
     * Método que atualiza X da nave inimiga
     */
    public void move(){
        
        x += dx; //seta
        
        if(x < 0) x = 0; //se alcançou o lado esquerdo seta X com zero
        
    }
    /**
     * 
     * @param x seta nova posição em X
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * 
     * @param y seta nova posição em Y
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * 
     * @return retorna posição X atual
     */
    public int getX(){
        return x;
    }
    /**
     * 
     * @return retorna posição Y atual
     */
    public int getY(){
        return y;
    }
    /**
     * 
     * @return retorna largura do sprite
     */
    public int getWidth(){
        return width;
    }
    /**
     * 
     * @return retorna altura do sprite
     */
    public int getHeight(){
        return height;
    }
    /**
     * 
     * @return retorna referência de inimigo
     */
    public BufferedImage getEnemy(){
        return enemy;
    }
    /**
     * 
     * @param dx seta dx com novo valor para o método move()
     */
    public void setDX(int dx){
        this.dx = dx;
    }

    /**
     * 
     * @param xw seta X + Width
     */
    public void setXW(int xw){
        this.xw = xw;
    }
}
