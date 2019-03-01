package customblocks;

import controller.Block;
import controller.BlockTexture;

/**
 * Tiilin määritykset
 * @author Mikko Suhonen
 *
 */
public class Brick extends Block {

	private boolean breaking = false;
	private boolean broken = false;
	private int speed;
	
	private int tickcount = 0; 
	
	/**
	 * 
	 * @param x			X sijainti
	 * @param y			Y sijainti
	 * @param width		Leveys
	 * @param height	korkeus
	 * @param texture	BlockTextuurin-olio
	 * @param speed		Katoamisnopeus
	 */
	public Brick(double x, double y, double width, double height, BlockTexture texture, int speed) {
		super(x, y, width, height, texture);
		
		this.speed = speed;
	}
	
	/**
	 * Tarkisteaan onko tiili rikkoutunut
	 * @return Tiili on rikkoutunut
	 */
	public boolean isBroken() {
		return broken;
	}
	
	/**
	 * Tarkistetaan onko tiili hajoamassa
	 * @return Tiili on hajoamassa
	 */
	public boolean isBreaking() {
		return this.breaking;
	}
	
	/**
	 * Muutetaan tiili hajoavaksi
	 */
	public void brickCollision() {
		breaking = true;
	}
	
	/**
	 * Nollataan tiilen tila
	 */
	public void reset() {
		broken = false;
		breaking = false;
		tickcount = 0;
		setVisible(true);
	}
	
	/**
	 * Tiilin hajoamisen seuraava askel
	 */
	public void tick() {
		if(breaking) {
			tickcount++;
			
			if(tickcount > 80-speed*6) {
				broken = true;
				setVisible(false);
			}
		}
	}

}
