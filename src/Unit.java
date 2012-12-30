import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Unit {
	private int x;			//Координаты х у в данный момент
	private int y;
	private int hp;			//Текущие хп
	public int type; 		//Тип это мечник, лучник ...
	private int action;		//Текущее действие
	private int frame;
	private int fraction;	//Чей юнит (пока будут только 2 фракции (1 и 2)
	private boolean mirror;	//Зеркальное отражение
	public int state=0;
	public boolean target;
	public int coolDown = 0;
	
	public int dmg_get;
	public int dmg_timer;
	
	public long previousTime = 0;
	public long previousTime2 = 0;
			
	public Unit(int hp, int type, int fraction,int x,int y)
	{
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.type = type;
		this.action = 0;
		this.frame = 0;
		this.fraction = fraction;
	}
	
	public void update(long time, UnitList box, Graphics g,ArrayList<Unit> Units,int i)
	{
		if(time - previousTime >= 100){    		
		
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
			machine(Units,box);
			getFrame(g,box);
		}
		else
		{			
			Units.remove(i);
		}
			chDmg();
		
		previousTime = time;
    	}
		box.img.chFrame(frame);
		draw(g,box);
		draw_dmg(g,box);
		
	}
	
	
	private void getFrame(Graphics g,UnitList box)
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
				coolDown = 2;
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
				coolDown = 2;
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
	}
	
	public void draw(Graphics g,UnitList box)
	{
		hpBar(g,x,y,reSize(box.w/2,box.size),5,this.hp,box.size);
		int x = this.x;
		if(mirror)
		{
			x+=reSize(box.w,box.size)/2;
			g.drawImage(box.img.sprite, x, y, reSize(box.w*(-1),box.size), reSize(box.h,box.size), null);
		}
		else
		{
			x-=reSize(box.w,box.size)/2;
			g.drawImage(box.img.sprite, x, y, reSize(box.w,box.size), reSize(box.h,box.size), null);
		}	
	}
	
	public void draw_dmg(Graphics g,UnitList box)
	{
		int x = this.x;
		g.setColor(Color.red);		
		if(mirror)
		{
			if(dmg_get>0)
				g.drawString(String.valueOf(dmg_get), x, y-5*dmg_timer);
		}
		else
		{
			if(dmg_get>0)
				g.drawString(String.valueOf(dmg_get), x, y-5*dmg_timer);
		}	
	}

	public void chDmg()
	{
		if(dmg_timer<5)
		{
			dmg_timer++;
		}
		else
			dmg_get = 0;
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
	
	public void machine(ArrayList<Unit> Units,UnitList box)
	{
		int unit_i=-1;
		int dmg;
		target = false;
		if(fraction == 1)
			action = 1;
		else
			action = 10;
		state = 0;
		
		double d=0;
		
		for(int i=0;i<Units.size();i++)
		{
			if(Units.get(i).fraction != fraction)
			{
				if(fraction == 1)
				{
					if((x+box.range) >= Units.get(i).x)
					{
						if((y+box.h-box.range >= Units.get(i).y)&&(y-box.h+box.range <= Units.get(i).y))
						{
							action = 0;
							state = 1;
							if(frame==box.frame_attack_right_finish&&!target)
							{
								dmg = getDmg(box.dmg_max,box.dmg_min);
								Units.get(i).hp -= dmg;
								Units.get(i).dmg_get = dmg;
								Units.get(i).dmg_timer = 0;
								target = true;
							}
						}
					}					
				}
				if(fraction == 2)
				{
					if((x-box.range) <= Units.get(i).x)
					{
						if((y+box.h-box.range >= Units.get(i).y)&&(y-box.h+box.range <= Units.get(i).y))
						{
							action = 9;
							state = 1;
							if(frame==box.frame_attack_right_finish&&!target)
							{
								dmg = getDmg(box.dmg_max,box.dmg_min);
								Units.get(i).hp -= dmg;
								Units.get(i).dmg_get = dmg;
								Units.get(i).dmg_timer = 0;
								target = true;
							}
						}
					}
				}				
				
				if((Math.sqrt((x-Units.get(i).x)^2+(y-Units.get(i).y)^2) < d) || d==0)
				{					
					d = Math.sqrt((Math.abs(x-Units.get(i).x))^2+(Math.abs(y-Units.get(i).y))^2);					
					if(d<10)					
						unit_i = i;
				}				
			}			
		}	
		
		if(state == 0)
		{		
			if(fraction == 1)
			{
				if(unit_i != -1)
				{
					if(x+box.w/2 <= Units.get(unit_i).x)
						x+=box.ms;
					if((y-box.h+box.range < Units.get(unit_i).y)&&(y+box.h-box.range < Units.get(unit_i).y))
						y+=box.ms;
					if((y-box.h+box.range > Units.get(unit_i).y)&&(y+box.h-box.range > Units.get(unit_i).y))
						y-=box.ms;
				}
				else
					x+=box.ms;
			}
			else
			{
				if(unit_i != -1)
				{
					if(x-box.w/2 >= Units.get(unit_i).x)
						x-=box.ms;
					if((y-box.h+box.range < Units.get(unit_i).y)&&(y+box.h-box.range < Units.get(unit_i).y))
						y+=box.ms;
					if((y-box.h+box.range > Units.get(unit_i).y)&&(y+box.h-box.range > Units.get(unit_i).y))
						y-=box.ms;
				}
				else
					x-=box.ms;
			}
		}
		else
		{
			
		}
		
	}
	
	public int getDmg(int max,int min)
	{
		Random r = new Random();
		return min + r.nextInt(max-min+1);
	}
}
