package controller;


import controller.BlockType.Types;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BlockTexture {
	
	private ImagePattern texture;
	private BlockType.Types type;
	
	private int red;
	private int green;
	private int blue;
	
	private boolean anim;
	
	private static int animcount;
	private ImagePattern[] animation = new ImagePattern[4];
	
	private boolean bg;
	private boolean drawable;
	
	/**
	 * 
	 * @param type		Tyyppi
	 * @param r			Punainen
	 * @param g			Vihre‰
	 * @param b			Sininen
	 * @param file		Tiedoston sijainti
	 * @param anim		Palikalla on animaatio
	 * @param bg		Palikka on osa taustaa
	 * @param drawable	Palikka on pelaajan piirrett‰viss‰
	 */
	public BlockTexture(Types type, int r, int g, int b, String file, boolean anim, boolean bg, boolean drawable) {
		this.type = type;
		
		this.red = r;
		this.green = g;
		this.blue = b;
		
		if(!file.isEmpty()) {
			if(anim) {
				for(int i = 0; i < 4; i++) {
					animation[i] = new ImagePattern(new Image("res/textures/"+file+""+(1+i)+".png"));
				}
				this.texture = new ImagePattern(new Image("res/textures/"+file+"1.png"));
			} else {
				this.texture = new ImagePattern(new Image("res/textures/"+file+".png"));
			}
		}
		
		this.anim = anim;
		this.bg = bg;
		this.drawable = drawable;
	}
	
	/**
	 * Seuraavan tekstuurin animaation askel
	 */
	public static void animationtick() {
		if(animcount +1 >= 4) {
			animcount = 0;
		} else {
			animcount++;
		}
		
		
	}
	
	/**
	 * Palautttaa nykyisen animaation tekstuurin
	 * @return Tekstuuri
	 */
	public ImagePattern getAnimationTexture() {
		return this.animation[animcount];
	}
	
	/**
	 * Palauttaa tekstuurin (Palikoille, jotka eiv‰t ole animaatioita)
	 * @return Tekstuuri
	 */
	public ImagePattern getTexture() {
		return texture;
	}

	/**
	 * Palatutaa palikan typin
	 * @return Tyyppi
	 */
	public Types getType() {
		return type;
	}

	/**
	 * Palauttaa punaisen arvon
	 * @return Punaisen arvo
	 */
	public int getRed() {
		return this.red;
	}

	/**
	 * Palauttaa vihre‰n arvon
	 * @return	Vihre‰ arvo
	 */
	public int getGreen() {
		return this.green;
	}
	
	/**
	 * Palauttaa sinisen arvon
	 * @return	Sininen arvo
	 */
	public int getBlue() {
		return this.blue;
	}

	/**
	 * @return Palikka on osa taustaa
	 */
	public boolean isBackground() {
		return this.bg;
	}
	
	/**
	 * Voiko pelaaja piirt‰‰ palikan luomaansa karttaan vai luodaanko palikka automaattisesti
	 * Esimerkiksi pelaaja palikkaa luodessa luodaan pelaajan palikan sijaintiin BgSpawn-palikka. T‰m‰ palikka ei ole erikseen piirrett‰viss‰.
	 * 
	 * @return Palikka on pelaajan piirrett‰viss‰
	 */
	public boolean isDrawable() {
		return this.drawable;
	}
	
	/**
	 * @return Palikalla on animaatio
	 */
	public boolean hasAnimation() {
		return this.anim;
	}
	
}
