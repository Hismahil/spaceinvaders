
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author hismahil
 */
public class Tiro {
    
    private BufferedImage tiro; //imagem do tiro
    private boolean orientacao; //true para cima false para baixo
    private int x, y, width, height; //dimensões do tiro
    private int velocidade;
    
    /**
     * 
     * @param bullet imagem do tiro
     * @param x coordenada do tiro
     * @param y coordenada do tiro
     */
    public Tiro(BufferedImage bullet,int x,int y) {
        tiro = bullet;
        this.x = x;
        this.y = y;
        width = tiro.getWidth();
        height = tiro.getHeight();
    }
    /**
     * faz o movimento do tiro
     */
    public void move(){
        if(orientacao) y+=velocidade;
        else y-=velocidade;
    }
    /**
     * desenha o tiro no double buffering
     * @param g referência a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        g.drawImage(tiro, x, y, null);
    }
    /**
     * diz em qual sentido o tiro irá se mover
     * @param true sentido para cima
     * @param false sentido para baixo
     * @param orientacao 
     */
    public void setOrientacao(boolean orientacao){
        this.orientacao = orientacao;
    }
    /**
     * 
     * @return y
     */
    public int getY(){
        return y;
    }
    /**
     * 
     * @return x
     */
    public int getX(){
        return x;
    }
    /**
     * 
     * @return tiro
     */
    public BufferedImage getTiro(){
        return tiro;
    }
    /**
     * 
     * @param velocidade seta velocidade do movimento
     */
    public void setVelocidade(int velocidade){
        this.velocidade = velocidade;
    }
}
