
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author hismahil
 */
public class Cometa {

    private BufferedImage[] cometa; //vetor com sequencia do cometa
    private int x, y, dy, i = 0, j = 0;
    /**
     * 
     * @param cometa vetor com sequencia de imagens do cometa
     * @param x posição x onde será desenhado o cometa 
     * @param y posição y onde será desenhado o cometa
     */
    public Cometa(BufferedImage[] cometa,int x,int y){
        this.cometa = cometa;
        this.x = x;
        this.y = y;
        
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        //verifica i de acordo com cada frame da animação do cometa
        if(i < cometa.length){
            g.drawImage(cometa[i], x, y, null); //desenha
            if( j == 20 ) { //j fica como um delay entre um frame e outro
                i++; //passa pro próximo frame
                j = 0;
            }
        }
        else i = 0;
    }
    /**
     * Método que atualiza a posição X ou Y do elemento
     */
    public void move(){
        y += dy; //atualiza y
        j++; //atualiza j até 20
    }
    /**
     * 
     * @return coordenada X
     */
    public int getX(){
        return x;
    }
    /**
     * 
     * @return coordenada Y
     */
    public int getY(){
        return y;
    }
    /**
     * 
     * @param dy atualiza Y
     */
    public void setDY(int dy){
        this.dy = dy;
    }
}
