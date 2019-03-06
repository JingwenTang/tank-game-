package tank.thread;

import java.awt.Rectangle;

import tank.Scene;
import tank.entity.Boom;
import tank.entity.Tank;

public class BoomThread extends Thread{
	
	private Boom  boom;
	private Scene scene;
	
	public BoomThread(Boom boom,Scene scene){
		this.boom = boom;
		this.scene = scene;
	}
	
	public void run(){
		scene.addBoom(boom);
		
		scene.removeBoom(boom);
	}}

