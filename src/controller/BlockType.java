package controller;

import java.util.ArrayList;
import java.util.List;
/**
 * Sis‰lt‰‰ palikoiden tyyppien ja tyyppien m‰‰ritykset
 * @author Mikko Suhonen
 *
 */
public class BlockType {


	/**
	 * Palikan tyylit
	 */
	public enum Types {
		Player(),
		Enemy(),
		Grass(),
		GrassTop(),
		Dirt(),
		Stone(),
		Cloud(),
		Lava(),
		LavaBrick(),
		Goal(),
		ElevatorVer(),
		ElevatorHor(),
		BgSpawn(),
		BgStone(), 
		Coin(),
		BgAir(),
		Brick(),
		Pillar(),
		PillarTop(),
		Torch(),
		CastleBrick(),
		BgCastleBrick(),
	}
	
	private List<BlockTexture> blocktype;
	
	/**
	 * M‰‰rittelee jokaiselle tyylille v‰riarvot kartassa, tiedoston nimen, tiedon onko palikalla animaatio, onko palikka osa taustaa ja voiko 
	 * pelaaja piirt‰‰ palikan vai luodaanko palikka muiden palikoiden avulla.
	 */
	public BlockType() {
		blocktype = new ArrayList<BlockTexture>();
		blocktype.clear();
		
		//								Tyyppi				R		G		B		Tiedostonimi	Animatio BG		Pelaajan piirrett‰viss‰
		blocktype.add(new BlockTexture(Types.Player, 		0,		0,		0,		"player",		false,	false, 	true	));	
		blocktype.add(new BlockTexture(Types.Enemy, 		255,	0,		0,		"enemy",		true,	false, 	true	));		
		blocktype.add(new BlockTexture(Types.Grass, 		0,		255,	0,		"grass",		false,	false, 	true	));		
		blocktype.add(new BlockTexture(Types.GrassTop, 		0,		0,		0,		"grasstop",		false,	true, 	false	));		
		blocktype.add(new BlockTexture(Types.Dirt, 			135,	74,		24,		"dirt",			false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.Stone, 		150,	150,	150,	"stone",		false,	false, 	true	));	
		blocktype.add(new BlockTexture(Types.Cloud, 		90,		200,	255,	"cloud",		false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.Lava, 			255,	150,	2,		"lava",			true,	false, 	true	));		
		blocktype.add(new BlockTexture(Types.LavaBrick, 	69,		0,		0,		"lavabrick",	true,	false, 	true	));	
		blocktype.add(new BlockTexture(Types.Goal, 			255,	255,	0,		"goal",			false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.ElevatorVer, 	0,		36,		255,	"elevatorver",	false,	false, 	true	));
		blocktype.add(new BlockTexture(Types.ElevatorHor, 	0,		126,	255,	"elevatorhor",	false,	false, 	true	));	
		blocktype.add(new BlockTexture(Types.BgSpawn,		0,		0,		0,		"bgspawn",		false,	true, 	false	));	
		blocktype.add(new BlockTexture(Types.BgStone, 		49,		49,		49,		"bgstone",		false,	true, 	true	));		
		blocktype.add(new BlockTexture(Types.Coin, 			255,	0,		255,	"coin",			false,	false,	true	));		
		blocktype.add(new BlockTexture(Types.BgAir, 		255,	255,	255,	"",				false,	true, 	true	));			
		blocktype.add(new BlockTexture(Types.Brick, 		139,	71,		37,		"brick",		false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.Pillar, 		200,	200,	200,	"pillar",		false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.PillarTop, 	100,	100,	100,	"pillartop",	false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.Torch, 		255,	208,	147,	"torch",		true,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.CastleBrick, 	149,	131,	131,	"castlebrick",	false,	false, 	true	));			
		blocktype.add(new BlockTexture(Types.BgCastleBrick, 100,	85,		85,		"bgcastlebrick",false,	true, 	true	));			
		
		
	}
	
	/**
	 * Palauttaa kaikki palikoiden tyypit
	 * @return
	 */
	public List<BlockTexture> getBlockTypes() {
		return this.blocktype;
	}
	

	/**
	 * Palauttaa tyypin avulla palikan BlockTexture luokan olion.
	 * @param type 
	 * @return BlockTexture luokan olio
	 */
	public BlockTexture getByType(Types type) {
		return blocktype.get(Types.valueOf(type.toString()).ordinal());
	}
}
