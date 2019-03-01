package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import view.Main;

/**
 * Piste-ennätyksien tallennus-luokka
 * @author Mikko Suhonen
 *
 */
public class StoreScore implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<MapScore> scores = new ArrayList<MapScore>();
	
	private static List<StoreScore>  players = new ArrayList<StoreScore>();

	
	public StoreScore() {
		
	}
	
	public StoreScore(String name) {
		this.name = name;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public List<MapScore> getScores() {
		return this.scores;
	}
	
	public boolean saveScore(String lvl, long time, int difficulty) {
		boolean save = false, found = false;
				
		StoreScore current = null;
		
		boolean playerfound = false;
		for(StoreScore player : players) {
			if(player.getName() == this.name) {
				
				current = player;
				
				playerfound = true;
				break;
			}
		}
		
		
		if(!playerfound) {
			current = this;
		}
		
		if(current != null) {
			for(MapScore score : current.getScores()) {
				if(score.getMap() == lvl && score.getDifficulty() == difficulty) {
					if(score.getTime() < time) {
						score.setTime(time);
						save = true;
					}
					
					found = true;
					break;
				}
			}
			
			if(!found) {
				current.scores.add(new MapScore(lvl,time, difficulty));
				save = true;
			}
			
			if(!playerfound) {
				players.add(current);
			}
			
		}
		return save;
		
	}
		
	
	public static List<StoreScore> getPlayers() {
		return players;
	}
	
	public static void saveHighscores() {

		ObjectOutputStream oos = null;
		
		try{
			oos = new ObjectOutputStream(new FileOutputStream(Main.ROOT_PATH+"Highscores.dat"));
			oos.writeObject(players);
			oos.close();
		   
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(oos  != null){
		        try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    } 
		    
		}
	}

	@SuppressWarnings("unchecked")
	public static void readHighscores() {
		
		Path path = Paths.get(Main.ROOT_PATH+"Highscores.dat");
		
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path.getParent());
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if(new File(path.toString()).length() != 0) {
			ObjectInputStream ois = null;
			
			try {
			    ois = new ObjectInputStream(new FileInputStream(Main.ROOT_PATH+"Highscores.dat"));
			    
			    players = (List<StoreScore>)ois.readObject();
			    
			    ois.close();
			    
			} catch (EOFException e) {
			    
			    
			} catch (Exception e) {
				e.printStackTrace();
				
			} finally {
			    if(ois != null){
			    	try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    } 
			}
		}
		
		
	}	
}
