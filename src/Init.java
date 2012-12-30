import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Init {	
	
	public ArrayList<BufferedImage> getArrayList(String name,int x,int y,int row,int col)
	{
		ArrayList<BufferedImage> image = new ArrayList<BufferedImage>();
		BufferedImageLoader loader = new BufferedImageLoader();
	    BufferedImage image_buff = null;   
	    try {
	    	image_buff = loader.loadImage(name);         
	    } catch (IOException ex) {            
	    }
	    SpriteSheet image_sheet = new SpriteSheet(image_buff);
	    
	    int a,b;        
	    for(int t=0;t<row;t++)
	    {
	    	a = x*t;
	    	for(int k=0;k<col;k++)
	    	{
	    		b = y*k;
	    		image.add(image_sheet.grabSprite(b, a, x, y));
	    	}
	    	
	    }
	    
	    return image;
	}
}
