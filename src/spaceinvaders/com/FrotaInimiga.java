
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
public class FrotaInimiga {
    
    private ArrayList<Enemy> frota = new ArrayList<Enemy>(); //para a sequencia de naves
    private Enemy enemy; //para criar os objetos e por na frota
    private ArrayList<Tiro> tiro = new ArrayList<Tiro>(); //sequencia de tiros
    private Tiro t; //para criar os objetos e por no tiro
    private BufferedImage bullet; //imagem do tiro
    private LoadResources resource = new LoadResources();
    private boolean[] disparo = new boolean[5]; //numero maximo de disparos
    private boolean movimento = true, dezsegundos = false, left = true, right = false; //dados gerais
    private int sequencia[] = new int[5];
    private int numDisparos = 0;
    private Sound shoot; //som do tiro
    private Sound playerKilled; //som do player morrendo
    private int dificuldade, atualiza = 0, mv = 0;
    private Boss boss; //boss
    /**
     * Construtor que carrega a imagem dos tiros
     * seta sequencia de disparos[] como true
     * carrega os sons
     * @throws IOException 
     */
    public FrotaInimiga() throws IOException{
        
        bullet = resource.resourceImageLoader("bullet2.png");
        
        for(int i = 0; i < 5; i++) {
            disparo[i] = true;
            sequencia[i] = -1;
        }
        
        shoot = new Sound("shoot.wav");
        playerKilled = new Sound("explosion.wav");
        
        resource = null;
    }
    /**
     * Método que cria sequencia de naves inimigas
     * Modo facil
     * @throws IOException 
     */
    public void loadEnemysFacil() throws IOException{
        int x = 32 , y = 32; //posição x e y onde começará a por as naves na tela
        dificuldade = 0; //nivel de dificuldade
        
        //primeira fileira de naves
        //se chegar no limite da tela imprime na linha de baixo
        for(int i = 0; i < 90; i ++){
            
            if( (enemy != null) && (enemy.getX() + enemy.getWidth()) >= (WinDefs.GWIDTH - 50) ) {
                x = 32; //reseta x
                y += 48; //pula y pixels para separa cada sequencia de naves
            }
            
            enemy = new Enemy("Enemy.png");
            enemy.setX(x);
            enemy.setY(y);
            
            frota.add(enemy);
            
            x += 50; //incrementa x de 74 em 74
        }
    }
    /**
     * Método que cria sequencia de naves
     * Modo Normal
     * @throws IOException 
     */
    public void loadEnemysNormal() throws IOException{
        int x = 32 , y = 32; //posição x e y onde começará a por as naves na tela
        
        dificuldade = 1; //nivel de dificuldade
        
        //primeira fileira de naves
        //se chegar no limite da tela imprime na linha de baixo
        for(int i = 0; i < 48; i ++){
            
            if( (enemy != null) && (enemy.getX() + enemy.getWidth()) >= (WinDefs.GWIDTH - 64) ) {
                x = 32; //reseta x
                y += 64; //pula y pixels para separa cada sequencia de naves
            }
            
            enemy = new Enemy("Enemy.png");
            enemy.setX(x);
            enemy.setY(y);
            
            frota.add(enemy);
            
            x += 64; //incrementa x de 128 em 128
        }
    }
    
