
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author hismahil
 */
public class Explosao {
    
    private BufferedImage explosao[]; //vetor de sprites da explosão
    private Sprites sprite; //para carregar sprites e quebrar em sub imagens
    private int x, y, i = 0; //dados gerais
    /**
     * 
     * @param pathFile nome do arquivo de sprite
     * @param x local em X da tela onde será mostrado a explosão
     * @param y local em Y da tela onde será mostrado a explosão
     * @param width largura do sprite
     * @param height altura do sprite
     * @param quantidade quantidade de frames
     * @throws IOException 
     */
    public Explosao(String pathFile,int x,int y,int width,int height,int quantidade) throws IOException{
        //carrega e quebra sprites
        sprite = new Sprites(pathFile, 0, width, height, quantidade);
        explosao = sprite.getSprites(); //pega vetor de sprites
        this.x = x; //seta coordenadas
        this.y = y;
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        if(i < explosao.length){ //verifica se i não alcançou o limite do vetor
            g.drawImage(explosao[i], x, y, null);
            i++;
        }
        
    }
    /**
     * 
     * @return true se i == ao tamanho do vetor de sprites da explosão
     * @return false caso contrario
     */
    public boolean getIter(){
        if(i == explosao.length) return true;
        return false; 
    }
    /**
     * 
     * @return quantidade de frames
     */
    public int getQTFrames(){
        return explosao.length;
    }
}
