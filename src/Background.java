import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Background {
	private Animator bg;
	private Animator tr_colors;
	private int width;
	private int height;
	private long timer=0;
	private int min=0;
	private int sec=0;
	

	private int infoband = 30; //если придётся менять, и чтобы не было вопросов откуда 30...
	
	public Background()
	{
		Init init_png = new Init();		
		width = 190;
		height = 126;
		bg = new Animator(init_png.getArrayList("grass.png", width, height, 1, 1));
		tr_colors = new Animator(init_png.getArrayList("tr_colors.png", 1, 1, 1, 3));
	}
	
	public void drawBG(Graphics g,int w,int h, long time)
	{
		bg.chFrame(0);		
		for(int i=infoband;i<h;i+=height/2)
		{
			for(int j=0;j<w;j+=width/2)
			{
				g.drawImage(bg.sprite, j, i, width/2, height/2, null);
			}
		}
		g.setColor(Color.black);
		
		//Поля (красное(2) - база, зеленое(0) - спавн, желтое(1) - поле боя)
		tr_colors.chFrame(0);
		g.drawImage(tr_colors.sprite,100,300,40,120,null);
		g.drawImage(tr_colors.sprite,860,300,40,120,null);		
		tr_colors.chFrame(1);
		g.drawImage(tr_colors.sprite,140,300,720,120,null);		
		tr_colors.chFrame(2);
		g.drawImage(tr_colors.sprite,0,280,100,160,null);	
		g.drawImage(tr_colors.sprite,900,280,1000,160,null);	
		
		//Надписи
		g.setColor(Color.white);
		Font font = new Font("Verdana", Font.BOLD,14);
		g.setFont(font);
		g.drawString("Поле боя", 440, 280);
		g.drawString("База", 20, 260);
				
		g.drawRect(0, 0, w, 29);
		g.setColor(Color.black);
		g.drawString("gold = "+ String.valueOf(Game.gold), 50, 20);
		
		//Время
		if(timer+1000 <= time)
		{
			timer=time;
			sec++;
			if(sec>=60)
			{
				sec=0;
				min++;
			}
		}
		g.drawString(String.valueOf(min) + " : " + String.valueOf(sec), 400, 20);
		
		g.setColor(Color.white);
		
		font = new Font("Verdana", Font.BOLD,9);
		g.setFont(font);
		g.drawString("SPAWN", 100, 270);
		
		
				
		g.setColor(Color.black);
		// Надписи координат
		font = new Font("Verdana", 0,8);
		g.setFont(font);
		for(int i=0;i<w;i+=20)
			g.drawString(String.valueOf(i), i, 20+ infoband);
		for(int i=40;i<h;i+=20)
			g.drawString(String.valueOf(i), 0, i+ infoband);
		
		/* Сетка координат
		for(int i=0;i<w;i+=20)
		{
			for(int j=0;j<h;j+=20)
			{
				
				g.drawLine(i, j, i, j+20);
				g.drawLine(i, j, i+20, j);
				
			}
			
		}
		*/
		
	}	
}
