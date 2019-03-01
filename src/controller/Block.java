package controller;


import controller.BlockType.Types;
import controller.BlockTexture;

import javafx.scene.shape.Rectangle;


/**
 * Jokaisen palikan alustusluokka
 * @author Mikko Suhonen
 *
 */
public class Block extends Rectangle {

	protected boolean bg;
	protected BlockTexture texture;
	
	/**
	 * 
	 * @param x 		x sijainti
	 * @param y			y sijainti
	 * @param width		Leveys
	 * @param height	Korkeus
	 * @param texture	BlockTexture-luokanolio
	 */
	public Block(double x, double y, double width, double height, BlockTexture texture) {
		
		this.texture = texture;
		
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		setFill(texture.getTexture());
	}

	/**
	 * Tarkistaa onko palikka osa taustaa
	 * @return Palikka on tausta
	 */
	public boolean isBackground() {
		return this.texture.isBackground();
	
	}
	
	/**
	 * Tarkistaa onko palikka pelaajan mahdollista piirtää palikka karttaan vai lisätäänkö palikka muiden palikoiden avulla
	 * @return Palikka on pelaajan piirrettävissä
	 */
	public boolean isDrawable() {
		return this.texture.isDrawable();
	}
	
	/**
	 * Palauttaa palikan tekstuurin
	 * @return Palikan tekstuuri
	 */
	public BlockTexture getTexture() {
		return this.texture;
	}
	
	/**
	 *  Palauttaa palikan tyypin
	 * @return Palikan tyyli
	 */
	public Types getType() {
		return this.texture.getType();
	}
}
