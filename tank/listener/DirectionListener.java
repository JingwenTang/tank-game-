package tank.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tank.BulletFly;
import tank.MapManager;
import tank.battle.Utils;
import tank.entity.Bullet;
import tank.entity.Tank;
import tank.gui.TankPanel;

public class DirectionListener extends KeyAdapter{
	
	private static Tank tank;
	private static long currenttime=0;
	
	private int k;
	
	private int direction = 0;
	
	private TankPanel tankPanel; 
	
	private boolean complete = true;
	
	public DirectionListener(TankPanel panel){
		tankPanel = panel;
		tank = tankPanel.getScene().getMainTank();
	}
	
	public void keyPressed(KeyEvent e){
	
		if(MapManager.getInstance().getGameResult() != 0)
			return;
		k = e.getKeyCode();
		
		switch(k){
		case KeyEvent.VK_UP:
			direction = 1;
			Thread animation = new Thread(){
				public void run(){
					complete = false;
					Tank main = tankPanel.getScene().getMainTank();
					Utils.moveTank(main,16,direction, main.getSpeed(),tankPanel.getScene());
					complete = true;
				}	
			};
			
			animation.start();
			break;
			
		case KeyEvent.VK_DOWN:
			direction = 2;
			Thread animation2 = new Thread(){
				public void run(){
					complete = false;
					Tank main = tankPanel.getScene().getMainTank();
					Utils.moveTank(main,16,direction, main.getSpeed(),tankPanel.getScene());
					complete = true;
				}	
			};
			
			animation2.start();
			break;
			
		case KeyEvent.VK_LEFT:
			direction = 3;
			Thread animation3 = new Thread(){
				public void run(){
					complete = false;
					Tank main = tankPanel.getScene().getMainTank();
					Utils.moveTank(main,16,direction, main.getSpeed(),tankPanel.getScene());
					complete = true;
				}	
			};
			
			animation3.start();
			break;
			
		case KeyEvent.VK_RIGHT:
			direction = 4;
			Thread animation4 = new Thread(){
				public void run(){
					complete = false;
					Tank main = tankPanel.getScene().getMainTank();
					Utils.moveTank(main,16,direction, main.getSpeed(),tankPanel.getScene());
					complete = true;
				}	
			};
			
			animation4.start();
			break;
			
		case KeyEvent.VK_SPACE:
			try {
				if(getCurentSecond()-currenttime>1000)
					{
					currenttime=getCurentSecond();
					if( MapManager.getInstance().getGameResult() != 0){
						return;
				 }

				 //if(e.getKeyCode()==KeyEvent.VK_SPACE)
				 Bullet bullet = tank.readyTofire();

				 //必须另起线程！
				 new Thread(){
					 public void run(){
						 BulletFly bulletFly = new BulletFly();
						 bulletFly.flyBullet(tank,tankPanel.getScene());
					 }
				 }.start();}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		if(!complete){
			return;
		}
		
		}
		
	
	public String getCurentTime(){
		  SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
		  String   date   =   formatter.format(new  java.util.Date()); 
		        return date;
		 }
	public long getCurentSecond() throws ParseException{
		  String curentTime = getCurentTime();
		  SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date;
		  long beginTime;
		  date = sDate.parse(curentTime);
		  beginTime = date.getTime();
		  return beginTime;
		 }
	
	}



