package customblocks;


import controller.Block;
import controller.BlockTexture;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

/**
 * Pelaaja palikan luokat
 * @author Mikko Suhonen
 *
 */
public class Player extends Block {

	private double xdir;
	private double ydir;
	private boolean jump = false;
	private int jumpcount = 0;
	private Elevator elev = null;
	
	private boolean fall = true;
	
	
	private int startx;
	private int starty;
	
	private final int DEFAULT_FALLSPEED = 3;
	private final int MAX_FALLSPEED = 30;
	private final int GRAVITY_INCREASE = 10;
	
	
	private int fallspeed = DEFAULT_FALLSPEED;
	
	private int gravity = 0;

	
	/**
	 * @param x			X sijainti
	 * @param y			Y sijainti
	 * @param width		Leveys
	 * @param height	korkeus
	 * @param texture	BlockTextuurin-olio
	 */
	public Player(int x, int y, int width, int height, BlockTexture texture) {
		super(x, y, width, height, texture);
		
		this.startx = x;
		this.starty = y;
	}
	
	/**
	 * X aloitus sijaint
	 * @return Palikan X aloitus sijainti
	 */
	public int getStartX() {
		return this.startx;
	}
	
	/**
	 * Y aloitus sijainti
	 * @return Palikan Y aloitus sijaint
	 */
	public int getStartY() {
		return this.starty;
	}

	/**
	 * @return Vasemman laidan t�rm�ys rajat
	 */
	public Bounds collideLeft() {
		return new Rectangle((int)getX(),
						 	 (int)(getY()+getHeight()/5),
							 (int)(getWidth()/5),
							 (int)(getHeight()-getHeight()/7*3)).getBoundsInParent();
	}
	
	/**
	 * @return Oikean laidan t�rm�ys rajat
	 */
	public Bounds collideRight() {
		return new Rectangle((int)(getX()+getWidth()-(getWidth()/5)),
							 (int)(getY()+getHeight()/5),
							 (int)(getWidth()/5),
							 (int)(getHeight()-getHeight()/7*3)).getBoundsInParent();
	}
	
	/**
	 * @return Yl�laidan t�rm�ys rajat
	 */
	public Bounds collideTop() {
		return new Rectangle((int)(getX()+getWidth()/5),
							 (int)(getY()),
							 (int)(getWidth()-getWidth()/5*2),
							 (int)(getHeight()/5)).getBoundsInParent();
	}
	
	
	/**
	 * @return Alalaidan t�rm�ys rajat
	 */
	public Bounds collideBottom() {
		return new Rectangle((int)(getX()+getWidth()/5),
							 (int)(getY()+getHeight()-getHeight()/5),
							 (int)(getWidth()-getWidth()/5*2),
							 (int)(getHeight()/5)).getBoundsInParent();
	}
	
	/**
	 * Asettaa pelaajan t�m�n hetkeisen hissin olion
	 * @param elev Nykyisen hissin olio
	 */
	public void setElevator(Elevator elev) {
		this.elev = elev;
	}
	
	/**
	 * @return Pelaaja on hississ�
	 */
	public boolean onElevator() {
		return (elev != null);
	}
	
	/**
	 * Palauttaa nykyisen hissin olion
	 * @return Nykyisen hissin olio
	 */
	public Elevator getElevator() {
		return elev;
	}
	
	/**
	 * Tarkistaa hypp��k� pelaaja
	 * @return Pelaaja hypp��
	 */
	public boolean getJump() {
		return jump;
	}

	/**
	 * Asettaa pelaaajan hypyn t�m�n hetkisen tilan
	 * @param jump Pelaaja hypp��
	 */
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	/**
	 * Tarkistaa onko pelaaja tippumassa
	 * @return Pelaaja tippuu
	 */
	public boolean isFall() {
		return fall;
	}
	
	/**
	 * Tiputuksessa aiheutuvan kiihtyvyyden palautus oletusarvoon
	 */
	public void resetFallMulti() {
		this.fallspeed = DEFAULT_FALLSPEED;
	}
	
	/**
	 * Asettaa tippumsen tilan
	 * @param fall Pelaaja tippuu
	 */
	public void setFall(boolean fall) {
		this.fall = fall;
	}

	/**
	 * Palauttaa pelaajan horisontaalisen liikkumisensuunnan
	 * @return Horisontaalinen liikkumissuunta
	 */
	public double getXdir() {
		return xdir;
	}

	/**
	 * Asettaa pelaajan horisntaalisen liikkumisensuunnan
	 * @param xdir Horisontaalinen liikkumissuunta
	 */
	public void setXdir(double xdir) {
		this.xdir = xdir;
	}

	/**
	 * Palauttaa pelaajan vertikaalisen liikkumissuunnan
	 * @return Vertikaalinen liikkumissuunta
	 */
	public double getYdir() {
		return ydir;
	}

	/**
	 * Asettaa pelaaajan vertikaalisen liikkumissuunnan
	 * @param ydir Vertikaalinen liikkumissuunta
	 */
	public void setYdir(double ydir) {
		this.ydir = ydir;
	}
	

	/**
	 * Suorita hyppy
	 */
	public void jump() {
		if(jumpcount != 2) {
			this.jump = true;
			this.ydir = -10;
			jumpcount++;
		}
	}
	
	/**
	 * Palauttaa nykyisen hyppyjen m��r�n, ennen viimeisint� maahan koskestusta
	 * @return
	 */
	public int getJumpcount() {
		return jumpcount;
	}

	/**
	 * Asettaa hyppyjen m��r�n takaisin aloitustilaan
	 */
	public void resetJumpCount() {
		this.jumpcount = 0;
	}
	
	
	/**
	 * Suorittaa pelaajan animaation seuraavan askeleen
	 */
	public void tick() {
		int elevdir = 0;
		if(elev != null) {
			elevdir = elev.getXDir();
		}
		
		if(xdir + elevdir != 0) {
			
			setX(getX()+xdir + elevdir);
		}
		
		if(ydir != 0) {
			setY(getY()+ydir);
			
		}
		
		if(jump || ydir < 0) {
			fall = false;
			ydir = ydir + 1;
			if(ydir >= 0) {
				ydir = 0;
				fall = true;
			}
		}
		
		if(fall) {
			if(gravity + GRAVITY_INCREASE >= 100 && fallspeed < MAX_FALLSPEED) {
				fallspeed += 1;
				gravity = 0;
			} else {
				gravity += GRAVITY_INCREASE;
			}

			setY(getY()+fallspeed);
		}
		
		
		
	}
	
}