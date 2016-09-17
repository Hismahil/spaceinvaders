
package spaceinvaders.com;

import java.applet.Applet;
import java.applet.AudioClip;
import javax.sound.sampled.Clip;
import spaceinvaders.com.resources.LoadResources;


/**
 *
 * @author hismahil
 */
public class Sound {

    private AudioClip clip; //referencia para o audio clip
    private LoadResources resource;
    
    /**
     * 
     * @param pathFileName nome do arquivo de audio
     * Construtor exemplo http://www.thejavahub.net/
     */
    public Sound(String pathFileName){
        try{
            resource = new LoadResources();
            //carrega audio
            clip = Applet.newAudioClip(resource.resourceSoundLoader(pathFileName));
            
        }
        catch(Exception e){
            System.err.println("Não foi possivel carregar o arquivo de audio!");
        }
    }
    /**
     * Método que toca o audio
     * Método exemplo http://www.thejavahub.net/
     */
    public void playSound(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                if(clip != null) {
                    clip.play();
                }
            }
            
        }).start();
        
    }
    /**
     * Método que para o audio
     * Método exemplo http://www.thejavahub.net/
     */
    public void stopSound(){
        if(clip != null) clip.stop();
    }
    /**
     * Método que mantem o audio em loop
     * Método exemplo http://www.thejavahub.net/
     */
    public void loopSound(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                if(clip != null) {
                    clip.play();
                    clip.loop();
                }
            }
        
        }).start();
        
    }
}
