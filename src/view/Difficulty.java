package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Vaikeusasteen valitsimen näkymä
 * @author Mikko Suhonen
 *
 */
public class Difficulty extends HBox {
	
	private int difficulty = 2;
	private List<Button> buttons = new ArrayList<Button>();;
	
	
	public Difficulty() {
		setSpacing(5);
		
		setAlignment(Pos.CENTER);
		
		Text diftxt = new Text("Difficulty: ");
		
		diftxt.setFont(Font.font("Calibri",16));
		
		
		diftxt.setFill(Color.web("#fff4e6"));
		
		
		Button easy = new Button("Easy");
		Button medium = new Button("Medium");
		Button hard = new Button("Hard");
		
		buttons.add(easy);
		buttons.add(medium);
		buttons.add(hard);
		
		
		
		easy.setFont(Font.font("Calibri",16));
		medium.setFont(Font.font("Calibri",16));
		hard.setFont(Font.font("Calibri",16));
		
		easy.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		medium.setStyle("-fx-background-color:yellow;-fx-background-radius:0;");
		hard.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		
		
		getChildren().addAll(diftxt, easy, medium, hard);
		
		
		EventHandler<ActionEvent> difchange = new EventHandler<ActionEvent>(){

		    @Override
		    public void handle(ActionEvent e) {
		    	if(e.getSource() == easy) {
		    		difficulty = 1;
		    		
		    		easy.setStyle("-fx-background-color:green;-fx-background-radius:0;");
		    		medium.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		hard.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		
		    	} else if(e.getSource() == medium) {
		    		difficulty = 2;
		    		
		    		easy.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		medium.setStyle("-fx-background-color:yellow;-fx-background-radius:0;");
		    		hard.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		
		    	} else {
		    		difficulty = 3;
		    		
		    		easy.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		medium.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		    		hard.setStyle("-fx-background-color:red;-fx-background-radius:0;");
		    		
		    	}
		    	
		    	
		    }
		};
		
		
		easy.setOnAction(difchange);
		medium.setOnAction(difchange);
		hard.setOnAction(difchange);
	}
	
	/**
	 * Asettaa vaikeusasteen
	 * @param difficulty Vaikeusaste
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Palauttaa vaikeusasteen
	 * @return Vaikeuaste
	 */
	public int getDifficulty() {
		return this.difficulty;
	}
	
	/**
	 * Palauttaa panikkeet
	 * @return Painikkeet
	 */
	public List<Button> getButtons() {
		
		return this.buttons;
	}
}
