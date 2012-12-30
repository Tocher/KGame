/**
 * 
 */

/**
 * @author Александр
 *
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Build {
	private int x;			//Координаты х у в данный момент
	private int y;
	private int hp;			//Текущие хп
	public int type; 		//Тип это военное, научное, тд.
	private int frame;
	private int fraction;	//Чьё здание (пока будут только 2 фракции (1 и 2)
	private boolean mirror;	//Зеркальное отражение
	public int state=0;
	//public boolean target;
	public int coolDown = 0;
	
	public long previousTime = 0;
			
	public Build(int hp, int type, int fraction,int x,int y)
	{
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.type = type;
		this.frame = 0;
		this.fraction = fraction;
	}
	
	/*public void update(long time, BuildList box, Graphics g,ArrayList<Build> Builds,int i)
	{
		if(time - previousTime >= 100){    		
		/*
			if(mirror)
			{
				if(x<100)
					Units.remove(i);
			}
			else
				if(x>860)
					Units.remove(i);
			
			if(hp>0)
			{
				machine(Builds,box);
				getFrame(g,box);
			}
			else
			{			
				Units.remove(i);
			}
				
			
			previousTime = time;
    	}
		box.img.chFrame(frame);
		draw(g,box);
		
	}*/
	
	
	/*private void getFrame(Graphics g,UnitList box)
	{
		if(coolDown<1)
			frame++;	
		else
		{
			coolDown--;
			return;
		}
		switch(action)
		{
		//направо
		case 0: //Атака вправо
			mirror = false;
			if(frame<box.frame_attack_right_start)
				frame=box.frame_attack_right_start;
			if(frame>box.frame_attack_right_finish)
			{
				frame=box.frame_attack_right_start;
				coolDown = 1;
			}
				
			break;		
		case 1: //Бег вправо
			mirror = false;
			if(frame<box.frame_run_right_start||frame>box.frame_run_right_finish)
				frame=box.frame_run_right_start;
			break;		
		case 2: //Смотрим вправо
			mirror = false;
			if(frame<box.frame_stay_right_start||frame>box.frame_stay_right_finish)
				frame=box.frame_stay_right_start;
			break;
		
		//налево		
		case 9: //Атака влево
			mirror = true;
			if(frame<box.frame_attack_right_start)
				frame=box.frame_attack_right_start;		
			if(frame>box.frame_attack_right_finish)
			{
				frame=box.frame_attack_right_start;
				coolDown = 1;
			}
			break;		
		case 10: //Бег влево
			mirror = true;
			if(frame<box.frame_run_right_start||frame>box.frame_run_right_finish)
				frame=box.frame_run_right_start;			
			break;		
		case 11: //Смотрим влево
			mirror = true;
			if(frame<box.frame_stay_right_start||frame>box.frame_stay_right_finish)
				frame=box.frame_stay_right_start;			
			break;
		}
		box.img.chFrame(frame);
	}*/
	
	public void draw(Graphics g,BuildList box)
	{
		box.img.chFrame(frame);
		//hpBar(g,x,y,reSize(box.w/2,box.size),1,this.hp,box.size);
		//int x = this.x;		
			//x+=reSize(box.w,box.size)/2;
			g.drawImage(box.img.sprite, x, y, reSize(box.w,box.size), reSize(box.h,box.size), null);
	}
	
	public int reSize(int a, int size)
	{
		return a*size/100;
	}
	
	public void hpBar(Graphics g,int x,int y,int w,int h,int hp,int size)
	{
		if(hp!=100)
		{
			// Рамка
			g.setColor(Color.black);
			g.fillRect(x-1-w/2, y-h-1, w+2, h+2); 
			// Жизни
			g.setColor(Color.green);
			g.fillRect(x-w/2, y-h, w*hp/100, h); 
		}
	}
	
	/*public void machine(ArrayList<Build> Builds,BuildList box)
	{
		target = false;
		if(fraction == 1)
			action = 1;
		else
			action = 10;
		state = 0;
		for(int i=0;i<Units.size();i++)
		{
			if(Units.get(i).fraction != fraction)
			{
				if(fraction == 1)
				{
					if((x+box.w/2+box.range) >= Units.get(i).x)
					{
						if(y == Units.get(i).y)
						{
							action = 0;
							state = 1;
							if(frame==box.frame_attack_right_finish&&!target)
							{
								Units.get(i).hp -= box.getDmg();
								target = true;
							}
						}
					}
				}
				if(fraction == 2)
				{
					if((x-box.w/2-box.range) <= Units.get(i).x)
					{
						if(y == Units.get(i).y)
						{
							action = 9;
							state = 1;
							if(frame==box.frame_attack_right_finish&&!target)
							{
								Units.get(i).hp -= box.getDmg();
								target = true;
							}
						}
					}
				}
			}			
		}	
		if(state == 0)
		{
			if(fraction == 1)
				x+=box.ms;
			else
				x-=box.ms;
		}
		else
		{
			
		}
		
	}*/
}













