package HUD;

import Entity.Enemy;
import Entity.Hero;
import Map.TileMap;
import accesories.HeroClass;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainGameWindow {
    protected final int GAMEPANEL_SIZE=480;


    private Stage gameStage;
    private Scene gameScece;
    private AnchorPane gamePane;
    private Canvas gameCanvas;



    TileMap tm = new TileMap(48);

    private Hero savior=new Hero(tm);
    //    private Stage menuStage
    private Enemy enemy=new Enemy(tm);
    public MainGameWindow() {
        initStage();
        keyListener();
    }

    private void keyListener() {
        gameScece.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    savior.setRight(true);
                }
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    savior.setLeft(true);
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    savior.setUp(true);
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    savior.setDown( true);
                }
            }
        });
        gameScece.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    savior.setRight(false);
                }
                if (keyEvent.getCode() == KeyCode.LEFT) {
                    savior.setLeft(false);
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    savior.setUp(false);
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    savior.setDown(false);
                }
            }
        });
    }

    private void initStage() {
        gamePane = new AnchorPane();
        gameCanvas = new Canvas(GAMEPANEL_SIZE, GAMEPANEL_SIZE);

        gamePane.getChildren().add(gameCanvas);
        gameScece = new Scene(gamePane, GAMEPANEL_SIZE, GAMEPANEL_SIZE);
        gameStage = new Stage();
        gameStage.setScene(gameScece);

    }

    public void createNewGame(HeroClass heroClass) throws FileNotFoundException {
        gameStage.show();
        gameloop();
    }

    public Stage getGameStage() {
        return gameStage;
    }



    public void gameloop() throws FileNotFoundException {


        tm.loadTile("src/Map/pipo-map001.png");
        tm.loadMap("src/Map/untitled.tmx");
        tm.setPosition(0.0,0.0);
        savior.setPosition(30,30);
        enemy.setPosition(50,50);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                actionEvent -> {
                    gameCanvas.getGraphicsContext2D().clearRect(0,0,GAMEPANEL_SIZE,GAMEPANEL_SIZE);
                    savior.update();
                    enemy.update(savior);
                    tm.setPosition(savior.getx()-GAMEPANEL_SIZE/2,savior.gety()-GAMEPANEL_SIZE/2);
                    tm.drawMap(gameCanvas.getGraphicsContext2D());
                    tm.drawLayer2(gameCanvas.getGraphicsContext2D());
                    tm.drawLayer3(gameCanvas.getGraphicsContext2D());
                    savior.draw(gameCanvas.getGraphicsContext2D());
                    enemy.draw(gameCanvas.getGraphicsContext2D());


                }
        );
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }



}


