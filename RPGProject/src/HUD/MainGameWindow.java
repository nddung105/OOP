package HUD;

import Entity.Enemy;
import Entity.Hero;
import Map.TileMap;
import accesories.HeroClass;
import accesories.RPGSubSence;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainGameWindow {
    //HEIGHT 30
    //WITDH 40
    private final int GAMEPANEL_SIZE_X=1000;
    private final int GAMEPANEL_SIZE_Y=700;
    private Stage gameStage;
    private Scene gameScece;
    private AnchorPane gamePane;
    private Canvas gameCanvas;



    TileMap tm = new TileMap(48,1000,700);

    private Hero savior=new Hero(tm);
    //    private Stage menuStage
    private Enemy enemy=new Enemy(tm);
    // Timeline
    private Timeline gameLoop = new Timeline();
    private boolean pause=false;

    public MainGameWindow() {
        initStage();
        keyListener();
        createOptionButton();
    }

    //some nodes of scence have input focus->> pane
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
        gameCanvas = new Canvas(GAMEPANEL_SIZE_X, GAMEPANEL_SIZE_Y);

        gamePane.getChildren().add(gameCanvas);
        gameScece = new Scene(gamePane, GAMEPANEL_SIZE_X, GAMEPANEL_SIZE_Y);
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
                    gameCanvas.getGraphicsContext2D().clearRect(0,0,GAMEPANEL_SIZE_X,GAMEPANEL_SIZE_Y);
                    savior.update();
                    enemy.update(savior);
                    tm.setPosition(savior.getx()-GAMEPANEL_SIZE_X/2,savior.gety()-GAMEPANEL_SIZE_Y/2);
                    tm.drawMap(gameCanvas.getGraphicsContext2D());
                    tm.drawLayer2(gameCanvas.getGraphicsContext2D());
                    tm.drawLayer3(gameCanvas.getGraphicsContext2D());
                    savior.draw(gameCanvas.getGraphicsContext2D());
                    enemy.draw(gameCanvas.getGraphicsContext2D());
                }
        );
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();


    }


    public void createOptionButton()
    {


        Button optionButton=new Button();
        optionButton.setPrefWidth(30);
        optionButton.setPrefHeight(30);
        SettingScece settingScece=new SettingScece(new AnchorPane());
        gamePane.getChildren().addAll(settingScece);


        try{
            //option button
            Image image=new Image(new FileInputStream("src/HUD/Reso/option button.png"),30,30,true,true);
            BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
            optionButton.setBackground(new Background(backgroundImage));
            optionButton.setPadding(Insets.EMPTY);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        gamePane.getChildren().add(optionButton);

        optionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(pause==false)
                {
                    pause=true;
                    gameLoop.stop();
                    gameCanvas.setEffect(new BoxBlur());
                    settingScece.transition();
                }
                else {
                    pause=false;
                    gameLoop.play();
                    gameCanvas.setEffect(null);
                    settingScece.transition();
                }
                gamePane.requestFocus();
            }
        });

        gamePane.requestFocus();
        settingScece.getResumeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pause=false;
                settingScece.transition();
                gameCanvas.setEffect(null);
                gameLoop.play();
                gamePane.requestFocus();
            }
        });


    }
    public boolean isPause()
    {
        return pause;
    }




}


