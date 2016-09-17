
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import spaceinvaders.com.interfaces.WinDefs;
import spaceinvaders.com.resources.LoadResources;

public class Player {

    private BufferedImage player, bullet; //imagem do jogador e do disparo
    private LoadResources load;
    private int x, y, width, height, dx; //dados gerais
    private Tiro tiro; //para criar o tiro
    private boolean disparo = true; //permite o disparo
    private Sound shoot; //som do tiro
    private Sound naveKilled; //som da nave inimiga explodindo
    private Explosao explosao; //para explosão da nave inimiga
    private int frameExplosion = 0; 
    private boolean morto = false;
    /**
     * 
     * @param path Nome do arquivo sprite do player
     * @throws IOException 
     */
    public Player(String path) throws IOException{
        load = new LoadResources();
        player = load.resourceImageLoader(path); //carrega sprite do player
        bullet = load.resourceImageLoader("bullet3.png"); //carrega sprite do disparo
        //seta posição da tela onde o player aparecerá
        x = WinDefs.GWIDTH / 2;
        y = WinDefs.GHEIGHT - player.getHeight();
        width = player.getWidth();
        height = player.getHeight();
        //carrega sons
        shoot = new Sound("shoot.wav");
        naveKilled = new Sound("invaderkilled.wav");
        
        load = null;
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     * @throws IOException 
     */
    public void draw(Graphics g) throws IOException{
        if(!morto) { //se o jogador não esta morto
            g.drawImage(player, x, y, null);
        }
        else{ //bota vetor de explosão do player na tela
            if(explosao == null) explosao = new Explosao("explosao64.png", x, y, 64, 64, 12);
            explosao.draw(g);
            frameExplosion++;
        }
    }
    /**
     * Método que move a nave do jogador
     */
    public void move(){
        x += dx; //atualiza x
        
        if( x <= 0 ) x = 0; //verifica canto esquerdo e canto direito
        if( (x + width) >= WinDefs.GWIDTH ) x = WinDefs.GWIDTH - width;
        
        //se tiro não saiu da tela e o tiro não for null e o disparo for falso (já foi criado o tiro)
        if (tiro != null && tiro.getY() > 0 && !disparo) {
            tiro.move(); //move
        } else {
            disparo = true; //se não seta novo disparo
        }
    }
    /**
     * 
     * @param x seta nova posição X da nave
     */
    public void setDX(int x){
        this.dx = x;
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
     * @return player
     */
    public BufferedImage getPlayer(){
        return player;
    }
    /**
     * 
     * @param e referência aos eventos capturados pelo GamePanel
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if( code == KeyEvent.VK_LEFT ){
            setDX(-5); //decrementa x
        }
        if( code == KeyEvent.VK_RIGHT ){
            setDX(+5); //incrementa x
        }
        if( code == KeyEvent.VK_SPACE ){
            if(disparo){ //cria disparo
                tiro = new Tiro(bullet, x + (width / 2) - 4, y);
                tiro.setOrientacao(false);
                tiro.setVelocidade(+10);
                disparo = false;
                shoot.playSound(); //executa o som
            }
        }
    }

    /**
     * 
     * @param e referência aos eventos capturados do GamePanel
     */
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if( code == KeyEvent.VK_LEFT ){
            setDX(0); //não faz nada
        }
        if( code == KeyEvent.VK_RIGHT ){
            setDX(0);
        }
        if( code == KeyEvent.VK_SPACE ){
            shoot.stopSound(); //para o som
        }
    }
    /**
     * 
     * @param disparo seta disparo com true para criar novo disparo
     */
    public void setDisparo(boolean disparo){
        this.disparo = disparo;
        tiro = null; //elimina disparo anterior
    }
    /**
     * 
     * @return disparo
     */
    public boolean getDisparo(){
        return disparo;
    }
    /**
     * 
     * @return tiro
     */
    public Tiro getTiro(){
        return tiro;
    }
    
    /**
     * se o tiro do jogador intersecta a posição x da nave inimiga
     * e posição x + width da nave inimiga e
     * posição y da nave inimiga e posição y + height da nave inimiga
     * desabilita tiro
     * retorna true
     * caso contrario rotorna false
     * @param x coordenada
     * @param xwidth x+width
     * @param y coordenada
     * @param yheight y + height
     * @return true se acertou a nave inimiga
     * @return false se não acertou
     */
    public boolean intersects(int x,int xwidth,int y,int yheight){
        
        if( tiro != null &&
           (tiro.getX() >= x && tiro.getX() <= xwidth)
           && (tiro.getY() >= y && tiro.getY() <= yheight) ){
            tiro = null;
            naveKilled.playSound();
            return true;
        }
        return false;
    }
    /**
     * 
     * @param morto seta player como morto
     */
    public void setKilled(boolean morto){
        this.morto = morto;
    }
    /**
     * 
     * @return frameExplosion
     */
    public int getFrameExplosion(){
        return frameExplosion;
    }
    /**
     * 
     * @return quantidade de frames da explosão
     */
    public int getFrameExplosionTotal(){
        if(explosao != null) return explosao.getQTFrames();
        return -1;
    }
    /**
     * 
     * @return morto
     */
    public boolean isMorto(){
        return morto;
    }
}
