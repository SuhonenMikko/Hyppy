package view;

import controller.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.LevelLoader;

/**
 * Kent‰n valinta-n‰kym‰
 * @author Mikko
 *
 */
public class Selection extends VBox {
	
	
	private LevelLoader loader = new LevelLoader();
	private Difficulty difficulty = new Difficulty();;
	
	public Selection() {
		
		VBox root = new VBox(10);
		root.setMinSize(Main.size, Main.size);
		
		FlowPane paneeli = new FlowPane(10,10);
		paneeli.setAlignment(Pos.CENTER);
		
		
		for(String level : loader.getLevels()) {

			VBox wrapper = new VBox(5);
			
			ImageView img = new ImageView(new Image("file:"+Main.ROOT_PATH+"Levels/"+level));
			
			img.setFitHeight(200);
			img.setFitWidth(Main.size/3);
			
			Text title = new Text((level.substring(0, 1).toUpperCase()+level.substring(1)).replace(".bmp", ""));
			
			title.setFill(Color.web("#fff4e6"));
			title.setFont(Font.font("Calibri",15));
			title.setTextAlignment(TextAlignment.CENTER);
			
			BorderPane bp = new BorderPane();
			
			bp.setLeft(title);
			
			Button highscores = new Button("Highscores");
			highscores.setFont(Font.font("Calibri",15));
			
			
			Button play = new Button("Play");
			play.setFont(Font.font("Calibri",15));
			
			play.setOnAction(e -> loadLevel(level));
			highscores.setOnAction(e -> loadScores(level));
			
			HBox hb = new HBox(5);
			hb.getChildren().addAll(highscores,play);
			
			play.setMaxHeight(10);
			highscores.setMaxHeight(10);
			
			play.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
			highscores.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
			
			bp.setRight(hb);
			
			BorderPane.setAlignment(title, Pos.CENTER_LEFT);
			
			wrapper.setStyle("-fx-background-color:#4b3832");
			wrapper.setPadding(new Insets(5,5,5,5));
			wrapper.getChildren().addAll(img,bp);
			
			paneeli.getChildren().add(wrapper);
		}
		

		ScrollPane sp = new ScrollPane();
		
		
		sp.setFitToWidth(true);
	
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		sp.setContent(paneeli);
		
		sp.setStyle("-fx-background-color:transparent;");
		
		Text header = new Text("Levels");
		
		HBox nav = new HBox(20);
		nav.setAlignment(Pos.CENTER);
		nav.setStyle("-fx-background-color:#4b3832;");
		nav.setPadding(new Insets(10,10,10,10));

		
		Button importlvl = new Button("Import level");
		importlvl.setFont(Font.font("Calibri",16));
		importlvl.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		
		importlvl.setOnAction(e -> importLevel());
		
		nav.getChildren().addAll(difficulty, importlvl);
		
		
		header.setFont(Font.font("Calibri", Main.size/6));
		header.setFill(Color.web("#854442"));
			
		root.getChildren().addAll(header, nav, sp);
		sp.setPadding(new Insets(10,10,10,10));
	
		
		root.setAlignment(Pos.TOP_CENTER);
		
		setStyle("-fx-background:#fff4e6;");
		
		getChildren().add(root);
	
	}

	/**
	 * Siirrt‰‰n kent‰n tuonti-n‰kym‰‰n
	 */
	private void importLevel() {
		getChildren().clear();
		
		Import li = new Import();
		
		Scene scene = new Scene(li, Main.size, Main.size);
		Main.stage.setScene(scene);
		
		li.requestFocus();
	}
	
	/**
	 * Siirryt‰‰n kent‰n piste-enn‰tyksiin
	 * @param lvl Kent‰n nimi
	 */
	private void loadScores(String lvl) {
		getChildren().clear();
		
		Highscores hs = new Highscores(lvl);
		
		Scene scene = new Scene(hs, Main.size, Main.size);
		Main.stage.setScene(scene);
		
		hs.requestFocus();
	}
	
	/**
	 * Ladataan valittu taso
	 * @param lvl Tason nimi
	 */
	private void loadLevel(String lvl) {
		getChildren().clear();
		
		Game level = new Game(lvl, difficulty.getDifficulty(), true);
		
		Scene scene = new Scene(level, Main.size, Main.size);
		Main.stage.setScene(scene);
		
		level.requestFocus();
	}
	
}
