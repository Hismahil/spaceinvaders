
package spaceinvaders.com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JPanel;
import spaceinvaders.com.interfaces.WinDefs;

public class GamePanel extends JPanel implements Runnable{

    private Thread game; //para o thread geral do jogo
    private Image dbImage; //para double buffering
    private Graphics dbGraphics; //para double buffering
    //para jogo em execução, se perdeu, se menu ativo e se esta em jogo
    private boolean running = false, gameOver = false, 
            menuActive = true, emJogo = false, jogoCriado = false, gameWin = false;
    //limite do FPS
    private long periodo = 20; 
    //menu
    private Menu menu = new Menu();
    private int menuOption; //opção do menu
    private Map mapa; //mapa do jogo
    private Player player; //nave do player
    private Creditos creditos; //tela de crêditos
    private FrotaInimiga frota; //frota de naves inimigas
    private Explosao explosao; //explosão
    private SequenciaDeCometas seq; //sequencia de cometas
    private int dificuldade; //nivel de dificuldade
    private int milesimo = 0, segundos = 0, minuto = 0; //para saber se passou 10 segundos
    private Win win; //tela de vitória
    private Lose lose; //tela de derrota
    private Help help; //tela de ajuda
    
    /**
     * construtor que cria o panel de acordo com as dimensões da tela do jogo
     * Construtor exmplo do livro Killer game programming in Java
     * e requesita foco para eventos do teclado
     * @throws IOExcpetion
     */
    public GamePanel() throws IOException{
        
        setDoubleBuffered(false); //sem double buffering automatico
        setBackground(Color.BLACK); //cor de fundo
        setPreferredSize(new Dimension(WinDefs.GWIDTH, WinDefs.GHEIGHT)); //dimensões do painel
        setFocusable(true); //requisita foco
        requestFocus();
        //eventos
        addKeyListener(new Teclado());
        addMouseListener(new Mouse());
        addMouseMotionListener(new Mouse());
        //instâncias
        
        creditos = new Creditos();
        mapa = new Map();
        win = new Win("ganhou.png");
        lose = new Lose("perdeu.png");
        help = new Help();
    }

