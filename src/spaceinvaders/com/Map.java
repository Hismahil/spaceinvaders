
package spaceinvaders.com;

import java.awt.Graphics;
import java.io.IOException;
import spaceinvaders.com.interfaces.WinDefs;

public class Map {
    
    
    private Tile[][] mapa; //matriz com o mapa
    private Tile buffer; //para cria instancias do mapa
    private int x, y; //coordenadas x y
    
    /**
     * Construtor que cria o mapa
     * @throws IOException 
     */
    public Map() throws IOException{
        mapa = new Tile[75][25]; //define as dimensões
        
        loadMap();//carrega o mapa
        
    }
    /**
     * Método que cria o mapa
     * @throws IOException 
     */
    private void loadMap() throws IOException{
        
        x = 0; //coordenadas
        y = -1600;
        
        for(int i = 0; i < 75; i++){
            for(int j = 0; j < 25; j++){
                int r = (int) (Math.random() * 4); //0 a 3

                switch (r) { //carrega uma tile de forma randomica
                    case 0: 
                        buffer = new Tile("espaço.png");
                        break;
                    case 1:
                        buffer = new Tile("espaço1.png");
                        break;
                    case 2:
                        buffer = new Tile("espaço2.png");
                        break;
                    case 3:
                        buffer = new Tile("espaço3.png");
                        break;

                }
                //se x == a largura da tela
                if (x == WinDefs.GWIDTH) {
                    x = 0; //reseta
                    y += 32; //avança 32 para baixo
                }
                //seta posição do tile
                buffer.setX(x);
                buffer.setY(y);

                mapa[i][j] = buffer; //adiciona referência
                
                x += 32; //avança 32 para frente
            } 
        }
    }
    /**
     * 
     * @param g referência da imagem do Double Buffering do GamePanel
     */
    public void draw(Graphics g){
        
        for(int i = 0; i < 72; i++){
            for(int j = 0; j < 25; j++){
                g.drawImage(mapa[i][j].getTileSet(), 
                        mapa[i][j].getX(), 
                        mapa[i][j].getY(), 
                        null);
            }
        }
    }
    /**
     * Método que movimenta os tiles do mapa
     */
    public void move(){
        
        for(int i = 0; i < 72; i++){
            for(int j = 0; j < 25; j++){
                mapa[i][j].move(); //chama o método interno de cada tile
            }
        }
    }
    
}
