package tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import tank.battle.TankType;
import tank.battle.Utils;
import tank.display.Background;
import tank.entity.Tank;
import tank.gui.TankPanel;



public class MapManager {
	
	private static MapManager instance;
	
	private Map<Integer,Scene> scenes = new HashMap<Integer,Scene>();
	/** 结果，胜利或失败 */
	private int gameResult;
	
	private TankPanel panel;
	/** 当前是第几关 */
	private int currentRound = 1;
	

	
	private int[][][] allMaps;
	
	private MapManager(){
	
	}
	
	public static MapManager getInstance(){
		if(instance == null){
			instance = new MapManager();
			return instance;
		}
		return instance;
	}
	
	/**
	 * 初始化各个关卡/场景
	 */
	public void initScenes(){
		allMaps = new int[][][]{Background.map1,Background.map2,Background.ending};
		// 设置第一关，这里是完全自己定制,可以控制到细节
		Scene sce = new Scene(1);
		scenes.put(1, sce);
		Tank main = TankFactory.buildTank(TankType.MAIN_TANK,160,464,1,false);
		main.setDirection(1);
		Tank enemy2 = TankFactory.buildTank(TankType.LIGHT_TANK,32,16,4,true);
		Tank enemy3 = TankFactory.buildTank(TankType.LIGHT_TANK,92,16,3,true);
		Tank enemy4 = TankFactory.buildTank(TankType.LIGHT_TANK,92,200,2,true);
		Tank enemy5 = TankFactory.buildTank(TankType.HEAVY_TANK,416,16,1,true);
		Tank enemy6 = TankFactory.buildTank(TankType.HEAVY_TANK,190,200,3,true);
		Tank enemy7 = TankFactory.buildTank(TankType.HEAVY_TANK,416,200,4,true);
		sce.addTank(main);
		sce.addTank(enemy2);
		sce.addTank(enemy3);
		sce.addTank(enemy4);
		sce.addTank(enemy5);
		sce.addTank(enemy6);
		sce.addTank(enemy7);
		List<Tank> tanks = Utils.buildDefaultTankList(TankType.LIGHT_TANK, 0);
		sce.addWaitedTankList(tanks);
		
		// 设置第二关,利用了Utils类,简化了场景构造，但降低了控制权。
		// 如果还是觉得累赘，可以用Utils.buildSecne 的另一个overload方法，更加简便
		Scene sce2 = new Scene(2);
		tanks = Utils.buildDefaultTankList(TankType.HEAVY_TANK, 2);
		sce2.addWaitedTankList(tanks);
		scenes.put(2, sce2);	
		Tank main2 = TankFactory.buildTank(TankType.MAIN_TANK,160,464,1,false);
		main.setDirection(1);
		Tank enemy8 = TankFactory.buildTank(TankType.LIGHT_TANK,32,16,4,true);
		Tank enemy9 = TankFactory.buildTank(TankType.LIGHT_TANK,92,16,3,true);
		Tank enemy10 = TankFactory.buildTank(TankType.LIGHT_TANK,92,200,2,true);
		Tank enemy11 = TankFactory.buildTank(TankType.HEAVY_TANK,416,16,1,true);
		Tank enemy12 = TankFactory.buildTank(TankType.HEAVY_TANK,190,200,3,true);
		Tank enemy13 = TankFactory.buildTank(TankType.HEAVY_TANK,416,200,4,true);
		sce2.addTank(main);
		sce2.addTank(enemy2);
		sce2.addTank(enemy3);
		sce2.addTank(enemy4);
		sce2.addTank(enemy5);
		sce2.addTank(enemy6);
		sce2.addTank(enemy7);
		
		
		// 如果要自定义关卡，在这里可以加上。注意同时需要在本类中allMaps变量中，加上新地图的二维数组
		// 这里是例子
		// Scene sce3 = Utils.buildScene(3, TankType.LIGHT_TANK, 1);
		// scenes.put(3, sce3);	
		
		/** ending 画面 */
		int last = allMaps.length;
		Scene ending = new Scene(last);
		scenes.put(last, ending);	
	}
	
	public void start(){
		initScenes();
		initCurrentScene();
	}
	


	public void endGame(int gameResult) {
		this.gameResult = gameResult;
		JLabel label = panel.getLabel();
		new ShowGameResult(label).start();
	}
	
	public void resumeGame(){
		currentRound = 1;
		start();
		reset();
	}
	
	
	
	class ShowGameResult extends Thread {
		
		JLabel label;
		public ShowGameResult(JLabel label){
			this.label = label;
		}
		
		public void run(){
			int x = label.getLocation().x;
			int y = label.getLocation().y;
			label.setVisible(true);
			Font font = new Font("Dialog",1,25);
			label.setFont(font);
			label.setForeground(Color.red);
			if(gameResult == -1){
				label.setText("Game Over");
			}else if(gameResult == 1){
				label.setText("win!");
			}
			
			for(int i=0; i<panel.HEIGHT/2 - 105;i++){
				try{
					y--;
					label.setLocation(x,y);
					Thread.sleep(10);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
				Thread.sleep(2000);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(gameResult == 1 && currentRound < scenes.size()-1) {			
				nextRound();	
			}else if(gameResult == -1 || currentRound == scenes.size()-1){
				showEnding();
			}
		}
	}
	
	private void initCurrentScene(){
		gameResult = 0;
		//activate
		final Scene sce = scenes.get(currentRound);
		List<Tank> tanks = sce.getTankList();
		for(Tank t:tanks){
			t.go();
		}
		
		new Thread(){
			public void run(){
				while(sce.getWaitedTanks().size()>0){
					

					try {
						Thread.sleep(15000);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}.start();
		panel.setScene(scenes.get(currentRound));
	}

	
	public void nextRound(){	
		currentRound++;
		if(currentRound > scenes.size()){
			return;
		}
		reset();
		initCurrentScene();
	}
	
	private void reset(){
		JLabel label = panel.getLabel();
		label.setBounds(new Rectangle(200,400,150,50));
		label.setVisible(false);
		Tank mt = scenes.get(currentRound).getMainTank();
		TankPanel.setRemainLives(mt.getHp());
		JLabel hplabel = TankPanel.getHpIcon();
		hplabel.setText(String.valueOf(mt.getHp()));
		JLabel roundLabel = panel.getRoundtext();
		roundLabel.setText(String.valueOf(currentRound));
		
	}
	
	private void showEnding(){	
		int last = scenes.size();
		panel.setScene(scenes.get(last));
		panel.hideComponent();
		panel.getText().setVisible(true);
		panel.getBtn().setVisible(true);
		panel.getTankIcon().setVisible(true);
	}
	
	
	public void addScene(int round,Scene s){
		scenes.put(round, s);
	}
	
	public Scene getScene(int round){
		if(round > 0)
			return scenes.get(round);
		else
			return null;
	}

	public int getGameResult() {
		return gameResult;
	}
	
	public void setPanel(TankPanel panel) {
		this.panel = panel;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	

	public int[][][] getAllMaps() {
		return allMaps;}}