    public void loadEnemysDificil() throws IOException{
        int x = 32 , y = 96; //posição x e y onde começará a por as naves na tela

        dificuldade = 2; //nivel de dificuldade
        
        //cria o boss
        boss = new Boss("boss.png", WinDefs.GWIDTH / 2, 0);
        
        //primeira fileira de naves
        //se chegar no limite da tela imprime na linha de baixo
        for(int i = 0; i < 48; i ++){
            
            if( (enemy != null) && (enemy.getX() + enemy.getWidth()) >= (WinDefs.GWIDTH - 64) ) {
                x = 32; //reseta x
                y += 64; //pula y pixels para separa cada sequencia de naves
            }
            
            enemy = new Enemy("Enemy.png");
            enemy.setX(x);
            enemy.setY(y);
            
            frota.add(enemy);
            
            x += 64; //incrementa x de 74 em 74
        }
    }
    /**
     * 
     * @param g referência a imagem do Double Buffering do GamePanel
     * @throws IOException 
     */
    public void draw(Graphics g) throws IOException{
        
        switch(dificuldade){ //pelo nivel de dificuldade 
            case 0://facil
                if(!frota.isEmpty()){//se não eliminou todas as naves
                    //redesenha
                    for(int i = 0; i < frota.size(); i++){
                        g.drawImage(frota.get(i).getEnemy(), frota.get(i).getX(), frota.get(i).getY(), null);
                    }
                    //cria tiros
                    tiroInimigo();
                    //verifica se não esta vasio
                    if( !tiro.isEmpty() ){
                        for(int i = 0; i < tiro.size(); i++){
                            if(tiro.get(i).getY() >= WinDefs.GHEIGHT){ //se saiu fora da tela
                                tiro.remove(i); //remove
                                disparo[i] = true; //libera novo disparo
                            }
                            else{
                                tiro.get(i).draw(g); //se não redesenha
                            }
                        }
                    }
                }
                break;
            case 1: //Normal
                if(!frota.isEmpty()){

                    for(int i = 0; i < frota.size(); i++){
                        g.drawImage(frota.get(i).getEnemy(), frota.get(i).getX(), frota.get(i).getY(), null);
                    }

                    tiroInimigo();

                    if( !tiro.isEmpty() ){
                        for(int i = 0; i < tiro.size(); i++){
                            if(tiro.get(i).getY() >= WinDefs.GHEIGHT){
                                tiro.remove(i);
                                disparo[i] = true;
                            }
                            else{
                                tiro.get(i).draw(g);
                            }
                        }
                    }
                }
                break;
            case 2: //Dificil
                if(boss != null) boss.draw(g); //redesenha o boss
                
                if(!frota.isEmpty()){

                    for(int i = 0; i < frota.size(); i++){
                        g.drawImage(frota.get(i).getEnemy(), frota.get(i).getX(), frota.get(i).getY(), null);
                    }

                    tiroInimigo();

                    if( !tiro.isEmpty() ){
                        for(int i = 0; i < tiro.size(); i++){
                            if(tiro.get(i).getY() >= WinDefs.GHEIGHT){
                                tiro.remove(i);
                                disparo[i] = true;
                            }
                            else{
                                tiro.get(i).draw(g);
                            }
                        }
                    }
                }
                break;
        }
                
    }
    /**
     * Método que atualiza o movimento da sequencia de naves e do boss
     */
    public void move(){
        
        switch(dificuldade){ //pela dificuldade
            case 0: //facil 
                if(!tiro.isEmpty()){
                    for(int i = 0; i < tiro.size(); i++){
                        tiro.get(i).move(); //movimenta só os tiros
                    }
                }
                break;
            case 1://Normal
                
                if(!tiro.isEmpty()){ //movimenta os tiros
                    for(int i = 0; i < tiro.size(); i++){
                        tiro.get(i).move();
                    }
                }
                //verifica se passou 10 segundos para movimentar
                //as naves
                if(dezsegundos){
                    if(left){ //movimenta para a esquerda
                        mv++;
                        //se a frota não esta vasia
                        if(!frota.isEmpty() && atualiza < 32){
                            if (movimento) { //se movimento é true
                                for (int i = 0; i < frota.size(); i++) {
                                    frota.get(i).setDX(-1); //atualiza
                                    frota.get(i).move();
                                }
                                atualiza++;//atualiza
                            } else { //se nao volta 
                                for (int i = 0; i < frota.size(); i++) {
                                    frota.get(i).setDX(+1);
                                    frota.get(i).move();
                                }

                                atualiza++;//atualiza
                            }

                        }
                        else{
                            
                            atualiza = 0;
                            if(movimento) movimento = false;
                            else movimento = true;
                            if(mv == 66) { //a cada 66 atualizações muda a direção
                                left = false;
                                right = true; //vai pela direita
                                mv = 0;
                            }
                            dezsegundos = false;
                        }
                    }
                    
                    if(right){//movimenta para a direita
                        mv++;
                        //se a frota não esta vasia
                        if(!frota.isEmpty() && atualiza < 32){
                            if (movimento) { //se movimento é true
                                for (int i = 0; i < frota.size(); i++) {
                                    frota.get(i).setDX(+1); //atualiza
                                    frota.get(i).move();
                                }
                                atualiza++;//atualiza
                            } else { //se nao volta 
                                for (int i = 0; i < frota.size(); i++) {
                                    frota.get(i).setDX(-1);
                                    frota.get(i).move();
                                }

                                atualiza++;//atualiza
                            }

                        }
                        else{
                            atualiza = 0;
                            if(movimento) movimento = false;
                            else movimento = true;
                            if(mv == 66) { //a cada 66 atualizações 
                                //muda a direção do movimento
                                right = false; 
                                left = true; //atualiza pela esquerda
                                mv = 0;
                            }
                            dezsegundos = false;
                        }
                    }
                }
                break;
            case 2://Dificil
                //move o boss
                if(boss != null) boss.move();
                //move a frota
                if(!frota.isEmpty()){ //se a frota não esta vasia
                    //move os tiros
                    if(!tiro.isEmpty()){
                        for(int i = 0; i < tiro.size(); i++){
                            tiro.get(i).move();
                        }
                    }
                    //verifica se passou 10 segundos para movimentar
                    //as naves
                    if(dezsegundos){
                        if(left){ //movimenta para a esquerda
                            mv++;
                            //se a frota não esta vasia
                            if(!frota.isEmpty() && atualiza < 32){
                                if (movimento) { //se movimento é true
                                    for (int i = 0; i < frota.size(); i++) {
                                        frota.get(i).setDX(-1); //atualiza
                                        frota.get(i).move();
                                    }
                                    atualiza++;//atualiza
                                } else { //se nao volta 
                                    for (int i = 0; i < frota.size(); i++) {
                                        frota.get(i).setDX(+1);
                                        frota.get(i).move();
                                    }

                                    atualiza++;//atualiza
                                }

                            }
                            else{

                                atualiza = 0;
                                if(movimento) movimento = false;
                                else movimento = true;
                                if(mv == 66) { //a cada 66 atualizações muda a direção
                                    left = false;
                                    right = true; //vai pela direita
                                    mv = 0;
                                }
                                dezsegundos = false;
                            }
                        }

                        if(right){//movimenta para a direita
                            mv++;
                            //se a frota não esta vasia
                            if(!frota.isEmpty() && atualiza < 32){
                                if (movimento) { //se movimento é true
                                    for (int i = 0; i < frota.size(); i++) {
                                        frota.get(i).setDX(+1); //atualiza
                                        frota.get(i).move();
                                    }
                                    atualiza++;//atualiza
                                } else { //se nao volta 
                                    for (int i = 0; i < frota.size(); i++) {
                                        frota.get(i).setDX(-1);
                                        frota.get(i).move();
                                    }

                                    atualiza++;//atualiza
                                }

                            }
                            else{
                                atualiza = 0;
                                if(movimento) movimento = false;
                                else movimento = true;
                                if(mv == 66) { //a cada 66 atualizações 
                                    //muda a direção do movimento
                                    right = false; 
                                    left = true; //atualiza pela esquerda
                                    mv = 0;
                                }
                                dezsegundos = false;
                            }
                        }
                    }
                }
                break;
        }
                
    }
    /**
     * enquanto a frota não for eliminada cria disparo
     * sequencia de 5 disparos randomicos, podendo repetir
     * e registra essa sequencia
     */
    public void tiroInimigo(){
        
        if( !frota.isEmpty() ){//verifica primeiro se existe alguma nave
            
            if(disparo[0]){ //primeiro disparo
                int r = (int)(Math.random()*frota.size());
                
                if(r == frota.size()) r--; //para evitar erro
                //cria disparo e coloca no arraylist
                if( r >= 0 && r < frota.size() ){
                    t = new Tiro(bullet,frota.get(r).getX()+
                            (frota.get(r).getWidth() / 2) + 3,
                            frota.get(r).getY() + frota.get(r).getHeight());
                    t.setOrientacao(true);
                    t.setVelocidade(+5);
                    tiro.add(t);
                }
                //impede novo disparo
                disparo[0] = false;
                sequencia[0] = r;//registra
                shoot.playSound();
            }
            
            if(disparo[1]){
                int r = (int)(Math.random()*frota.size());
                
                if(r == frota.size()) r--;
                
                if( r >= 0 && r < frota.size() ){
                    t = new Tiro(bullet,frota.get(r).getX()+
                            (frota.get(r).getWidth() / 2) + 3,
                            frota.get(r).getY() + frota.get(r).getHeight());
                    t.setOrientacao(true);
                    t.setVelocidade(+5);
                    tiro.add(t);
                }
                
                disparo[1] = false;
                sequencia[1] = r;
                shoot.playSound();
            }
            
            if(disparo[2]){
                int r = (int)(Math.random()*frota.size());
                
                if(r == frota.size()) r--;
                
                if( r >= 0 && r < frota.size() ){
                    t = new Tiro(bullet,frota.get(r).getX()+
                            (frota.get(r).getWidth() / 2) + 3,
                            frota.get(r).getY() + frota.get(r).getHeight());
                    t.setOrientacao(true);
                    t.setVelocidade(+5);
                    tiro.add(t);
                }
                
                disparo[2] = false;
                sequencia[2] = r;
                shoot.playSound();
            }
            
            if(disparo[3]){
                int r = (int)(Math.random()*frota.size());
                
                if(r == frota.size()) r--;
                
                if( r >= 0 && r < frota.size() ){
                    t = new Tiro(bullet,frota.get(r).getX()+
                            (frota.get(r).getWidth() / 2) + 3,
                            frota.get(r).getY() + frota.get(r).getHeight());
                    t.setOrientacao(true);
                    t.setVelocidade(+5);
                    tiro.add(t);
                }
                
                disparo[3] = false;
                sequencia[3] = r;
                shoot.playSound();
            }
            
            if(disparo[4]){
                int r = (int)(Math.random()*frota.size());
                
                if(r == frota.size()) r--;
                
                if( r >= 0 && r < frota.size() ){
                    t = new Tiro(bullet,frota.get(r).getX()+
                            (frota.get(r).getWidth() / 2) + 3,
                            frota.get(r).getY() + frota.get(r).getHeight());
                    t.setOrientacao(true);
                    t.setVelocidade(+5);
                    tiro.add(t);
                }
                
                disparo[4] = false;
                sequencia[4] = r;
                shoot.playSound();
            }
        }
    }
    /**
     * 
     * @return retorna arraylist da sequencia de naves
     */
    public ArrayList getFrota(){
        return frota;
    }
    
