package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;

public class Main extends Application {	
	@Override
	public void start(Stage theStage) {
		ViewManager manager = new ViewManager();
		theStage = manager.getStage();
		theStage.setTitle("hello");
		theStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}