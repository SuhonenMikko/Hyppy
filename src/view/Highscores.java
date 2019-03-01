package view;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MapScore;
import model.StoreScore;

/**
 * Piste-ennätykset näyttävä näkymä
 * @author Mikko
 *
 */
public class Highscores extends VBox {
	
	private String lvl;		//Valittu kenttä
	
	private Difficulty difficulty = new Difficulty();

	private VBox wrapper;
	
	/**
	 * @param lvl Tason nimi
	 */
	public Highscores(String lvl) {
		this.lvl = lvl;

		setAlignment(Pos.TOP_CENTER);
		setSpacing(15);
		
		Text header = new Text("Highscores");
		header.setFont(Font.font("Calibri", Main.size/6));
		header.setFill(Color.web("#854442"));
		
		Text levelheader = new Text("("+(lvl.substring(0, 1).toUpperCase()+lvl.substring(1)).replace(".bmp", "")+")");
		
		levelheader.setFont(Font.font("Calibri", 30));
		levelheader.setFill(Color.web("#3c2f2f"));
		
		Button btnback = new Button("Back");
		
		btnback.setPrefSize(Main.size/10, Main.size/20);
		btnback.setStyle("-fx-background-color:#3c2f2f;-fx-background-radius:0;");
		btnback.setTextFill(Color.web("#fff4e6"));
		
		btnback.setOnAction(e -> backToLevels());
		
		difficulty.setStyle("-fx-background-color:#4b3832;");
		difficulty.setPadding(new Insets(10,10,10,10));
		
		List<Button> buttons = difficulty.getButtons();
		
		
		EventHandler<ActionEvent> difchange = new EventHandler<ActionEvent>(){

		    @Override
		    public void handle(ActionEvent e) {
		    	if(e.getSource() == buttons.get(0)) {
		    		difficulty.setDifficulty(1);
		    		
		    		buttons.get(0).setStyle("-fx-background-color:green;-fx-background-radius:0;");
		    		buttons.get(1).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		buttons.get(2).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		
		    	} else if(e.getSource() == buttons.get(1)) {
		    		difficulty.setDifficulty(2);
		    		
		    		buttons.get(0).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		buttons.get(1).setStyle("-fx-background-color:yellow;-fx-background-radius:0;");
		    		buttons.get(2).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		
		    	} else {
		    		difficulty.setDifficulty(3);
		    		
		    		buttons.get(0).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		buttons.get(1).setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		buttons.get(2).setStyle("-fx-background-color:red;-fx-background-radius:0;");
		    	}
		    	
		    	updateHighscores();
		    	
		    	
		    }
		};
		
		buttons.get(0).setOnAction(difchange);
		buttons.get(1).setOnAction(difchange);
		buttons.get(2).setOnAction(difchange);
		
		VBox content = new VBox();
		content.setMinHeight(Main.size/2+Main.size/10);
		
		
		wrapper = new VBox();
		wrapper.setStyle("-fx-background-color:#3c2f2f;-fx-background:#3c2f2f;"+
                "-fx-border-style: solid outside;" + 
                "-fx-border-width: 5;" +
                "-fx-border-color: #3c2f2f;");
		
		wrapper.setMaxWidth(Main.size/3);

		wrapper.setAlignment(Pos.CENTER);
		
		
		getChildren().addAll(header,levelheader, difficulty);
		
		createScrollView();
		
		content.getChildren().add(wrapper);
		content.setMinHeight(Main.size/2+Main.size/10);
		content.setMaxHeight(Main.size/2+Main.size/10);
		
		
		content.setAlignment(Pos.TOP_CENTER);
		getChildren().addAll(content, btnback);
		
		
		setMaxWidth(Main.size/3);

		setStyle("-fx-background:#fff4e6;");
		
		setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ESCAPE) backToLevels();
		});

	}
	
	/**
	 * Luodaan ScrollPane
	 */
	public void createScrollView() {

		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-background-color:transparent;");
		sp.setFitToWidth(true);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		
		
		VBox content = new VBox(5);
		
		content.setAlignment(Pos.CENTER);
		
		StoreScore.readHighscores();
			
		List<String[]> list = new ArrayList<String[]>();
		
		
		for(StoreScore player : StoreScore.getPlayers()) {

			for(MapScore pm : player.getScores()) {
				
				if(pm.getMap().equals(lvl) && pm.getDifficulty() == difficulty.getDifficulty()) {
					String[] line = {player.getName(), String.valueOf(pm.getTime())};
					list.add(line);
					
				}
			}
		}
		
		
		if(!list.isEmpty()) {
			List<String[]> temp = new ArrayList<String[]>();

			while(list.size() != 0) {
				long min = Long.parseLong(list.get(0)[1]);
				int mini = 0;
				
				for(int i = 0; i < list.size(); i++) {
					if(Long.parseLong(list.get(i)[1]) < min) {
						min = Long.parseLong(list.get(i)[1]);
						mini = i;
					} 
				}
				
				String[] ls = {list.get(mini)[0], list.get(mini)[1]};
				
				temp.add(ls);
				list.remove(list.get(mini));	
			}
			
			list = temp;
			
			for(String[] values : list) {
				HBox line = new HBox();
				line.setPadding(new Insets(5,5,5,5));
				
				
				line.setStyle("-fx-background-color:#fff4e6;");
				
				HBox hb1 = new HBox();
				HBox hb2 = new HBox();
				
				HBox.setHgrow( hb1, Priority.ALWAYS );
				HBox.setHgrow( hb2, Priority.ALWAYS );
				
				hb1.setAlignment(Pos.CENTER_LEFT);
				hb2.setAlignment(Pos.CENTER_RIGHT);
				
				Text txtname = new Text(values[0]);
				
				long time =  Long.parseLong(values[1]);
				
				String diftext = "";
	        	
	        	if(((time / (1000*60)%60)) != 0) {
	        		diftext += String.valueOf((int)((time / (1000*60)%60))) +" mins ";
	        	}
	        	
	        	diftext += String.valueOf((int)((time / 1000)%60)) +"."+String.valueOf(((int)(time%1000)/100) + " seconds");
	        	
				
				Text txtscore = new Text(diftext);
				
				txtname.setFont(Font.font("Calibri",16));
				txtscore.setFont(Font.font("Calibri",16));
				
				
				hb1.getChildren().addAll(txtname);
				hb2.getChildren().add(txtscore);

				line.getChildren().addAll(hb1,hb2);
			
				content.getChildren().add(line);
			}
		} else {			
			HBox line = new HBox();
			line.setAlignment(Pos.CENTER);
			
			line.setPadding(new Insets(5,5,5,5));
			
			line.setStyle("-fx-background-color:#fff4e6;");
			Text nohs = new Text("No highscores.");
			nohs.setFont(Font.font("Calibri",16));
			line.getChildren().add(nohs);
			
			content.getChildren().add(line);
		}
		
		sp.setMinHeight(0);
		//sp.setMaxHeight(Main.size/2+Main.size/10);
		
		sp.setContent(content);
		
		wrapper.getChildren().add(sp);
		
		
	}
	
	/**
	 * Päivitetään näkymä.
	 * .käytetään vaikeusastetta vaihdettaessa.
	 */
	public void updateHighscores() {
		wrapper.getChildren().clear();
		createScrollView();	
		
	}
	
	/**
	 * Palataan takaisin tason valintaan
	 */
	public void backToLevels() {
		getChildren().clear();
		
		Selection ll = new Selection();
		
		Scene scene = new Scene(ll,Main.size,Main.size);
		
		Main.stage.setScene(scene);
		ll.requestFocus();
	}
}
