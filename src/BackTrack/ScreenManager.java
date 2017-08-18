package BackTrack;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenManager {
	static Stage mainStage;
	static AnchorPane pane;
	static ImageView img[];
	static Scene scene;
	static TranslateTransition transition[];
	static int rainhas = Tabuleiro.rainhas;
	public static void show(char[][] tabuleiro) throws IOException{
		img = new ImageView[Tabuleiro.rainhas];
		
		for(int i = 0; i < Tabuleiro.rainhas;i++){
			for(int j = 0; j < 8;j++){
				if(tabuleiro[i][j] == 'R'){
					img[i] = new ImageView(new Image(ScreenManager.class.getResourceAsStream("Queen.png")));
					img[i].fitHeightProperty().set(62.5);
					img[i].fitWidthProperty().set(62.5);
					img[i].setLayoutX(j*63);
					img[i].setLayoutY(i*63);
					break;
				}
			}
		}
		pane = FXMLLoader.load(ScreenManager.class.getResource("tab.fxml"));
		pane.getChildren().addAll(img);
		pane.getChildren().add(TabuleiroGUI.button);
		scene = new Scene(pane, 502, 550);
		mainStage.setScene(scene);
	}
}
