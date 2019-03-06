package tank;

import java.awt.Image;

import javax.swing.ImageIcon;

import tank.display.Mapelements;

public class ImageManager {
	
	  private Image brick;
	  private Image steel;
	  private Image eagle;
	  private Image lake;
	  private Image tank;	  
	  private Image background;  
	  private Image edge;  
	  private Image whiteFlag;  
	  private Image boom;
	  private Image bullet;
	  private Image blink;
	  private Image blinkExp;	  
	  private Image miniTank;
	  private Image roundflag;
	  private Image mainTank;
	  private Image heavyTank;
	  private Image lightTank;
	 
	  
	  private static ImageManager instance;
	  
	  public static ImageManager getInstance(){
		  if(instance == null){
			  return new ImageManager();
		  }
		  return instance;
	  }
	  
	  private ImageManager(){
		  bullet = loadImg("bullet.png");
		  brick = loadImg("lbrick.png");
		  steel =loadImg("steel.png");
		  eagle = loadImg("eagle.png");
		  tank = loadImg("myTank.png");
		  background = loadImg("bg.png");
		  edge = loadImg("edge.png");	  
		  whiteFlag = loadImg("white_flag.png");
		  boom = loadImg("boom.png");
		  blink = loadImg("blink.png");
		  blinkExp = loadImg("blinkExp2.png"); 
		  miniTank = loadImg("mini-tank.png"); 
		  roundflag = loadImg("flag.png");
		  mainTank =  loadImg("myTank.png");
		  heavyTank =  loadImg("heavy_tank.png");
		  lightTank =  loadImg("light_tank.png");
		  lake =  loadImg("lake.png");
	  }

		public Image getImageByType(int type){
			Image img = null;
			switch(type) {
				case Mapelements.ELEMENT_TYPE_ROAD:
					img = background;
					break;
					
				case Mapelements.ELEMENT_TYPE_WALL:
					img = brick;
					break;
					
				case Mapelements.ELEMENT_TYPE_STEEL:
					img = steel;
					break;	
					
				case Mapelements.ELEMENT_TYPE_BASE:
					img = eagle;
					break;
					
				case Mapelements.ELEMENT_TYPE_EDGE:
					img = edge;
					break;
					
				case Mapelements.ELEMENT_TYPE_NOTHING:
			
					break;
					
				case Mapelements.ELEMENT_TYPE_WHITEFLAG:
					img = whiteFlag;
					break;
					
				case Mapelements.ELEMENT_TYPE_MINITANK:
					img = miniTank;
					break;
					
				case Mapelements.ELEMENT_TYPE_LAKE:
					img = lake;
					break;
			}
			return img;
		}
		

	public Image loadImg(String str){
		 ImageIcon icon = new ImageIcon((str));
		 return icon.getImage();
	}

	public Image getSteel() {
		return steel;
	}

	public void setSteel(Image steel) {
		this.steel = steel;
	}

	public Image getEagle() {
		return eagle;
	}

	public void setEagle(Image eagle) {
		this.eagle = eagle;
	}

	public Image getTank() {
		return tank;
	}

	public void setTank(Image tank) {
		this.tank = tank;
	}

	public Image getBackground() {
		return background;
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public Image getEdge() {
		return edge;
	}

	public void setEdge(Image edge) {
		this.edge = edge;
	}

	public Image getWhiteFlag() {
		return whiteFlag;
	}

	public void setWhiteFlag(Image whiteFlag) {
		this.whiteFlag = whiteFlag;
	}

	public Image getBullet() {
		return bullet;
	}


	public void setBullet(Image bullet) {
		this.bullet = bullet;
	}

	public Image getBoom() {
		return boom;
	}

	public void setBoom(Image boom) {
		this.boom = boom;
	}

	public Image getBlink() {
		return blink;
	}

	public void setBlink(Image blink) {
		this.blink = blink;
	}

	public Image getBlinkExp() {
		return blinkExp;
	}

	public void setBlinkExp(Image blinkExp) {
		this.blinkExp = blinkExp;
	}

	public Image getRoundflag() {
		return roundflag;
	}

	public Image getMainTank() {
		return mainTank;
	}

	public Image getHeavyTank() {
		return heavyTank;
	}

	public Image getLightTank() {
		return lightTank;
	}

	public Image getLake() {
		return lake;
	}


}

