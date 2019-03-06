package tank;

import java.util.List;

import tank.battle.Utils;
import tank.display.Mapelements;
import tank.entity.Boom;
import tank.entity.Bullet;
import tank.entity.Cell;
import tank.entity.Tank;
import tank.gui.TankPanel;

import tank.thread.BoomThread;

public class BulletFly{

	private Tank tank;
	
	private int direction;

	public void flyBullet(Tank tank,Scene scene){
		Bullet bullet = tank.getBullet();
		List<Tank> tankList = scene.getTankList();
		int direction = bullet.getDirection();
		int[][] map = scene.getMap();
		
		while(true){
			
			if(direction == 1){
				bullet.setVertical(bullet.getVertical()-1);
			}else if(direction == 2){
				bullet.setVertical(bullet.getVertical()+1);
			}else if(direction == 3){
				bullet.setHorizon(bullet.getHorizon()-1);
			}else if(direction == 4){
				bullet.setHorizon(bullet.getHorizon()+1);
			}
			
			for(int i=0;i<tankList.size();i++){
				Tank t = tankList.get(i);
				if(tank.equals(t) || t.isAlive()==false || Utils.checkIfTeammate(t, tank)){
					continue;
				}
				
				if(Utils.checkCollide(bullet, t)) {
					t.setHp(t.getHp()-1);
					 if(!t.isComputer()){
						 TankPanel.decreaseLives();
					 }
					 if(t.getHp() <= 0) {
						 t.setAlive(false);
						 checkWinOrLose(scene);
						 if(t.isComputer())
							 ;
					 }
					 this.bulletBlast(tank, t,scene);
					 tank.setBullet(null);
					 return;
				}
				if(t.getBullet() != null && Utils.checkCollide(tank.getBullet(), t.getBullet())){
					tank.setBullet(null);
				    t.setBullet(null);
					return;
				}
			}
			
			boolean collide = false;
			try {
				for(int i=0;i< scene.getMapElements().size();i++){
					Cell cell = scene.getMapElements().get(i);
					if(cell.getType() == 0){
						continue;
					}
					if(Utils.checkCollide(tank.getBullet(), cell)){
						 if(cell.getType() == Mapelements.ELEMENT_TYPE_WALL){
							 int col = cell.getCol();
							 int row = cell.getRow();
							 map[row][col] = 0;
							 cell.setType(0);
						 }else if(cell.getType() == Mapelements.ELEMENT_TYPE_BASE){
							 int col = cell.getCol();
							 int row = cell.getRow();
							 map[row][col] = Mapelements.ELEMENT_TYPE_WHITEFLAG;
							 MapManager.getInstance().endGame(Mapelements.LOSE);
							 cell.setType(Mapelements.ELEMENT_TYPE_WHITEFLAG);
						 }else if(cell.getType() == Mapelements.ELEMENT_TYPE_LAKE){
							 continue;
						 }
						 this.bulletBlast(tank, cell,scene);
						 collide = true;
						 
					}
					
				}
			}catch(Exception e){
				
				if(tank.getBullet() == null)
					System.out.println("bullet collide.[bullet]:"+tank.getBullet());
				else
					e.printStackTrace();
				return;
			}
			if(collide == true){
				tank.setBullet(null);
				return;
			}
			try{
				 Thread.sleep((long)(1 * 1000 / Bullet.SPEED));  //(long)(1 * 1000 / Bullet.SPEED)
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
		}
	} 	// end method
	
	public void bulletBlast(Tank tank,Tank collideObj,Scene scene){
		ImageManager imgMgr = ImageManager.getInstance();
		int centerX = collideObj.getHorizon() + collideObj.getImg().getWidth(null)/2;
		int centerY = collideObj.getVertical() + collideObj.getImg().getHeight(null)/2;
		int blastX = centerX - imgMgr.getBoom().getWidth(null)/2;
		int blastY = centerY - imgMgr.getBoom().getHeight(null)/2;
		Boom boom = new Boom(blastX,blastY);
		showBoom(boom,scene);
	}
	
	private void checkWinOrLose(Scene scene){
		List<Tank> tankList = scene.getTankList();
		List<Tank> waitingList = scene.getWaitedTanks();
		Tank mainTank = scene.getMainTank();
		boolean PCElimated = true;
		if(mainTank.isComputer() == false && mainTank.isAlive() == false){
			MapManager.getInstance().endGame(Mapelements.LOSE);
			return;
		}
		if(waitingList.size() != 0){
			return;
		}else {
			for(int i=0;i<tankList.size();i++){
				Tank t = tankList.get(i);
				if(t.isComputer() == true && t.isAlive() == true){
					PCElimated = false;
				}
			}
		}
		if(PCElimated == true){
			MapManager.getInstance().endGame(Mapelements.WIN);
		}
	}
	
	public void bulletBlast(Tank tank,Bullet collideObj){
		
	}
	
	public void bulletBlast(Tank tank,Cell collideObj,Scene scene){
		ImageManager imgMgr = ImageManager.getInstance();
		int direction = tank.getBullet().getDirection();  
		int bulletX = tank.getBullet().getHorizon();
		int bulletY = tank.getBullet().getVertical();
		int blastImgWidth = imgMgr.getBoom().getWidth(null);
		int bulletImgWidth = tank.getBullet().getImg().getWidth(null);
		int blastX = 0;
		int blastY = 0;
		if(direction == 1 || direction == 2){
			blastX = bulletX + bulletImgWidth/2 - blastImgWidth/2;
			blastY = bulletY - blastImgWidth/2;
		}else if(direction == 3 || direction == 4){
			blastX = bulletX - blastImgWidth/2;
			blastY = bulletY + bulletImgWidth/2 - blastImgWidth/2;
		}
		Boom boom = new Boom(blastX,blastY);
		showBoom(boom,scene);
	}
	
	private void showBoom(Boom boom,Scene scene){
		BoomThread thread = new BoomThread(boom,scene);
		thread.start();
//		scene.addBoom(boom);
//		try{
//			Thread.sleep(20);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		scene.removeBoom(boom);
	}
	
	/**
	 * 等待队列中的坦克加入战场
	 * @param scene
	 */

	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}

