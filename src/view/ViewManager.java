package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

public class ViewManager {
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,600,400);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		setBackground() ;
	}
	
	public void setBackground() {
		Image img = new Image("./view/image/background.png",600,400,false,true);
		BackgroundImage background = new BackgroundImage(img, null, null, null, null);
		mainPane.setBackground(new Background(background));
	}
	
	
	public Stage getStage() {
		return mainStage;
	}
	
	
}