    /**
     * 
     * @param x coordenada
     * @param xwidth coordenada x + largura de cada nave
     * @param y coordenada
     * @param yheight coordenda y + altura de cada nave
     * @return true se atingiu
     * @return false se não atingiu
     */
    public boolean intersects(int x,int xwidth,int y,int yheight){
        
        if( !tiro.isEmpty() ) {
            for(int i = 0; i < tiro.size(); i++){
                if( (tiro.get(i).getX() >= x && tiro.get(i).getX() <= xwidth)
                  && (tiro.get(i).getY() >= y && tiro.get(i).getY() <= yheight) ) {
                    tiro.remove(i);
                    disparo[i] = true;
                    playerKilled.playSound();
                    return true;
                }
            }
        }
        //verifica se o boss acertou o player
        if(boss != null && boss.intersects(x, xwidth, y, yheight)){
            return true;
        }
        
        return false;
    }
    /**
     * 
     * @param i indice da nave
     * @return retorna dados 
     */
    public int[] getLocationEnemy(int i){
        
        int dados[]={frota.get(i).getX(),
            frota.get(i).getX() + frota.get(i).getWidth(),
            frota.get(i).getY(),
            frota.get(i).getY() + frota.get(i).getHeight()
        };
        
        return dados;
    }
    /**
     * 
     * @param dezsegundos seta se passo 10s 
     */
    public void setDezSegundos(boolean dezsegundos){
        this.dezsegundos = dezsegundos;
    }
    /**
     * referência para o boss
     * @return boss
     */
    public Boss getBoss(){
        return boss;
    }
}
