package customblocks;


import controller.Block;
import controller.BlockTexture;
/**
 * Vihollisen luokka
 * @author Mikko Suhonen
 *
 */
public class Enemy extends Block {

	private double startx;
	
	private double x;
	private double y;
	
	private double endx;
	
	private int xdir;
	
	/**
	 * @param x			X sijainti
	 * @param y			Y sijainti
	 * @param width		Leveys
	 * @param height	korkeus
	 * @param texture	BlockTextuurin-olio
	 * @param speed		Liikkumisnopeus
	 */
	public Enemy(double x, double y, double width, double height, BlockTexture texture, int speed) {
		super(x, y, width, height, texture);
		
		this.x = x;
		this.y = y;
		
		startx = x - getWidth()*5;
		endx = x + getWidth()*5;
		
		xdir = speed;
		
	}
	
	/**
	 * Palautetaan vihollinen x aloitus sijaintiin
	 */
	public void resetX() {
		setX(this.x);
	}
	
	
	/**
	 * Palautetaan vihollisen y aloitus sijaintiin
	 */
	public void resetY() {
		setY(this.y);
	}
	
	/**
	 * Vihollisen animaation seuraava askel
	 */
	public void tick() {
		if(getX()+xdir > endx || getX()+xdir < startx) {
			xdir = -xdir;
		}
		
		setX(getX()+xdir);
	
	}
	
}
