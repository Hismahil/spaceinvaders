
package spaceinvaders.com;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceinvaders.com.interfaces.WinDefs;
import spaceinvaders.com.resources.LoadResources;


public class Menu {
    
    private BufferedImage back; //imagem de background do menu
    //botões
    private GButton iniciar;
    private GButton continuar;
    private GButton controle;
    private GButton difFacil;
    private GButton difNormal;
    private GButton difDificil;
    private GButton creditos;
    private GButton sair;
    //
    private LoadResources resource = new LoadResources();
    private int menuOption = -1; //opção invalida do menu
    private Sound backgroundEffect; //para o som de fundo do jogo
    private int dificuldade = 0; //nivel de dificuldade
    private Carregando carga; //tela de carregando
    private boolean jogoCriado = false; //jogoCriado do GamePanel 
    /**
     * Construtor que carrega a imagem de fundo
     * instancia os botões e o som de fundo
     */
    public Menu(){
        
        try {
            back = resource.resourceImageLoader("backgroundMenu.png");
            iniciar = new GButton("btnIniciar.png","btnIniciarHover.png", 330, 150, "Botao-2.wav", "Botao-7.wav");
            continuar = new GButton("btnContinueN.png","btnContinueH.png",330, 200, "Botao-2.wav", "Botao-7.wav");
            controle = new GButton("controlN.png","controlH.png", 330, 250, "Botao-2.wav", "Botao-7.wav");
            difFacil = new GButton("btnDifFacilN.png","btnDifFacilH.png", 330, 300, "Botao-2.wav", "Botao-7.wav");
            difNormal = new GButton("btnDifNormalN.png","btnDifNormalH.png", 330, 300, "Botao-2.wav", "Botao-7.wav");
            difDificil = new GButton("btnDifDificilN.png","btnDifDificilH.png", 330, 300, "Botao-2.wav", "Botao-7.wav");
            creditos = new GButton("btnCredito.png","btnCreditoHover.png", 330, 350, "Botao-2.wav", "Botao-7.wav");
            sair = new GButton("btnSair.png","btnSairHover.png", 330, 400, "Botao-2.wav", "Botao-7.wav");
            carga = new Carregando("carregando.png");
            
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundEffect = new Sound("space.wav");
    }
    /**
     * faz a impressão da tela de menu do jogo
     * @param g referência da imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        //se não foi escolhido nada e a imagem de fundo não é null
        if(jogoCriado){
            if(menuOption == -1 && back != null){
                g.drawImage(back, 0, 0, WinDefs.GWIDTH, WinDefs.GHEIGHT, null);
                //desenha os botões
                iniciar.draw(g);
                continuar.draw(g);
                controle.draw(g);
                //desenha botão de dificuldade de acordo com o escolhido
                switch (dificuldade) {
                    case 0:
                        difFacil.draw(g);
                        break;
                    case 1:
                        difNormal.draw(g);
                        break;
                    case 2:
                        difDificil.draw(g);
                }
                creditos.draw(g);
                sair.draw(g);
            }
        }
        else if( menuOption == -1 && back != null) {
            //desenha a imagem de fundo
            g.drawImage(back, 0, 0, WinDefs.GWIDTH, WinDefs.GHEIGHT, null);
            //desenha os botões
            iniciar.draw(g);
            continuar.draw(g);
            controle.draw(g);
            //desenha botão de dificuldade de acordo com o escolhido
            switch(dificuldade){
                case 0: difFacil.draw(g); break;
                case 1: difNormal.draw(g); break;
                case 2: difDificil.draw(g);
            }
            creditos.draw(g);
            sair.draw(g);   
        }
        else{
                carga.draw(g); //desenha tela de carregando
        }
    }
    /**
     * 
     * @param e referência dos eventos capturados pelo GamePanel
     */
    public void mousePressed(MouseEvent e) {
        int x = e.getX();//coordenadas
        int y = e.getY();
        //se a seta do mouse esta dentro das coordenadas do botão
        //e clica
        if( (x >= iniciar.getX()) && (x <= iniciar.getX() + iniciar.getWidth()) && 
                (y >= iniciar.getY()) && (y <= iniciar.getY() + iniciar.getHeight()) ) {
            menuOption = 0; //opção do menu
            iniciar.playSoundClick(); //som do click
            backgroundEffect.loopSound(); //da inicio ao som de fundo do jogo
            
        }
        
        if( jogoCriado && (x >= continuar.getX()) && (x <= continuar.getX() + continuar.getWidth()) && 
                (y >= continuar.getY()) && (y <= continuar.getY() + continuar.getHeight()) ){ 
            menuOption = 1;
            continuar.playSoundClick();
            backgroundEffect.loopSound();
        }
        
        if( (x >= controle.getX()) && (x <= controle.getX() + controle.getWidth()) && 
                (y >= controle.getY()) && (y <= controle.getY() + controle.getHeight()) ) {
            menuOption = 2;
            controle.playSoundClick();
        }
        
        switch(dificuldade){
            case 0:
                //clico no facil
                if( (x >= difFacil.getX()) && (x <= difFacil.getX() + difFacil.getWidth()) && 
                        (y >= difFacil.getY()) && (y <= difFacil.getY() + difFacil.getHeight()) ) {
                    difFacil.playSoundClick();
                    dificuldade = 1; //vai para o normal
                }
                break;
            case 1:
                //clico no normal
                if( (x >= difNormal.getX()) && (x <= difNormal.getX() + difNormal.getWidth()) && 
                        (y >= difNormal.getY()) && (y <= difNormal.getY() + difNormal.getHeight()) ) {
                    difNormal.playSoundClick();
                    dificuldade = 2; //vai para o dificil
                }
                break;
            case 2:
                //clico no dificil
                if( (x >= difDificil.getX()) && (x <= difDificil.getX() + difDificil.getWidth()) && 
                        (y >= difDificil.getY()) && (y <= difDificil.getY() + difDificil.getHeight()) ) {
                    difDificil.playSoundClick();
                    dificuldade = 0; //volta para o facil
                }
        }

        if( (x >= creditos.getX()) && (x <= creditos.getX() + creditos.getWidth()) && 
                (y >= creditos.getY()) && (y <= creditos.getY() + creditos.getHeight()) ) {
            menuOption = 3;
            creditos.playSoundClick();
        }
        
        if( (x >= sair.getX()) && (x <= sair.getX() + sair.getWidth()) && 
                (y >= sair.getY()) && (y <= sair.getY() + sair.getHeight()) ) {
            menuOption = 4;
            sair.playSoundClick();
        }
    }
    /**
     * 
     * @param e referência aos eventos capturados pelo GamePanel
     */
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //se o mouse esta dentro do botão
        if( (x >= iniciar.getX()) && (x <= iniciar.getX() + iniciar.getWidth()) && 
                (y >= iniciar.getY()) && (y <= iniciar.getY() + iniciar.getHeight()) ) {
            iniciar.setStatus(true); //seta HightLight do botão
            //iniciar.playSoundOver();
        }
        else iniciar.setStatus(false); //seta botão Normal
        
        if( (x >= continuar.getX()) && (x <= continuar.getX() + continuar.getWidth()) && 
                (y >= continuar.getY()) && (y <= continuar.getY() + continuar.getHeight()) ) {
            continuar.setStatus(true);
            //continuar.playSoundOver();
        }
        else continuar.setStatus(false);
        
        if( (x >= controle.getX()) && (x <= controle.getX() + controle.getWidth()) && 
                (y >= controle.getY()) && (y <= controle.getY() + controle.getHeight()) ) {
            controle.setStatus(true);
            //controle.playSoundOver();
        }
        else controle.setStatus(false);
        
        switch(dificuldade){
            case 0:
                if( (x >= difFacil.getX()) && (x <= difFacil.getX() + difFacil.getWidth()) && 
                    (y >= difFacil.getY()) && (y <= difFacil.getY() + difFacil.getHeight()) ) {
                        difFacil.setStatus(true);
                        //difFacil.playSoundOver();
                }
                else difFacil.setStatus(false);
                break;
            case 1:
                if( (x >= difNormal.getX()) && (x <= difNormal.getX() + difNormal.getWidth()) && 
                        (y >= difNormal.getY()) && (y <= difNormal.getY() + difNormal.getHeight()) ) {
                    difNormal.setStatus(true);
                    //difNormal.playSoundOver();
                }
                else difNormal.setStatus(false);
                break;
            case 2:
                if( (x >= difDificil.getX()) && (x <= difDificil.getX() + difDificil.getWidth()) && 
                        (y >= difDificil.getY()) && (y <= difDificil.getY() + difDificil.getHeight()) ) {
                    difDificil.setStatus(true);
                    //difDificil.playSoundOver();
                }
                else difDificil.setStatus(false);
        }
        
        if( (x >= creditos.getX()) && (x <= creditos.getX() + creditos.getWidth()) && 
                (y >= creditos.getY()) && (y <= creditos.getY() + creditos.getHeight()) ) {
            creditos.setStatus(true);
            //creditos.playSoundOver();
        }
        else creditos.setStatus(false);
        
        if( (x >= sair.getX()) && (x <= sair.getX() + sair.getWidth()) && 
                (y >= sair.getY()) && (y <= sair.getY() + sair.getHeight()) ) {
            sair.setStatus(true);
            //sair.playSoundOver();
        }
        else sair.setStatus(false);
    }
    /**
     * 
     * @return menuOption
     */
    public int getMenuOption(){
        return menuOption;
    }
    /**
     * Método que reseta a opção do menu
     */
    public void resetMenuOption(){
        menuOption = -1;
    }
    /**
     * Método que para o som de fundo
     */
    public void backGroundSoundStop(){
        backgroundEffect.stopSound();
    }
    /**
     * 
     * @return dificuldade para o GamePanel
     */
    public int getDificuldade(){
        return dificuldade;
    }
    /**
     * 
     * @param jogoCriado seta jogoCriado para ativar o botão continuar
     */
    public void setJogoCriado(boolean jogoCriado){
        this.jogoCriado = jogoCriado;
    }
    /**
     * 
     * @return jogoCriado
     */
    public boolean getJogoCriado(){
        return jogoCriado;
    }
}
