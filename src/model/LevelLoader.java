package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import view.Main;

public class LevelLoader {
	
	
	public LevelLoader() {
		
	}
	
	/**
	 * Luodaan tarvittavat kansiot ja kopioidaan res-kansion kartat luotuihin kansioihin.
	 * @return Kansioiden luonti ja karttojen kopiointi onnistui
	 */
	public boolean load() {
		
		boolean success = true;
		
		if(!createFolders()) {
			success = false;
		}
		
		if(!createDefaultLevels()) {
			success = false;
		}
	
		
		return success;
	}
	
	/**
	 * Palauttaa karttojen nimet
	 * @return Karttojen nimet sis‰lt‰v‰ lista
	 */
	public List<String> getLevels() {
		List<String> lvls = new ArrayList<String>();
		File[] files = new File(Main.ROOT_PATH+"Levels/").listFiles();
		
		for(File f : files) {
			if(f.isFile()) {
				if(f.getName().contains(".bmp")) {
					lvls.add(f.getName());
				}
			}
		}

		return lvls;
	}
	
	/**
	 * Luodaan tarvittavat kansiot
	 * @return	Kansioiden luonti onnistui
	 */
	public boolean createFolders() {
		boolean success = true;
		
		String[] folders = {Main.ROOT_PATH,Main.ROOT_PATH+"Levels/"};
		
		for(String folder : folders) {
			if(!Files.exists(Paths.get(folder))) {
				try {
				Files.createDirectories(Paths.get(folder));
				
				} catch(IOException e) {
					e.printStackTrace();
					success = false;
				}
			}
		}

		return success;
	}
	
	/**
	 * Kopiointi ne res-kansion kent‰t luotuihin kansioihin, joita ei viel‰ lˆydy kansioista.
	 * Kopioimalla kent‰t k‰ytt‰j‰ pystyy tekem‰‰n itse helpommin muutoksia kenttiin.
	 * Samalla k‰ytt‰j‰ pystyy itse lis‰‰m‰‰n kentti‰ yksinkertaisesti tallentamalla ne samaan kansioon miss‰ nykyisen kent‰t ovat.
	 * 
	 * @return Kopiointi onnistui
	 */
	public boolean createDefaultLevels() {
		boolean success = true;
		
		for(String level : Main.defaultlevels) {
			
			if(!Files.exists(Paths.get(Main.ROOT_PATH+"Levels/"+level+".bmp"))) {
				
				InputStream is = null;
				FileOutputStream os = null;
				try {
					Files.createFile(Paths.get(Main.ROOT_PATH+"Levels/"+level+".bmp"));
					is = LevelLoader.class.getClassLoader().getResourceAsStream("res/levels/"+level+".bmp");
					
					os = new FileOutputStream(Main.ROOT_PATH+"Levels/"+level+".bmp");
					
					byte[] data = new byte[2048];
					int x; 
					
					while(-1 != (x = is.read(data))) {
						os.write(data,0,x);
					}

				} catch(IOException e) {
					e.printStackTrace();
					success = false;
				} finally {
					if(os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					if(is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return success;
		
		
	}
}