    @Override
    public void addNotify() {
        super.addNotify(); //informa ao frame que o painel tem foco
        Game(); //inicia o jogo
    }
    /**
     * Método que da partida no thread
     * Método exmplo do livro Killer game programming in Java
     * se o thread é null e o jogo não esta em execução
     */
    public void Game(){
        if(game == null || !running){
            game = new Thread(this); //cria o thread
            game.start(); //da partida
            running = true; //jogo em execução
        }
    }
    /**
     * para o jogo
     * Método exmplo do livro Killer game programming in Java
     */
    public void gameStop(){
        running = false;
    }
    /**
     * renderiza o a imagem do jogo em segundo plano
     * Método exmplo do livro Killer game programming in Java
     * @throws IOException
     */
    public void gameRender() throws IOException{
        if( dbImage == null){
            //pega estado atual da tela 
            dbImage = createImage(WinDefs.GWIDTH, WinDefs.GHEIGHT);
            //se for null é porque deu erro na criação do frame
            if(dbImage == null) {
                System.out.println("Erro na imagem do video");
                return;
            }
            else{//se não atribui o grafico da imagem da tela para
                //o segundo buffer
                dbGraphics = dbImage.getGraphics();
            }
        }
        //limpa a tela
        dbGraphics.setColor(Color.BLACK);
        dbGraphics.fillRect(0, 0, WinDefs.GWIDTH, WinDefs.GHEIGHT);
        //cria imagem do jogo em segundo plano
        draw(dbGraphics);
    }
    /**
     * Método que cria a imagem do jogo 
     * Método exmplo do livro Killer game programming in Java
     * @param g referência a imagem do Double Buffering criado no método gameRender()
     * @throws IOException 
     */
    public void draw(Graphics g) throws IOException{
        if(menuActive) {//se menu esta ativo
            
            menu.draw(g); //desenha menu
            menuOption = menu.getMenuOption();//pega opcao
            dificuldade = menu.getDificuldade(); //pega nivel de dificuldade
            menu.resetMenuOption();//reseta escolha dentro da classe menu
            
            //necessário para manter o menu na tela, se não fica uma tela preta
            if(menuOption == 1 && !menu.getJogoCriado()) {
                menuActive = true;
                menu.backGroundSoundStop();
            }
            else if(menuOption != -1) menuActive = false; //se escolheu uma das opções desativa menu
        }
        else{
            switch(menuOption){ //verifica opção do menu
                case 0: //começar novo jogo
                    gameStart();
                    menu.draw(g); //para imprimir a tela de carregando
                    //cria novo jogo de acordo com a dificuldade
                    if (!jogoCriado) {
                        switch (dificuldade) {
                            case 0:
                                
                                novoJogoFacil();
                                break;
                            case 1:

                                novoJogoNormal();
                                break;
                            case 2:

                                novoJogoDificil();
                                break;
                        }
                        jogoCriado = true;
                        menuOption = 1;
                        menu.setJogoCriado(jogoCriado);
                    }
                    
                    break;
                case 1: //continuar jogo
                    
                    if(!gameOver && !gameWin){
                        if(jogoCriado){

                            gameContinue();

                            if(emJogo){
                                //desenha novo estado de cada elemento do jogo no gráfico
                                //em segundo plano
                                mapa.draw(g);
                                
                                seq.draw(g);
                                
                                if(explosao != null && !explosao.getIter()) explosao.draw(g);
                                else explosao = null;
                                player.draw(g);
                                
                                frota.draw(g);

                                //se o jogador atiro e o disparo foi desativado
                                if( player.getTiro() != null && !player.getDisparo()){
                                    player.getTiro().draw(g); //desenha o tiro na tela
                                }
                                //cria explosão do jogador
                                if(player.isMorto() && player.getFrameExplosion() == player.getFrameExplosionTotal()) gameOver();
                            }
                        }
                    }
                    else{
                        if(gameOver){
                            //tela de game over
                            lose.draw(g);
                        }
                        
                        if(gameWin){
                            //tela de congrutalações
                            win.draw(g);
                            
                        }
                    }
                    break;
                case 2: //controle
                    help.draw(g);
                    break;
                case 3: //credito
                    creditos.draw(g);
                    break;
                case 4: gameStop();
                    
            }
        }
    }
    /**
     * Método que atualiza o movimento dos elementos do jogo
     * Método exmplo do livro Killer game programming in Java
     * @throws IOException
     */
    public void gameUpdate() throws IOException{
        //se o jogo no geral esta em execução (menu e jogo) e o thread
        //não é null
        if( running && game != null && emJogo){
            
            mapa.move(); //movimenta o mapa
            
            if(player != null) player.move(); //atualiza posição do jogador
            
            //verifica colisão do tiro do jogador com as naves inimigas
            if( frota != null && !frota.getFrota().isEmpty() ){
                
                for(int i = 0; i < frota.getFrota().size(); i++){
                    int dados[] = frota.getLocationEnemy(i); //pegas os dados
                    //verifica
                    if( player.intersects(dados[0], dados[1], dados[2], dados[3])){
                        //cria animação de explosão na posição onde a nave foi removida
                        explosao = new Explosao("explosao32.png",dados[0],dados[2], 32, 32, 12);
                        frota.getFrota().remove(i);
                    }
                }
                
            }
            else{
                //quando elimina todas as naves e não existe boss
                if(frota.getBoss() == null){
                    gameWin();
                }
            }
            
            if(frota != null && frota.getBoss() != null && frota.getBoss().getHP() > 0){
                /*
                 * verifica se o boss foi atingido pelo tiro do player
                 * se for decrementa o HP do boss
                 * se o HP atingir zero o boss não é mais desenhado na tela
                 */
                if(player.intersects(frota.getBoss().getX(), frota.getBoss().getX() + frota.getBoss().getWidth(), 
                        frota.getBoss().getY(), frota.getBoss().getY() + frota.getBoss().getHeight() ) ) 
                    frota.getBoss().decBossHeal();
            }
            else{
                if(frota.getFrota().isEmpty()){ //se a frota esta vasia e o boss foi eliminado
                    gameWin();
                }
            }
            
            //move os tiros e dependendo do nivel de dificuldade move as naves
            if(frota != null) {
                //seta movimento se segundos não for zero e segundos mod 10 for zero
                if(segundos != 0 && segundos % 10 == 0) frota.setDezSegundos(true);
                frota.move(); //move as naves e os tiros se for dificuldade normal
            }
            
            //verifica colisão dos tiros das naves inimigas contra o jogador
            if(frota != null && frota.intersects(player.getX(), player.getX() + player.getWidth(),
               player.getY(), player.getY() + player.getHeight() ) && !player.isMorto()){
                //termina o jogo
                player.setKilled(true);
                
            } 
            
            
            //se sequencia de cometas não é null
            if(seq != null){
                //se passo 10 segundos faz criar novo cometa
                if(milesimo != 0 && milesimo % 10 == 0
                   && segundos != 0 && segundos % 10 == 0) seq.dezSecs(true);
                seq.move(); //move
                
            }
            
        }
    }
    /**
     * Método que coloca a nova imagem do jogo gerada em segundo plano
     * na tela
     * Método exemplo do livro Killer game programming in Java
     */
    public void gamePaint(){
        Graphics g;
        try{
            g = this.getGraphics(); //pega a imagem atual da tela
            //se a imagem pega no gameRender não é null
            //e a instância g não é null
            if(dbImage != null && g != null){ 
                g.drawImage(dbImage, 0, 0, null); //coloca imagem renderizada em segundo plano na tela
            }
            Toolkit.getDefaultToolkit().sync(); //For some operating systems
            g.dispose(); //descarta referencia
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    /**
     * Método que seta jogoCriado com false;
     * Método exemplo do livro Killer game programming in Java
     */
    public void gameStart(){
        
        jogoCriado = false;
    }
    /**
     * Método que seta emJogo como true 
     * ou seja, o jogo esta em execução
     */
    public void gameContinue(){
        emJogo = true;
    }
    /**
     * Método que instancia player, frota e sequencia de cometas
     * seta jogoCriado como true (ainda não esta em execução o jogo)
     * seta gameOver com false (ninguem perdeu)
     * seta gameWin com false (ninguem ganhou)
     * @throws IOException 
     */
    public void novoJogoFacil() throws IOException{
        
       
        player = new Player("Player.png");
        frota = new FrotaInimiga();
        frota.loadEnemysFacil();
        
        seq = new SequenciaDeCometas("Meteoro.png", 32, 128, 6);
        jogoCriado = true;
        gameOver = false;
        gameWin = false;
    }
    /**
     * Método que instancia player, frota e sequencia de cometas
     * seta jogoCriado como true (ainda não esta em execução o jogo)
     * seta gameOver com false (ninguem perdeu)
     * seta gameWin com false (ninguem ganhou)
     * @throws IOException 
     */
    public void novoJogoNormal() throws IOException{
        //mapa = new Map();
        player = new Player("Player.png");
        frota = new FrotaInimiga();
        frota.loadEnemysNormal();
        
        seq = new SequenciaDeCometas("Meteoro.png", 32, 128, 6);
        jogoCriado = true;
        gameOver = false;
        gameWin = false;
    }
    /**
     * Método que instancia player, frota e sequencia de cometas
     * seta jogoCriado como true (ainda não esta em execução o jogo)
     * seta gameOver com false (ninguem perdeu)
     * seta gameWin com false (ninguem ganhou)
     * @throws IOException 
     */
    public void novoJogoDificil() throws IOException{
        //mapa = new Map();
        player = new Player("Player.png");
        frota = new FrotaInimiga();
        frota.loadEnemysDificil();
        
        seq = new SequenciaDeCometas("Meteoro.png", 32, 128, 6);
        jogoCriado = true;
        gameOver = false;
        gameWin = false;
    }
    /**
     * Método que termina o jogo
     * seta as instancias player, frota e seq com null para remover da memória o jogo
     * seta jogoCriado com false
     * seta emJogo com false
     * seta gameOver com true
     * seta menu para informar que o jogo não existe mais com false
     * para o som de fundo
     */
    public void gameOver(){
        player = null;
        frota = null;
        seq = null;
        jogoCriado = false;
        emJogo = false;
        gameOver = true;
        menu.setJogoCriado(false);
        menu.backGroundSoundStop();
    }
    /**
     * Método que termina o jogo
     * seta as instancias player, frota e seq com null para remover da memória o jogo
     * seta jogoCriado com false
     * seta emJogo com false
     * seta gameWin com true
     * seta menu para informar que o jogo não existe mais com false
     * para o som de fundo
     */
    public void gameWin(){
        player = null;
        frota = null;
        seq = null;
        jogoCriado = false;
        emJogo = false;
        gameWin = true;
        menu.setJogoCriado(false);
        menu.backGroundSoundStop();
    }
    /**
     * Método run Thread geral
     * Método exemplo de http://www.thejavahub.net/ e 
     * http://www.youtube.com/user/TheJavaHub?feature=g-user-lik
     * que também é uma cópia do livro do Killer game programming in Java
     */
    
    @Override
    public void run() {
        
        long antes, depois = 0, diferenca, tempoDeEspera;
        
        
        antes = System.currentTimeMillis(); //pega o tempo antes de entrar no loop infinito
        
        while (running) { //enquanto running for true

            try {
                gameUpdate();
                gameRender();
            } catch (IOException ex) {
                System.err.println("Erro no rendering!");
            }
            gamePaint();
            //verifica quanto tempo se passou depois de executar os métodos
            //gameUpdate() gameRender() e gamePaint()
            diferenca = System.currentTimeMillis() - depois; 
            //verifica a diferença com o periodo que é definido no inicio da classe
            tempoDeEspera = periodo - diferenca;
            
            if (tempoDeEspera <= 0) {//se acontecer
                tempoDeEspera = 5; //aqui vai uns 200 fps
            }
            try {
                game.sleep(tempoDeEspera); //causa uma espera entre uma reimpressão da tela e outra
            } catch (Exception e) {
            }

            depois = System.currentTimeMillis();
            //faz uma contagem de FPS para setar um segundo
            if( milesimo < tempoDeEspera ) milesimo++;
            else {
                milesimo = 0;
                if( segundos < 60 ) segundos++;
                else{
                    segundos = 0;
                }
            }
            //System.out.println("Milesimo: "+milesimo+" Segundos: "+segundos);
            System.out.println("Antes: " + antes + " Diferenca: " 
                    + diferenca + " Tempo de espera: " 
                    + tempoDeEspera + " Depois: " + depois
                    + " FPS: "+ 1000 / tempoDeEspera);
        }
        
        System.exit(0); //fecha o jogo
    }

    /**
     * Classe interna que verifica eventos do mouse
     */
    private class Mouse implements MouseListener, MouseMotionListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            menu.mousePressed(e); //para o menu
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            menu.mouseMoved(e); //eventos para o menu
        }
        
    }
    /**
     * classe interna que implementa eventos do teclado
     */
    private class Teclado implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode(); //verifica que tecla foi presionada
            
            if( code == KeyEvent.VK_ESCAPE ) {
                menuActive = true; //ativa menu
                emJogo = false;
                menuOption = -1;
                menu.backGroundSoundStop();
            }
            else{//se não passa o evento para a instância do jogador
                if(player != null) player.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if( player != null ) player.keyReleased(e); //repassa eventos para a instância do jogador
        }
        
    }
}
