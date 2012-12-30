import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {
    
    private ArrayList<BufferedImage> frames;    
    public BufferedImage sprite; 
    
    public Animator(ArrayList<BufferedImage> frames){
        this.frames = frames;
    } 	    
    
    public void chFrame(int frame){   
    	sprite = frames.get(frame);
    }    
    
    
}