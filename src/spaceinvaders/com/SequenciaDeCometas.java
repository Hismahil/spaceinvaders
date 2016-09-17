
package spaceinvaders.com;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import spaceinvaders.com.interfaces.WinDefs;

/**
 *
 * @author hismahil
 */
public class SequenciaDeCometas {

    private ArrayList<Cometa> sequencia = new ArrayList<Cometa>();//arraylist de cometas
    private Cometa cometa;//para criar os cometas
    private boolean dezSecs = true, cria = true; //verifica se passou 10 segundos e cria novo cometa
    private int quantidade = 0; //para atualiza os cometas
    
    private Sprites sprite; //para carregar o sprite do cometa
    /**
     * 
     * @param FileName arquivo do sprite
     * @param width largura de cada sub imagem
     * @param height altura de cada sub imagem
     * @param quantidade de frames
     * @throws IOException 
     */
    public SequenciaDeCometas(String FileName,int width,int height,int quantidade) throws IOException{
        //carrega sprite
        sprite = new Sprites(FileName, 1, width, height, 6);
        
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
         
        //se a sequencia não é null
        if( sequencia != null ){
            for(int i = 0; i < sequencia.size(); i++){
                //System.out.println(sequencia.size());
                if(sequencia.get(i).getY() == WinDefs.GHEIGHT){
                    sequencia.remove(i); //se y do cometa é o limite da tela
                    //tira do arraylist
                }
                else sequencia.get(i).draw(g); //desenha o cometa
            }
        }
    }
    /**
     * Método que movimenta os cometas
     */
    public void move(){
        
        if(dezSecs) {//se passo 10 segundos
            loadCometas();
            dezSecs = false;
            
        }
        if(sequencia != null){
            
            for(int i = 0; i < sequencia.size(); i++){
                sequencia.get(i).setDY(+1);
                sequencia.get(i).move();
            }
        }
    }
    /**
     * Método que cria novo cometa
     */
    private void loadCometas(){
        int x, y; //coordenadas
        x = (int) (Math.random()*WinDefs.GWIDTH);
        y = -128;
        cometa = new Cometa(sprite.getSprites(), x, y);
        sequencia.add(cometa);
    }
    /**
     * 
     * @param secs seta 10 segundos como true
     */
    public void dezSecs(boolean secs){
        dezSecs = secs;
    }
    
    /**
     * 
     * @param x coordenada 
     * @param xwidth coordenada x + a largura do elemento intersectado
     * @param y coordenada y
     * @param yheight coordenada y + a altura do elemento intersectado
     * @return true se atingiu
     * @return false se n atingiu
     */
    /*public boolean intersects(int x,int xwidth,int y,int yheight){
        
        if(!sequencia.isEmpty()){
            for(int i = 0; i < sequencia.size(); i++){
                if( (sequencia.get(i).getX() >= x && sequencia.get(i).getX() <= xwidth)
                    && (sequencia.get(i).getY() >= y && sequencia.get(i).getY() <= yheight) ){
                        
                        return true;
                }
            }
        }
        
        return false;
    }*/
}
