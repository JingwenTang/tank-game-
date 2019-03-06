package tank;

import tank.entity.Tank;

public interface Move {
	
	void move(Tank tank,int speed,Scene scene);
}

