package BackTrack;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TabuleiroGUI extends Application{
	public static void main(String[] args){
		launch(args);
	}
	static AtomicBoolean bool;
	AnchorPane pane;
	static ToggleButton button;
	static Linha linha;
	static int frame = 1000;
	@Override
	public void start(Stage primaryStage) throws Exception {
		pane = FXMLLoader.load(getClass().getResource("tab.fxml"));
		button = new ToggleButton();
		button.setLayoutX(411);
		button.setLayoutY(510);
		button.setText("Gerar");
		linha = new Linha(0,0);
		Tabuleiro.zerar();
		ScreenManager.mainStage = primaryStage;
		button.setOnAction(event -> {
			bool.set(button.isSelected());
			try {
                if (bool.get()) {
                    Thread t = new Thread(new Runnable() {
                    	boolean ok = true;
                        public void run() {
                            while (ok && button.isSelected()) {
                                try {
									Thread.sleep(frame);
								} catch (InterruptedException e1) {
							
									e1.printStackTrace();
								}
                                
                                Platform.runLater(() -> {
                                    Linha l = TabuleiroGUI.linha;
                                    if(ok && button.isSelected()){
										Tabuleiro.Start(l);
									}
                                    if(Tabuleiro.rainhas > 7){
                                    	ok = false;
                                    	TabuleiroGUI.button.setSelected(false);
                                    }
                                });
                            }
                        }
                    });
                    t.setDaemon(true);
                    t.start();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
		});
		pane.getChildren().add(button);
		bool = new AtomicBoolean();
		primaryStage.setScene(new Scene(pane));
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
}