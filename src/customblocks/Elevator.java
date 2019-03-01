package customblocks;

import controller.Block;
import controller.BlockTexture;
import view.Main;
/**
 * Hissin määritykset
 * @author Mikko Suhonen
 *
 */
public class Elevator extends Block {

	private boolean vertical;
	
	private double starty;
	private double startx;
	
	private double endy;
	private double endx;
	
	
	private int xdir;
	private int ydir;
	
	/**
	 * 
	 * @param x			X sijainti
	 * @param y			Y sijainti
	 * @param width		Leveys
	 * @param height	korkeus
	 * @param texture	BlockTextuurin-olio
	 * @param vertical	Hissi liikkuu vertikaalisesti
	 */
	public Elevator(double x, double y, double width, double height, BlockTexture texture, boolean vertical) {
		super(x, y, width, height, texture);
		
		this.vertical = vertical;
		
		starty = y;
		startx = x;
		
		if(vertical) {
			ydir = 3;
			endx = x;
			endy = y  + Main.objectsize * 5;
			
		} else {
			xdir = 1;
			endx = x  + Main.objectsize * 5;
			endy = y;
		}
		
	}
	
	public void reset() {
		setX(startx);
		setY(starty);
		
		if(vertical) {
			ydir = 3;
		} else {
			xdir = 1;
		}
	}

	/**
	 * Muutetaan hissin suuntaa
	 */
	public void swapdir() {
		if(vertical) {
			ydir = -ydir;
		} else {
			xdir = -xdir;
		}
	}
	
	/** 
	 * Palautetaan hissin horisontaalinen suunta
	 * @return Horisontaalinen suunta
	 */
	public int getXDir() {
		return xdir;
	}
	
	/**
	 * Palautetaan hissin vertiaalinen suunta
	 * @return Vertikaalinen suunta
	 */
	public int getYDir() {
		return ydir;
	}
	
	/**
	 * Hissin animaation seuraava askel
	 */
	public void tick() {
		if(vertical) {
			if(getY() +ydir > endy || (getY() + ydir < starty)) swapdir();
			setY(getY()+ydir);
			
		} else {
			if(getX() +xdir > endx || (getX() + xdir < startx)) swapdir();
			setX(getX()+xdir);
		}
		
		
	}

}
