package controller;


import java.util.ArrayList;
import java.util.List;

import controller.BlockType.Types;
import customblocks.Brick;
import customblocks.Elevator;
import customblocks.Enemy;
import customblocks.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.StoreScore;
import view.Selection;
import view.Main;
/**
 * Peli-luokka
 * @author Mikko Suhonen
 *
 */
public class Game extends Pane {
	
	private String lvl;							//Tason nimi
	private int difficulty;						//Tason vaikeusaste
	private boolean deflvl;						//Onko kartta pelin oma taso, vai pelaajan itse luoma
	
	private Block map[][][];					//Pelin palikoiden taulukko (x,y,[0 = block | 1 = bg])
	
	private String errormsg = "";				//Virhe viesti
		
	private List<KeyCode> keys;					//T‰ll‰ hetkell‰ painetut painikkeet
	
	private Timeline run, timer, pattern;		//Pelin timeline / Ajan laskenta / Animaation timeline
	private long difvalue;						//Nykyisen ajan ja aloitusajan erotus
	private long starttime;						//Aloitusaika
	private String diftext;						//Ajan teksti
	
	private Player player;						//Pelaajan palikka
	private StoreScore save;					//Tiedontallennus-olio
	private BlockList blocks;					//Palikkalistojen-olio
	private BlockType bt;						//Palikkatyyppien-olio
	
	private ColorAdjust ca = new ColorAdjust();	//Pelaajan v‰rin muunnos
	private double hue;							//Nykyinen pelaajan palikan s‰vy
	
	private boolean dead;						//Onko pelaaja kuollut
	private boolean win;						//Onko pelaaja voittanut pelin
	
	private Pane gameView;						//Pelin‰kym‰
	private StackPane gameOverView;				//Peli p‰‰tty-n‰kym‰
	private VBox status;						//Vasemman yl‰kulman status
	
	private int coinspicked;					//Poimittujen kolikoiden m‰‰r‰
	private int coinstotal;						//Kartassa olevien kolikoiden kokonaism‰‰r‰
	
	
	private Text timetxt, coinstxt;				//Ajastimen ja kolikoiden Text
	
	/**
	 * @param lvl			Tason nimi
	 * @param difficulty	Vaikeusaste
	 * @param deflvl		Onko taso alkuper‰iskentt‰ vai pelaajan itse luoma
	 */
	public Game(String lvl, int difficulty, boolean deflvl) {
		this.lvl = lvl;
		this.difficulty = difficulty;
		this.deflvl = deflvl;
		
		bt = new BlockType();
		save = new StoreScore(Main.name);
		
		
		gameView = new Pane();
		
		setStyle("-fx-background-color: linear-gradient(to bottom, #2288dc 0%,#7db9e8);");
		
		
		keys = new ArrayList<KeyCode>();
		setKeys();
		
		newGame();

		if(status != null && gameView != null) {
			getChildren().addAll(status,gameView);
			status.toFront();
			
		}
		
	}
	
	/**
	 * Uusi peli
	 */
	public void newGame() {
		
		boolean success = false;
		
		if(deflvl) {
			success = createLevel("file:C:/temp/Hyppy/Levels/"+lvl);
		} else {
			success = createLevel(lvl);
		}
		
		
		if(success) {
			setBlockGroups();
			setTransparentBackgrounds();
			
			run();
			createStatus();
			startTimer();
			
		} else {
			Main.alertError("Failed to load level.", errormsg).setOnCloseRequest(e -> backToLevels());
		
		}
		
		requestFocus();
	}
	
