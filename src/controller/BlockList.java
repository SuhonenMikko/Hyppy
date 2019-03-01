package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Sis‰lt‰‰ kaikkien palikkalistat
 * @author Mikko Suhonen
 *
 */
public class BlockList {

	private List<Block> blocks;
	private List<Block> visibleblocks;
	private List<Block> nonstaticblocks;
	private List<Block> transparentblocks;
	private List<Block> touchedbricks;
	private List<Block> pickedcoins;
	
	
	
	public BlockList() {
		blocks = new ArrayList<Block>();
		visibleblocks = new ArrayList<Block>();
		nonstaticblocks = new ArrayList<Block>();
		transparentblocks = new ArrayList<Block>();
		touchedbricks = new ArrayList<Block>();
		pickedcoins = new ArrayList<Block>();
	}
	
	/**
	 * Palauttaa kaikki t‰ll‰ hetkell‰ kuvaruudussa olevat palikat
	 * @return Kuvaruudussa olevat palikat
	 */
	public List<Block> getVisibleBlocks() {
		return visibleblocks;
	}
	
	/**
	 * Palauttaa kaikki pelin palikat
	 * @return Pelin kaikki palikat
	 */
	public List<Block> getAllBlocks() {
		return blocks;
	}
	
	/**
	 * Palauttaa palikat, jotka liikkuvat peliss‰
	 * @return Liikkuvat palikat
	 */
	public List<Block> getNonStaticBlocks() {
		return nonstaticblocks;
	}
	
	/**
	 * Palauttaa palikat, joiden tekstuurissa on l‰pin‰kyvi‰ pikseleit‰
	 * @return L‰pin‰kyv‰t palikat
	 */
	public List<Block> getTransparentBlocks() {
		return transparentblocks;
	}
	
	/**
	 * Palauttaa tiilet joihin p‰‰lle pelaaja on  astunut
	 * @return P‰‰lle astutut tiilet
	 */
	public List<Block> getTouchedBricks() {
		return touchedbricks;
	}
	
	/**
	 * Palauttaa pelaajan poimimat kolikot
	 * @return Pelaajan poimimat kolikot
	 */
	public List<Block> getPickedCoins() {
		return pickedcoins;
	}
}
