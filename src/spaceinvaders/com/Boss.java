
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import spaceinvaders.com.interfaces.WinDefs;
import spaceinvaders.com.resources.LoadResources;

/**
 *
 * @author hismahil
 */
public class Boss {

    private BufferedImage boss; //sprite do boss
    private BufferedImage missiel;
    private LoadResources load;
    private int x, y, width, height, dx; //definições gerais
    private int bossHeal = 10; //quantidade de tiros que o boss pode levar até morrer
    private ArrayList<Tiro> bossTiro = new ArrayList<Tiro>(); //para organizar os tiros
    private int bossQtTiros = 5; //quantidade maxima de tiros
    private boolean direction = false; //false para esquerda true para direita
    private boolean delay = true; //espera 10x para criar outro tiro
    private int countDelay = 0; //contabiliza o dalay
    private Sound shoot; //som do tiro
    private Sound killed; //som de explosão
    private Explosao bossFail = null; //vetor de explosão
    
    
    /**
     * coordenada x e y do boss
     * FileBoss = nome do arquivo da imagem do boss
     * @param FileBoss nome do sprite do boss
     * @param x coordenada x onde o boss aparecerá de inicio
     * @param y coordenada y onde o boss aparecerá de inicio
     * @throws IOException 
     */
    public Boss(String FileBoss,int x,int y) throws IOException{
        
        load = new LoadResources(); 
        boss = load.resourceImageLoader(FileBoss); //carrega sprite do boss
        this.x = x; //seta coordenadas da tela 
        this.y = y;
        missiel = load.resourceImageLoader("missiel.png"); //carrega sprite do missiel
        //sons de tiro e quando ele é morto
        shoot = new Sound("shoot.wav");
        killed = new Sound("explosion.wav");
        //dimensões da imagem
        width = boss.getWidth();
        height = boss.getHeight();
 
        
        load = null;
    }
    /**
     * Desenha a imagem do boss no double buffering e
     * os tiros
     * @param g referência da imagem em Double Buffering do GamePanel
     */
    public void draw(Graphics g) throws IOException{
        //se o boss existe e o hp dele é maior que 0
        if(boss != null && bossHeal > 0) {
            g.drawImage(boss, x, y, null);
            //causa uma espera entre um disparo e outro
            if (delay) {
                createFire();
                delay = false;
            } else {
                waitFire(); //espera para o próximo disparo
            }
            /**
             * verifica se a sequencia de tiros não esta vasia verifica se o
             * tiro saiu da tela, se saiu remove se não desenha na nova posição
             */
            if (!bossTiro.isEmpty()) {
                for (int i = 0; i < bossTiro.size(); i++) {
                    if (bossTiro.get(i).getY() > WinDefs.GHEIGHT) {
                        bossTiro.remove(i);
                    } else {
                        bossTiro.get(i).draw(g);
                    }
                }
            }
        }
        else {
            //se o hp do boss acabou cria vetor de explosão
            if( bossFail == null) bossFail = new Explosao("explosao64.png", x, y, 64, 64, 12);
            bossFail.draw(g); //repasa referência para o objeto que criará a explosão na tela
        }
    }
    /**
     * atualiza posição da nave do boss e dos tiros
     */
    public void move(){
        if(bossHeal > 0){ //se o hp > 0
            //verifica se ele vai pela esquerda ou pela direita
            if(direction) x++; //direita
            else x--; //esquerda

            if(x < 0) {//muda direção caso o boss chegue no canto esquerdo da tela
                x = 0;
                direction = true; //direita
            }
            if(x+width > WinDefs.GWIDTH) { //muda de direção caso chegue no canto direito
                x = WinDefs.GWIDTH - width;
                direction = false; //esquerda
            }
            //atualiza tiro
            if(!bossTiro.isEmpty()){
                for(int i = 0; i < bossTiro.size(); i++) bossTiro.get(i).move();
            }
        }
    }
    
    /**
     * cria disparos do boss
     */
    private void createFire(){
        if(bossTiro.size() < 5){
            Tiro t = new Tiro(missiel, x + (width / 2) - 3, y + height);
            t.setOrientacao(true);
            t.setVelocidade(+5);
            bossTiro.add(t);
            shoot.playSound();
        }
    }
    /**
     * causa uma espera para o próximo disparo
     */
    private void waitFire(){
        if(countDelay < 32) countDelay++;
        else{
            countDelay = 0;
            delay = true;
        }
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
    public boolean intersects(int x,int xwidth,int y,int yheight){
        
        if(!bossTiro.isEmpty()){
            for(int i = 0; i < bossTiro.size(); i++){
                if( (bossTiro.get(i).getX() >= x && bossTiro.get(i).getX() <= xwidth)
                    && (bossTiro.get(i).getY() >= y && bossTiro.get(i).getY() <= yheight) ){
                        bossTiro.remove(i);
                        killed.playSound();
                        return true;
                }
            }
        }
        
        return false;
    }

    /**
     * 
     * @param x seta x com novo posição em x da nave
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 
     * @param y seta y com nova posição em y da nave
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 
     * @return posição atual da nave em x
     */
    public int getX() {
        return x;
    }

    /**
     * 
     * @return posição atual da nave em y
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @return largura da nave
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @return altura da nave
     */
    public int getHeight() {
        return height;
    }
    /**
     * decrementa o HP do boss
     */
    public void decBossHeal(){
        bossHeal--;
        
    }
    /**
     * 
     * @return HP do boss
     */
    public int getHP(){
        return bossHeal;
    }
}
