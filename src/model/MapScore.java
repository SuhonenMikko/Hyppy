package model;

/**
 * Player.java luokan pisteet, kartat ja vaikeusasteen yhdistävä olio
 * @author Mikko Suhonen
 *
 */
public class MapScore extends StoreScore {
	
	private static final long serialVersionUID = -7651029041743953500L;
	private long time;
	private String map;
	private int difficulty;
	
	public MapScore() {
		
	}
	/**
	 * @param map			Kartan nimi
	 * @param time			Suoritusaika
	 * @param difficulty	Suorituksen vaikeusaste
	 */
	public MapScore(String map, long time, int difficulty) {
		this.time = time;
		this.map = map;
		this.difficulty = difficulty;
	}

	/**
	 * Palauttaa suoritusajan
	 * @return Suoritusaika
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Asettaa suoritusajan
	 * @param time Suoritusaika
	 */
	public void setTime(long time) {
		this.time = time;
		
	}

	/**
	 * Palauttaa kartan nimen
	 * @return Kartan nimi
	 */
	public String getMap() {
		return map;
	}

	/**
	 * Asettaa kartan nimen
	 * @param map Kartan nimi
	 */
	public void setMap(String map) {
		this.map = map;
	}
	
	/**
	 * Palauttaa suorituksen vaikeusasteen
	 * @return	Suorituksen vaikeusaste
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Asettaa suorituksen vaikeusasteen
	 * @param difficulty	Palauttaa suorituksen vaikeusasteen
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
}
