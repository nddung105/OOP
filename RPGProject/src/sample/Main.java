package sample;

import HUD.LoadingScence;
import HUD.Menu;
import HUD.refreshMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        refreshMenu menu=new refreshMenu();
        LoadingScence loadingScence=new LoadingScence(menu.getPrimaryWindow());
        primaryStage=loadingScence.getLoadingStage();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
