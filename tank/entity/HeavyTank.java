package tank.entity;

import tank.ImageManager;

public class HeavyTank extends Tank{
	
	private int hp = 2;
	
	private int speed = 50;
	
	public HeavyTank(int horizon,int vertical,int direction,boolean computer){
		super(horizon,vertical,direction,computer);
		img = ImageManager.getInstance().getHeavyTank();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

