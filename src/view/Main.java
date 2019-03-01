package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.LevelLoader;

/**
 * Main-luokka
 * @author Mikko Suhonen
 *
 */
public class Main extends Application {
	
	public static int size;				//Ikkunan koko
	public static int objectsscreen;	//Montako palikkaa mahtuu yhdelle näytölle
	
	public static String name;			//Pelaajan nimi
	public static Stage stage;			
	
	public final static String ROOT_PATH = "C:/temp/Hyppy/";		//Kenttien ja piste-ennätyksien tallennussijainti
	public final static int objectsize = 32;	//Yhden Palikan koko
	
	//Oletus kentät
	public final static String[] defaultlevels = {"volcano", "cavejump", "skyjump", "dungeon", "skyjump2", "clouds", "almost too easy", "castle", "basement"}; 
	
	
	@Override
	public void start(Stage stage) {
		Main.stage = stage;
		
		
		LevelLoader loader = new LevelLoader();
		
		if(loader.load()) {
			
			size = (int)Math.min(Screen.getPrimary().getBounds().getWidth()*0.9, Screen.getPrimary().getBounds().getHeight()*0.9);
			
			objectsscreen = size / objectsize;
			
			
			VBox root = new VBox(size/20);
			root.setAlignment(Pos.CENTER);
			
			HBox hbox = new HBox(5);
			hbox.setAlignment(Pos.CENTER);
			
			
			TextField tf = new TextField();
			tf.setStyle("-fx-background-radius:0;");
			tf.setPrefHeight(30);
			tf.setPrefWidth(size/5);
			
			Button btn = new Button("Play");
			btn.setStyle("-fx-background-color:#3c2f2f;-fx-background-radius:0;");
			btn.setTextFill(Color.web("#fff4e6"));
			btn.setPrefHeight(30);
			
			btn.setFont(Font.font("Calibri",15));
			
			
			EventHandler<ActionEvent> difchange = new EventHandler<ActionEvent>(){
			    @Override
			    public void handle(ActionEvent e) {
			    	
			    	if(!tf.getText().isEmpty()) {
				    	Main.name = tf.getText();
						
						root.getChildren().clear();
						
						Selection ll = new Selection();
						
						
						Scene scene = new Scene(ll, size,size);
						stage.setScene(scene);
			    	}
			    }
		    };
		    
		    btn.setOnAction(difchange);
		    tf.setOnAction(difchange);
	
		    
			
			Text header = new Text("Hyppy");
			header.setFont(Font.font("Calibri",size/6));
			header.setFill(Color.web("#854442"));
			
			
			Text txtname = new Text("Name");
			txtname.setFill(Color.web("#3c2f2f"));
			
			hbox.getChildren().addAll(txtname, tf,btn);
			
			root.getChildren().addAll(header,hbox);
			
			
			Scene scene = new Scene(root, size, size);
			
			
			stage.setScene(scene);
			stage.setTitle("Hyppy");
			stage.show();
			stage.setResizable(false);
			stage.getIcons().add(new Image("res/textures/enemy1.png"));
			
			
			root.setStyle("-fx-background:#fff4e6;");
			
		} else {
			Main.alertError("Folder error", "Could not access folder "+Main.ROOT_PATH);
		}
	
	}
	
	/**
	 * Virhe-viesti
	 * @param sub		Otsikko
	 * @param content	Kuvaus
	 * @return			Alert-luokan olio
	 */
	public static Alert alertError(String sub, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText(sub);
		alert.setContentText(content);
	
		alert.show();
		
		return alert;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
