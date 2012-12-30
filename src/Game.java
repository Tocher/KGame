import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private boolean running;

	public static int WIDTH = 1000;				//Ширина окна
	public static int HEIGHT = 700;				//Высота окна
	public static String NAME = "KGame";		//Надпись

	private boolean leftPressed = false;		//Кнопки
	private boolean rightPressed = false;
	private boolean downPressed = false;
	private boolean upPressed = false;
	private boolean APressed = false;	

	public static int gold = 0;

    ArrayList<Unit> All_Units = new ArrayList<Unit>();			//Юниты в игре
    ArrayList<UnitList> BaseUnits = new ArrayList<UnitList>();	//Шаблоны юнитов
	Background bg = new Background();							//Фон
	ArrayList<Build> All_Builds = new ArrayList<Build>();
	ArrayList<BuildList> BaseBuilds = new ArrayList<BuildList>();
    
    public static void main(String[] args) {		//Главная функция
		Game game = new Game();				
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));		//создание окна, по ширине высоте имени бла бла
		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		//Выравнивение окна по центру монитора
		int w = 1050;
		int h = 750;
		int x1 = (dim.width - w) / 2;
		int y2 = (dim.height - h) / 2;
		frame.setLocation(x1, y2);
		
		frame.pack();						//какая-то бня
		frame.setResizable(false);
		frame.setVisible(true);
		game.start();						//ПОНЕСЛАСЬ
	}
	
	public void start() {		//Создает игровой поток
		running = true;
		new Thread(this).start();
	}

	public void run() {		//Запуск
		long lastTime = System.currentTimeMillis(); 	//Время в мс
		long delta;										//переменная для хранения миллисекунд между прорисовкой		
		long spawn=lastTime;

		init();			// Инициализация всего, т.е. используется 1 раз
		
		while(running) {			// Основной цикл игры
			delta = System.currentTimeMillis() - lastTime; 
			lastTime = System.currentTimeMillis();				//расчет времени между кадрами
			render(lastTime);									// Отрисовка
			update(delta);								// Тут реагируем на изменения (кнопки, постройки ...)					
			if(spawn+7000<=System.currentTimeMillis())
			{
				spawn = System.currentTimeMillis();
				
				///
				All_Units.add(new Unit(100,1,1,140,300));
				All_Units.add(new Unit(100,1,2,860,290));
				
				//All_Units.add(new Unit(100,3,1,120,356));
				//All_Units.add(new Unit(100,3,2,860,333));
				
				//All_Units.add(new Unit(100,2,1,120,367));
				//All_Units.add(new Unit(100,2,2,860,354));

				gold += 50;
				//*/
			}
		}
	}
    
	public void init() {
		addKeyListener(new KeyInputHandler());		// Прослушка клавиш Временно отключена т.к. юниты сами ходят
		addMouseListener(new CustomListener());
		addMouseWheelListener(new CustomListenerWheel());        
        
        // Заргузка юнитов по 1 шт.
		BaseUnits.add(new UnitList("boss.png", 9, 6, 64, 64,1,30,20,30));
		BaseUnits.get(0).frames_load(12, 15, 0, 5, 6, 8);
		BaseUnits.add(new UnitList("skeleton.png", 9, 4, 48, 48,2,24,10,20));
		BaseUnits.get(1).frames_load(8, 9, 0, 2, 4, 7);
		BaseUnits.add(new UnitList("ogre.png", 9, 6, 48, 48,3,30,5,15));
		BaseUnits.get(2).frames_load(12, 13, 0, 2, 6, 11);
		BaseUnits.add(new UnitList("deathknight.png", 9, 5, 42, 42,2,30,15,25));
		BaseUnits.get(3).frames_load(10, 11, 0, 4, 5, 8);
        
		BaseBuilds.add(new BuildList("build1.png", 1, 1, 48, 48));
		BaseBuilds.get(0).frames_load(0, 0);
		All_Builds.add(new Build(100,0,1,95,300));
		All_Builds.add(new Build(100,0,2,855,300));
		//All_Units.add(new Unit(100,1,1,420,360));
		//
		//All_Units.add(new Unit(100,1,2,660,280));		
		
	}

	public void render(long time) {	//Отрисовка
		BufferStrategy bs = getBufferStrategy();	//Использование стратегии буфферизации для отрисовки (меньше жрет памяти)
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		// Создаем переменную графики
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white); 					// Белый фон
		g.fillRect(0, 0, getWidth(), getHeight()); 	//Зарисовка белым
		
		bg.drawBG(g,getWidth(),getHeight(),time);
				
		//Цикл вычисления действий юнитов и перерисовка их.
		for(int i=0;i<All_Units.size();i++)
		{			
			All_Units.get(i).update(System.currentTimeMillis(),BaseUnits.get(All_Units.get(i).type),g,All_Units,i);			
		}
		for(int i=0;i<All_Builds.size();i++)
			All_Builds.get(i).draw(g, BaseBuilds.get(All_Builds.get(i).type));
		
		//Очистка и вывод на экран
		g.dispose();
		bs.show();
	}

	public void update(long delta) {
		if (leftPressed == true) {
			leftPressed = false;
		}
		if (rightPressed == true) {
			rightPressed = false;
		}	
		if (upPressed == true) {
			upPressed = false;
		}
		if (downPressed == true) {
			downPressed = false;
		}
		if (APressed == true) {		
			APressed = false;
		}
	}
	
	

	private class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				APressed = true;
			}
		} 

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
		}
	}
	
	public class CustomListener implements MouseListener {
		          public void mouseClicked(MouseEvent e) {
		          }

		          public void mouseEntered(MouseEvent e) {
		               
		          }

		          public void mouseExited(MouseEvent e) {
		              
		          }
		
		          public void mousePressed(MouseEvent e) {
		          }
		
		          public void mouseReleased(MouseEvent e) {
		              
		          }
		     }
	
	public class CustomListenerWheel implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			for(int i=0;i<BaseUnits.size();i++)
			{
				if(e.getWheelRotation() < 0)   
				{
         		   if(BaseUnits.get(i).size < 500)
         			   BaseUnits.get(i).size += 50;
				}
         		else
         		{
         			if(BaseUnits.get(i).size > 50)
          				BaseUnits.get(i).size -= 50;
         		}
			}
       }
   }

}