package view;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controller.BlockTexture;
import controller.BlockType;
import controller.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * Pelaajan omien kenttien tuonti ja ohje n‰kym‰
 * @author Mikko Suhonen
 *
 */
public class Import extends VBox {
	private BlockType blocktypes;
	
	private Difficulty difficulty = new Difficulty();
	
	public Import() {
		
		setSpacing(15);
		
		VBox wrapper = new VBox();
		
		wrapper.setAlignment(Pos.CENTER);
		
		blocktypes = new BlockType();
		
		setAlignment(Pos.TOP_CENTER);
		
		Text header = new Text("Import level");
		header.setFont(Font.font("Calibri", Main.size/8));
		header.setFill(Color.web("#854442"));
		
		
		Button btnback = new Button("Back");
		
		btnback.setPrefSize(Main.size/10, Main.size/20);
		btnback.setStyle("-fx-background-color:#3c2f2f;-fx-background-radius:0;");
		btnback.setTextFill(Color.web("#fff4e6"));
		
		btnback.setOnAction(e -> backToLevels());
		
		wrapper.getChildren().addAll(createScrollView(),createFileUploader());
		wrapper.setMaxWidth(Main.size/2);
		
		wrapper.setStyle("-fx-background-color:#3c2f2f;-fx-background:#3c2f2f;"+
                "-fx-border-style: solid outside;" + 
                "-fx-border-width: 5;" +
                "-fx-border-color: #3c2f2f;");
		

		wrapper.setAlignment(Pos.CENTER);
		wrapper.setMinHeight(Main.size/2+Main.size/4);
		wrapper.setMaxHeight(Main.size/2+Main.size/4);
		
		
		getChildren().addAll(header, wrapper, btnback);
		
		

		//setMaxWidth(Main.size/3);

		setStyle("-fx-background:#fff4e6;");
		
		setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ESCAPE) backToLevels();
		});

	}
	
	/**
	 * Luo ScrollPanen jossa n‰ytet‰‰n palikoiden tyypit ja v‰rit
	 * @return Luotu ScrollPane
	 */
	public ScrollPane createScrollView() {
		
		ScrollPane sp = new ScrollPane();
		
		sp.setFitToWidth(true);
		
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setStyle("-fx-background-color:#3c2f2f;-fx-background:#3c2f2f;");
		
		
		
		VBox wrapper = new VBox(5);
		
		wrapper.setAlignment(Pos.CENTER);
		
		
			List<BlockTexture> blocks = blocktypes.getBlockTypes();


			List<BlockTexture> temp = new ArrayList<BlockTexture>();

			while(blocks.size() != 0) {
				BlockTexture min = blocks.get(0);
				int mini = 0;
				
				for(int i = 0; i < blocks.size(); i++) {
					if(blocks.get(i).getType().toString().compareTo(min.getType().toString()) < 0) {
						min = blocks.get(i);
						mini = i;
					} 
				}
				temp.add(blocks.get(mini));
				blocks.remove(blocks.get(mini));
			}
			
			blocks = temp;
		
				
		
			for(BlockTexture block : blocks) {
				if(block.isDrawable()) {
					HBox hb = new HBox();
					hb.setPadding(new Insets(5,5,5,5));
					
					
					HBox hb1 = new HBox();
					
					
					
					HBox hb2 = new HBox();
		
					
					HBox.setHgrow( hb, Priority.ALWAYS );
					HBox.setHgrow( hb2, Priority.ALWAYS );
	
					hb1.setAlignment(Pos.CENTER_LEFT);
					hb2.setAlignment(Pos.CENTER_RIGHT);
					
					hb.setStyle("-fx-background-color:#fff4e6;");
					
					
					Rectangle pixelcolor = new Rectangle(Main.objectsize,Main.objectsize);
					pixelcolor.setStroke(Color.BLACK);
					pixelcolor.setFill(Color.color((double)block.getRed()/255, (double)block.getGreen()/255, (double)block.getBlue()/255));
					
					
					GridPane info = new GridPane();
					info.setVgap(5);
					info.setHgap(15);
					
					info.add(new Text("Type"),0,0);
					info.add(new Text("Texture"),0,1);
					info.add(new Text("Map color"),0,2);
					
					info.add(new Text(block.getType().toString()),1,0);
					
					if(block.hasAnimation()) {
						HBox hbox = new HBox(5);
						
						for(int i = 0; i < 4;i++) {
							Rectangle rect = new Rectangle(Main.objectsize,Main.objectsize);
							rect.setFill(block.getAnimationTexture());
							hbox.getChildren().add(rect);
							BlockTexture.animationtick();
						}
						info.add(hbox,1,1);
						
					} else {
						Rectangle rect = new Rectangle(Main.objectsize,Main.objectsize);
						if(block.getTexture() != null) {
							rect.setFill(block.getTexture());
						} else {
							rect.setFill(Color.WHITE);
						}
						
						info.add(rect,1,1);
					}
					
					
					info.add(pixelcolor,1,2);
					
					
					GridPane rgb = new GridPane();
					rgb.setVgap(5);
					rgb.setHgap(5);
					
					
					TextField red = new TextField();
					
					
					red.setText(String.valueOf(block.getRed()));
					red.setEditable(false);
				
					
					TextField green = new TextField();
					green.setText(String.valueOf(block.getGreen()));
					green.setEditable(false);
					
					TextField blue = new TextField();
					blue.setText(String.valueOf(block.getBlue()));
					blue.setEditable(false);
					
					rgb.add(new Text("Red"), 0, 0);
					rgb.add(red, 1, 0);
					
					rgb.add(new Text("Green"), 0, 1);
					rgb.add(green, 1, 1);
					
					rgb.add(new Text("Blue"), 0, 2);
					rgb.add(blue, 1, 2);
					
					
					hb1.getChildren().add(info);
					hb2.getChildren().add(rgb);
					
					hb.getChildren().addAll(hb1,hb2);
				
					wrapper.getChildren().add(hb);
				}
				
			}
			
			sp.setContent(wrapper);


			return sp;
	}
	
	/**
	 * Luodaan tiedostojen tuonti n‰kym‰
	 * @return N‰kym‰
	 */
	public HBox createFileUploader() {

		HBox wrapper = new HBox(20);
		wrapper.setMaxWidth(Main.size/2);
		
		
		wrapper.setPadding(new Insets(10,10,10,10));
		
		wrapper.setStyle("-fx-background-color:#3c2f2f;");
		
		wrapper.setAlignment(Pos.CENTER);

		HBox vbo = new HBox(10);
		vbo.setPadding(new Insets(5,5,5,5));
		
		
		vbo.setAlignment(Pos.CENTER);
		
		wrapper.getChildren().add(difficulty);
		
		Button upload = new Button("Upload");
	
		upload.setStyle("-fx-background-color:#fff4e6;-fx-background-radius:0;");
		upload.setFont(Font.font("Calibri",16));
		
		
		upload.setPrefSize(75, 30);
	
		
		upload.setOnAction(e -> {
			
			String errors = "";
			
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File(Main.ROOT_PATH+"Levels/"));
			fc.setTitle("Import level");

			File file = fc.showOpenDialog(Main.stage);
			

			if(file != null) {
				if(!file.getName().contains(".bmp")) {
					errors += "Wrong file format! You must use .bmp\n";
					
				}
			
				if(errors.isEmpty()) {
					loadLevel(file.toURI().toString());
				} else {
					Main.alertError("Failed to load level.", "Errors\n"+errors);

				}
			}
			
		});
		

		wrapper.getChildren().addAll(vbo, upload);
		
		return wrapper;
	}
	
	/**
	 * Ladataan tuotu taso
	 * @param path Tuodun tason sijainti
	 */
	private void loadLevel(String path) {
		getChildren().clear();
		
		Game level = new Game(path, difficulty.getDifficulty(), false);
		
		Scene scene = new Scene(level, Main.size, Main.size);
		Main.stage.setScene(scene);
		
		level.requestFocus();
	}
	
	/**
	 * Palataan takaisin kent‰n valintaan
	 */
	public void backToLevels() {
		getChildren().clear();
		
		Selection ll = new Selection();
		
		Scene scene = new Scene(ll,Main.size,Main.size);
		
		Main.stage.setScene(scene);
		ll.requestFocus();
	}
}