	/**
	 * Alustetaan status
	 */
	public void createStatus() {
		
		status = new VBox(10);
		status.setLayoutY(25);
		
		HBox timebox = new HBox(10);
		timebox.setPadding(new Insets(5,5,5,5));
		timebox.setStyle("-fx-background-color:#fff4e6;");
		
		timetxt= new Text();
		
		timetxt.setFont(Font.font("Calibri",25));
		timetxt.setFill(Color.web("#3c2f2f"));
		
		
		timebox.getChildren().add(timetxt);
		
		status.getChildren().add(timebox);
		
		if(coinstotal > 0) {
			HBox coinbox = new HBox(10);
			coinbox.setStyle("-fx-background-color:#fff4e6;");

			coinbox.setPadding(new Insets(5,5,5,5));
			
			coinstxt = new Text("Coins: 0/"+coinstotal);
			coinstxt.setFont(Font.font("Calibri",25));
			coinstxt.setFill(Color.web("#3c2f2f"));
			coinbox.getChildren().add(coinstxt);
			
			status.getChildren().add(coinbox);
		}		
	}
	
	/**
	 * K‰ynnistet‰‰n animaatiot
	 */
	public void run() {
		
		if(run != null) {
			run.stop();
			run = null;
		}
		
		run = new Timeline(new KeyFrame(Duration.millis(1000/60), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	tick();
            	
            	
            	if(dead || win) {		//Tarkistetaan onko pelaaja kuollut tai voittanut
            		run.stop();
            		timer.stop();
            		
	            	if(dead) {		
	            		gameOver(false);
	            	} else if(win) {	//Tallennetaan pisteet
	            		
	            		StoreScore.readHighscores();
            			if(save.saveScore(lvl, difvalue, difficulty)) {
            				StoreScore.saveHighscores();
            			}
            		
	            		
	            		gameOver(true);
	            	}
            	}
            }
        }));
		
		run.setCycleCount(Timeline.INDEFINITE);
		run.play();
		
		
		if(pattern != null) {
			pattern.stop();
			pattern = null;
		}
		
		//Alustetaan tekstuurien animaatiot
		pattern = new Timeline(new KeyFrame(Duration.millis(1000/3), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	animationTick();
            	
            }
        }));
		
		pattern.setCycleCount(Timeline.INDEFINITE);
		pattern.play();
	}
	
	/**
	 * M‰‰ritell‰‰n n‰ppimien toiminnot
	 */
	public void setKeys() {
		
			setOnKeyPressed(
		        new EventHandler<KeyEvent>()
		        {
		            public void handle(KeyEvent e)
		            {
		            	
		          
			            	if(!keys.contains(e.getCode())) {
			            		keys.add(e.getCode());
			            	}
			         
			            	
							switch(e.getCode()) {
							
							
								case A: 
								case LEFT:
									player.setXdir(-5);
									
									break;
								case D: 
								case RIGHT:
									player.setXdir(5);
									
									break;
									
								case W: 
								case SPACE:
								case UP:
									player.jump();
							
									break;
									
								case R: 
									if(gameOverView != null) {
										getChildren().remove(gameOverView);
									}
									resetGame();
									
									break;
									
								case ESCAPE:
									backToLevels();
									break;
									
								default:
								break;
							}
		            	
	            }
			});
			
				
			setOnKeyReleased(
		        new EventHandler<KeyEvent>()
		        {
		            public void handle(KeyEvent e)
		            {
		            	
		            	keys.remove(e.getCode());
		            	
		            	if(!(keys.contains(KeyCode.A) || keys.contains(KeyCode.D) || keys.contains(KeyCode.LEFT) || keys.contains(KeyCode.RIGHT))) {
		            		player.setXdir(0);
		            	} 

		            }
			});
	}
	

	/**
	 * Luodaan taso
	 * @param path Tason sijainti
	 * @return Kent‰n luonti onnistui
	 */
	public boolean createLevel(String path) {
		boolean success = true;
		
		int goals = 0;
		
		dead = false;
		win = false;
		
		gameView.getChildren().clear();
		
		
		if(blocks != null) {
			gameView.getChildren().removeAll(blocks.getAllBlocks());
			blocks = null;
		}
		
		
		int speed;
		switch(difficulty) {
		case 1: 
			speed = 1;
			break;
		case 2: 
		default:
			speed = 3;
			break;
		
		case 3:
			speed = 8;
			break;
		
		}
		
		blocks = new BlockList();
		Image image = new Image(path);
         
        PixelReader pr = image.getPixelReader();
        
        map = new Block[(int)image.getWidth()][(int)image.getHeight()][2];
        
        for(int x = 0; x < (int) image.getWidth();x++) {
        	for(int y = 0; y < (int) image.getHeight() ;y++) {
        		Block current = null;
        		
        		//L‰hde: https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
        		int argb = pr.getArgb(x, y);
        		
                int r = (argb >> 16) & 0xFF;
                int g = (argb >>  8) & 0xFF;
                int b =  argb        & 0xFF;
        		
				BlockTexture bttemp = bt.getByType(Types.BgAir);
				
        		for(BlockTexture texture : bt.getBlockTypes()) {
        			if(Math.abs(texture.getRed() - r) < 5 && Math.abs(texture.getBlue() - b) < 5 && Math.abs(texture.getGreen() - g) < 5) {
        				bttemp = texture;
        				break;
        			}
        		}
        				
	
				 if(bttemp.getType() == Types.Player) {	//Pelaaja ja pelaajan aloitussijainnin taustapalikka
					 
	                	blocks.getAllBlocks().add(player = new Player(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp));
	                	blocks.getAllBlocks().add(current = new Block(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bt.getByType(Types.BgSpawn)));
	                	current = player;
	                	
	                }  else if(bttemp.getType()== Types.Enemy) {	//Vihollinen
	                	blocks.getAllBlocks().add(current = new Enemy(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp, speed));
	                	
	                } else if(bttemp.getType() == Types.ElevatorVer) {	//Vertikaalinen hissi
	                	blocks.getAllBlocks().add(current = new Elevator(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp, true));
	
	                } else if(bttemp.getType() == Types.ElevatorHor) {	//Horisontaali hissi
	                	blocks.getAllBlocks().add(current = new Elevator(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp, false));
	                	
	                } else if(bttemp.getType() == Types.Brick) {	//Tiili
	                	blocks.getAllBlocks().add(current = new Brick(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp, speed));
	                	 
	                	
	                } else {	//Muut
	                	blocks.getAllBlocks().add(current = new Block(x * Main.objectsize, y * Main.objectsize,Main.objectsize,Main.objectsize, bttemp));
	                	
	                	if(bttemp.getType() == Types.Goal) {
	                		goals++;
	                	} else if(bttemp.getType() == Types.Coin) {
	                		coinstotal++;
	                	} else if(bttemp.getType() == Types.Grass) {
	                		blocks.getAllBlocks().add(new Block(x * Main.objectsize, (y * Main.objectsize)-Main.objectsize/4,Main.objectsize,Main.objectsize/4, bt.getByType(Types.GrassTop)));
	                	}
	                	
	                }
				 
				 if(current != null) {
					 if(current.isBackground()) {
						 map[x][y][1] = current;
						 if(current.getType() == Types.BgSpawn) {
							 map[x][y][0] = player;
						 }
						 
					 } else {
						 map[x][y][0] = current;
					 }
				 }	
        	}	
        }
        
		if(player == null) {
			success = false;
			errormsg += "Player not found\n";
		} 
		
		if(goals == 0) {
			success = false;
			errormsg += "Goal not found\n";
		}
		
        
        return success;
	}
	
	/**
	 * Asettaa palikat oikeisiin ryhmiin.
	 * 
	 * Jos palikka liikkuu asetetaan se omaan listaan, jotta palikka liikkuu myˆs ollessa n‰ytˆn ruudun ulkopuolella.
	 * Jos palikalla on l‰pin‰kyv‰tausta astetaan se omaan listaan, jotta voidaan tarkistaa palikan ymp‰rill‰ olevien palikoiden tausta.
	 * 
	 */
	public void setBlockGroups() {
        
	       for(Block b : blocks.getAllBlocks()) {
	    	   if(b.getType() == Types.Enemy || b.getType() == Types.ElevatorVer || b.getType() == Types.ElevatorHor) {
	    		   blocks.getNonStaticBlocks().add(b);
	    	   }
	    	   
	    	   if(b.getType() == Types.Enemy || b.getType() == Types.ElevatorVer || b.getType() == Types.ElevatorHor || b.getType() == Types.Coin || b.getType() == Types.Torch || b.getType() == Types.BgSpawn || b.getType() == Types.BgSpawn) {
	    		   blocks.getTransparentBlocks().add(b);
	    	   }
	    	   
	    	   if(b.isBackground()) {
	    		   b.toBack();
	    	   }
	       }
	       
	}
     
	/**
	 * Metodi tarkistaa jokaisen l‰pin‰kyv‰taustaisen palikan ymp‰rill‰ olevat palikat ja m‰‰rittelee palikalle taustan. 
	 * 
	 * Esimerkiksi jos kolikko on luolassa t‰ytyy kolikolle asettaa taustaksi BgStone. Ilman t‰t‰ toimenpidett‰ luolassa olevan palikan tausta olisi tyhj‰ (Kuten ulkoilman tausta)
	 * 
	 * Samassa metodissa poistetaan viholliset ja kolikot jos vaikeusaste on "easy", koska erillisess‰ metodissa kaikkien palikoiden uudelleen l‰pik‰yminen veisi turhaan resursseja.
	 */
	public void setTransparentBackgrounds() {

       List<Block> removed = new ArrayList<Block>();
       List<Block> added = new ArrayList<Block>();
       
       for(Block b : blocks.getAllBlocks()) {
    	   Types[] types = new Types[4];
    	   
    	   
    	   Block current = null;
    	   
    	   int x = (int)b.getX()/Main.objectsize;
    	   int y = (int)b.getY()/Main.objectsize;
    	   
    	  
    	   if(blocks.getTransparentBlocks().contains(b)) {
	    	   if(x-1 >= 0 && x+1 < map.length && y-1 >= 0 && y+1 < map[0].length) {
	    		   if(map[x-1][y][1] != null) {
	    			   types[0] = map[x-1][y][1].getType();
	    		   }
	    		   
		   		   if(map[x+1][y][1] != null) {
		   			   types[1] = map[x+1][y][1].getType();
	    		   }
		   		   
		   		   if(map[x][y-1][1] != null) {
		   			   types[2] = map[x][y-1][1].getType();
				   }
		   		   
		   		   if(map[x][y+1][1] != null) {
		   			   types[3] = map[x][y+1][1].getType();
				   }
	    	   }
    	   }
    	   
    	   int stones = 0;
    	   int castles = 0;
    	   for(int i = 0; i < types.length;i++) {
    		   if(types[i] == Types.BgStone) stones++;
    		   if(types[i] == Types.BgCastleBrick) castles++;
    	   }
    	 
    	   if(stones != 0 || castles != 0) {
    		   if(stones > castles) {
    			   added.add(current = new Block(b.getX(), b.getY(),Main.objectsize,Main.objectsize, bt.getByType(Types.BgStone))); 
    		   } else {
    			   added.add(current = new Block(b.getX(), b.getY(),Main.objectsize,Main.objectsize, bt.getByType(Types.BgCastleBrick))); 
    		   }
    		   map[x][y][1] = current;
    		   
    	   }
           
           if(difficulty == 1) {
        	   if(b.getType() == Types.Coin || b.getType() == Types.Enemy) {
        		   removed.add(b);
        	   }
           }
       }
       
       
       
   		if(difficulty == 1) {
   			coinstotal = 0;
   		}
   		
   		
		blocks.getAllBlocks().removeAll(removed);
		blocks.getAllBlocks().addAll(added);

   }


	/**
	 * P‰ivitt‰‰ n‰ytˆn n‰kyv‰t palikat. Liikuttaessa ruudun ulkopuolella olevat palikat h‰vi‰v‰t, jolloin resursseja ei kulu niin paljoa.
	 */
	public void updateVisibleBlocks() {

		List<Block> removedb = new ArrayList<Block>();
		
		for(Block block : blocks.getVisibleBlocks()) {
			if((block.getX() < player.getX() - Main.objectsize*((Main.objectsscreen+3)/2) || 
			block.getX() > player.getX() + Main.objectsize*((Main.objectsscreen+3)/2) ||
			block.getY() < player.getY() - Main.objectsize*(Main.objectsize/3*2+4) ||
			block.getY() > player.getY() + Main.objectsize*(Main.objectsscreen/3+1))) {
			
				removedb.add(block);
				gameView.getChildren().remove(block);
			}
		}

		blocks.getVisibleBlocks().removeAll(removedb);
		
		for(Block block : blocks.getAllBlocks()) {
			if(block.getX() >= player.getX() - Main.objectsize*((Main.objectsscreen+3)/2) && 
			block.getX() <= player.getX() + Main.objectsize*((Main.objectsscreen+3)/2) &&
			block.getY() >= player.getY() - Main.objectsize*(Main.objectsize/3*2+4) &&
			block.getY() <= player.getY() + Main.objectsize*(Main.objectsscreen/3+1)) {
		
				if(!blocks.getVisibleBlocks().contains(block) && !getChildren().contains(block)) {
					gameView.getChildren().add(block);
					blocks.getVisibleBlocks().add(block);
			
					if(block.isBackground() && block.getType() != Types.GrassTop) {
						block.toBack();
					} else if(block.getType() == Types.GrassTop || block.getType() == Types.ElevatorHor || block.getType() == Types.ElevatorVer) {
						block.toFront();
					}
				}
			}
		}
	}

	
	/**
	 *  Nollataan peli, palautetaan oletusarvot
	 */
	public void resetGame() {
		dead = false;
		win = false;
		
		run.play();
		
		startTimer();
		
		player.setJump(false);
		player.resetJumpCount();
		player.setFall(true);
		player.setYdir(0);
		player.resetFallMulti();
		
		
		player.setX(player.getStartX());
		player.setY(player.getStartY());
		
		for(Block b : blocks.getAllBlocks()) {
			if(b.getType() == Types.Enemy) {
				((Enemy) b).resetX();
				((Enemy) b).resetY();
				
			} else if(b.getType() == Types.ElevatorHor || b.getType() == Types.ElevatorVer) {
				((Elevator) b).reset();
			}
		}
		
		if(coinstotal > 0) {
			for(Block b :blocks.getPickedCoins()) {
				b.setVisible(true);
			}
			
			blocks.getPickedCoins().clear();
			
			coinspicked = 0;
			coinstxt.setText("Coins: 0/"+coinstotal);
		}
		
		for(Block b : blocks.getTouchedBricks()) {
			if(b.getType() == Types.Brick) ((Brick) b).reset();
		}
		
		blocks.getTouchedBricks().clear();
		
	}
	
	/**
	 * Peli p‰‰ttyi 
	 * @param victory Voittiko pelaaja
	 */
	public void gameOver(boolean victory) {
	
		gameOverView = new StackPane();
		gameOverView.setMinSize(Main.size, Main.size);
		
		
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color:#fff4e6;");
		
		vbox.setMaxSize(Main.size, Main.size/3);
		vbox.setPadding(new Insets(10,10,10,10));
		
		
		Text header;
		if(victory) {
			header = new Text("Victory!");
		} else {
			header = new Text("Game over!");
		}
		
		
		header.setFont(Font.font("Calibri", Main.size/6));
		header.setFill(Color.web("#854442"));
		
		vbox.getChildren().add(header);
		
		if(victory) {
			Text score = new Text(diftext);
			score.setFill(Color.web("#4b3832"));
			score.setFont(Font.font("Calibri", 30));
			vbox.getChildren().add(score);
			
		}
		
		vbox.setAlignment(Pos.CENTER);
		header.setTextAlignment(TextAlignment.CENTER);
	
		
		Text replay = new Text("Press 'R' to replay!");
		replay.setFont(Font.font("Calibri",25));
		replay.setFill(Color.web("#4b3832"));
		
		Button btnlevels = new Button("Back to menu");
		
		btnlevels.setPrefSize(Main.size/10, Main.size/20);
		btnlevels.setStyle("-fx-background-color:#3c2f2f;-fx-background-radius:0;");
		btnlevels.setTextFill(Color.web("#fff4e6"));
		
		btnlevels.setOnAction(e -> backToLevels());
		
		vbox.getChildren().addAll(replay, btnlevels);
		
		gameOverView.getChildren().add(vbox);
		
		getChildren().add(gameOverView);
		
	}
	
	/**
	 * Kolikon poiminta
	 * @param coin Poimitun kolikon olio
	 */
	public void pickUpCoin(Block coin) {
		blocks.getPickedCoins().add(coin);
		coin.setVisible(false);
		
		coinspicked++;
		
		coinstxt.setText("Coins: "+coinspicked+"/"+coinstotal);
	}
	
	/**
	 *  Siirry takaisin tason valintaan
	 */
	public void backToLevels() {
		getChildren().clear();
		blocks.getAllBlocks().clear();
		blocks.getVisibleBlocks().clear();
		run.stop();
		
		
		Selection ll = new Selection();
		
		Scene scene = new Scene(ll,Main.size,Main.size);
		
		Main.stage.setScene(scene);
		ll.requestFocus();
	}
	
	public void startTimer() {
		starttime = System.currentTimeMillis();
		
		if(timer == null) {
			timer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent e) {
		        	
		        	difvalue = 	System.currentTimeMillis() - starttime;
		        	
		        	diftext = "";
		        	
		        	if(((difvalue / (1000*60)%60)) != 0) {
		        		diftext += String.valueOf((int)((difvalue / (1000*60)%60))) +" mins ";
		        	}
		        	
		        
	        		diftext += String.valueOf((int)((difvalue / 1000)%60)) +"."+String.valueOf(((int)(difvalue%1000)/100))+" seconds";

		        	timetxt.setText("Time " +diftext);
					            	
		        }
		    }));
			
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.play();
		
		} else {
			timer.play();
		}

		
	}
	
	/**
	 * Seuraava tekstuurin animaation askel
	 */
	public void animationTick() {

		for(Block block : blocks.getVisibleBlocks()) {
			if(bt.getByType(block.getType()).hasAnimation()) {
				block.setFill(bt.getByType(block.getType()).getAnimationTexture());
			}
			
		}
		BlockTexture.animationtick();

		if(hue+0.01 == 1) {
			hue = -1;
		} else {
			hue += 0.025;
		}
		
		ca.setHue(hue);
		
		
		player.setEffect(ca);
	
	}
	
	/**
	 * Itse pelin animaation seuraava askel
	 */
	public void tick() {
		
		player.tick();	
		player.toFront();
		
		int x = (int)(player.getX() / Main.objectsize);
		int y = (int)(player.getY() / Main.objectsize);
	
		
		if(x == 0 && player.getXdir() < 0) {
			player.setX((int)(player.getX()+player.getWidth()/6));
		}
		
		
		List<Block> checkblocks = new ArrayList<Block>();
		List<Block> closeblocks = new ArrayList<Block>();
		
		checkblocks.addAll(blocks.getNonStaticBlocks());
		
		
		for(int i = -2; i <= 2;i++) {
			for(int j = -2; j <= 2;j++) {
				if(x+i > 0 && y+j > 0 && x+i < map.length && y+j < map[0].length && map[x+i][y+j][0] != null) {
					if(!checkblocks.contains(map[x+i][y+j][0])) checkblocks.add(map[x+i][y+j][0]);
					closeblocks.add(map[x+i][y+j][0]);
				}
			}
		}
		
		for(Block block : blocks.getTouchedBricks()) {
			((Brick) block).tick();
			
			if(((Brick) block).isBroken()) {
				block.setVisible(false);
			}
		}
		
		
		for(Block block : checkblocks) {
			if(player != block && block.getType() != Types.Torch) {
				
				if(block.getType() == Types.ElevatorHor || block.getType() == Types.ElevatorVer) {
					((Elevator) block).tick();
					
				} else if(block.getType() == Types.Enemy) {
					((Enemy) block).tick();
					
				}
				
				if(closeblocks.contains(block) || (blocks.getVisibleBlocks().contains(block) && (block.getType() == Types.ElevatorHor || block.getType() == Types.ElevatorVer || block.getType() == Types.Enemy))) {
				
					if(block.getType() == Types.Lava) {
						if(block.getBoundsInParent().intersects(player.collideBottom().getMinX()+player.getWidth()/5,player.collideBottom().getMaxY()+2.5,player.collideBottom().getWidth()/5, player.collideBottom().getHeight())) {
							this.dead = true;
						}
						
					} else if(block.getType() == Types.LavaBrick) {
						if(block.getBoundsInParent().intersects(player.getBoundsInParent())) this.dead = true;
						
					} else if(block.getType() == Types.Enemy) {
						if(player.getBoundsInParent().intersects(block.getBoundsInParent()) && difficulty != 1) this.dead = true;
						
					} else if(block.getType() == Types.Goal && coinstotal == coinspicked) {
						if(player.getBoundsInParent().intersects(block.getBoundsInParent())) this.win = true;
						
					} else if(block.getType() == Types.Coin) {
						if(difficulty != 1 && !blocks.getPickedCoins().contains(block) && player.getBoundsInParent().intersects(block.getBoundsInParent())) pickUpCoin(block);
						
					} else if(block.getType() != Types.Brick || (block.getType() == Types.Brick && !((Brick) block).isBroken()))  {	
						if(player.collideTop().intersects(block.getBoundsInParent())) {
							if(block.getType() != Types.ElevatorVer && block.getType() != Types.ElevatorHor) {
								player.setYdir(0);
								player.setY(block.getY()+block.getHeight());
								player.setFall(true);
							}
							
						} else {
							if(player.collideBottom().intersects(block.getBoundsInParent())) {
								player.resetFallMulti();
								player.resetJumpCount();
								player.setJump(false);
					
								player.setY(block.getY()-player.getHeight());
								
								if(block.getType() == Types.Brick) {
									if(!blocks.getTouchedBricks().contains(block)) {
										blocks.getTouchedBricks().add(block);
										((Brick) block).brickCollision();
									}
									
								} else if(block.getType() == Types.ElevatorHor || block.getType() == Types.ElevatorVer) {
									
									if(!player.onElevator() && block.getBoundsInParent().intersects(player.collideBottom().getMinX(), player.collideBottom().getMinY(), player.collideBottom().getWidth(), player.collideBottom().getHeight()+(player.getHeight()/2))) {
										player.setElevator((Elevator)block);
									}
								}
								
								
							} else {
								if(player.collideLeft().intersects(block.getBoundsInParent())) {
									player.setX(block.getX()+block.getWidth());
								} else if(player.collideRight().intersects(block.getBoundsInParent())) {
									player.setX(block.getX()-player.getWidth());
								}
							}
						}	
					}
					
					if(player.onElevator() && player.getElevator().getType() != Types.ElevatorHor) {
						if(block.getType() != Types.ElevatorVer && block.getType() != Types.Lava && block.getType() != Types.LavaBrick) {
							if(block.getBoundsInParent().intersects(player.collideTop().getMinX(), player.collideTop().getMinY()+player.getElevator().getYDir(), player.collideTop().getWidth(), player.collideTop().getHeight())) {
								player.getElevator().swapdir();
							}
						}
					}
				}
			}
		}
		
		if(player.onElevator()) {
			if(player.getElevator().getY() != player.getY()+player.getHeight()) {
				player.setElevator(null);
			}
		}
		
		gameView.setTranslateX(Main.size / 2 - player.getWidth()/2 - player.getX());
		gameView.setTranslateY(Main.size * 2/3 + player.getHeight()/2 - player.getY());
			
    	updateVisibleBlocks();
		
	}
}