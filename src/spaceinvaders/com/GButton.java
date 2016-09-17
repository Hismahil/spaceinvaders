
package spaceinvaders.com;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.resources.LoadResources;



public class GButton{
    
    private BufferedImage btnNormal; //imagem do botão normal
    private BufferedImage btnHover; //imagem do botão em hightlight
    private LoadResources resources = new LoadResources();
    private boolean status = false; //false botão normal true botão em hightlight
    private int x, y, width, height; //dados gerais
    private Sound over; //som quando passa o mouse em cima
    private Sound click; //som quando é clicado
    
    /**
     * 
     * @param btnNormalPath Nome do arquivo com a imagem do botão Normal
     * @param btnHoverPath Nome do arquivo com a imagem do botão HightLight
     * @param x coordenada x
     * @param y coordenada y
     * @param soundOver Nome do arquivo de som quando o mouse passa por cima
     * @param soundClick Nome do arquivo de som quando é clicado
     * @throws IOException 
     */
    public GButton(String btnNormalPath,String btnHoverPath,int x, int y,String soundOver,String soundClick) throws IOException{
        //imagens do botão
        btnNormal = resources.resourceImageLoader(btnNormalPath);
        btnHover = resources.resourceImageLoader(btnHoverPath);
        //sons
        over = new Sound(soundOver);
        click = new Sound(soundClick);
        //posição na tela
        this.x = x;
        this.y = y;
        this.width = btnNormal.getWidth();
        this.height = btnNormal.getHeight();
    }
    /**
     * 
     * @param g referêcia a imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        
        if( status ){ //se status é true mostra botão hover
            g.drawImage(btnHover, x, y, null);
        }
        else{ //se status é falso mostra botão normal
            g.drawImage(btnNormal, x, y, null);
        }
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
    /**
     * 
     * @return width
     */
    public int getWidth(){
        return width;
    }
    /**
     * 
     * @return height
     */
    public int getHeight(){
        return height;
    }
    /**
     * 
     * @param status true se o mouse esta em cima do botão
     */
    public void setStatus(boolean status){
        this.status = status;
    }
    /**
     * Método que executa o som Over
     */
    public void playSoundOver(){
        over.playSound();
    }
    /**
     * Método que executa o som do click
     */
    public void playSoundClick(){
        click.playSound();
    }
}
