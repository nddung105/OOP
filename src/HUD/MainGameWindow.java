package HUD;

import Entity.Enemy;
import Entity.Hero;
import ImageProcesser.Assets;
import Inventory.Inventory;
import Inventory.res.ItemManager;
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
import javafx.scene.canvas.GraphicsContext;
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


    //item manager
    private ItemManager itemManager= new ItemManager();
    //inventory
    Inventory inven =new Inventory();
    private boolean inventory=false;
    //Map
    TileMap sp = new TileMap(32);
    //player
    private Hero savior=new Hero(sp);

    //    private Stage menuStage
    //enemy
    private Enemy enemy=new Enemy(sp);
    //constructor
    public MainGameWindow() throws FileNotFoundException {
        initStage();
        keyListener();

    }

    public void keyListener() {
        gameScece.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.E) {
                    inventory=!inventory;
                    inven.setActive(inventory);
                }
                if(!inventory)
                {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D){
                    savior.setRight(true);
                }
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        savior.setLeft(true);
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        savior.setUp(true);
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        savior.setDown(true);
                    }
                }
                else {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        if (inven.getSelectedItem()!=35)
                            inven.setSelectedItem(inven.getSelectedItem()+1);
                        else inven.setSelectedItem(0);
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A ) {
                        if (inven.getSelectedItem()!=0)
                            inven.setSelectedItem(inven.getSelectedItem()-1);
                        else inven.setSelectedItem(35);
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W ) {
                        if(inven.getSelectedItem()>=9)
                            inven.setSelectedItem(inven.getSelectedItem()-9);
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        if(inven.getSelectedItem()<=26)
                        inven.setSelectedItem(inven.getSelectedItem()+9);
                    }
                }

            }
        });
        gameScece.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!inventory)
                {
                if (keyEvent.getCode() == KeyCode.RIGHT||keyEvent.getCode() == KeyCode.D) {
                    savior.setRight(false);
                }
                if (keyEvent.getCode() == KeyCode.LEFT||keyEvent.getCode() == KeyCode.A) {
                    savior.setLeft(false);
                }
                if (keyEvent.getCode() == KeyCode.UP||keyEvent.getCode() == KeyCode.W) {
                    savior.setUp(false);
                }
                if (keyEvent.getCode() == KeyCode.DOWN||keyEvent.getCode() == KeyCode.S) {
                    savior.setDown(false);
                }
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


        sp.loadTile("src/Map/tileshet.png");
        sp.loadMap("src/Map/RPG/spawnDesert.tmx");
        sp.setPosition(0.0,0.0);
        savior.setPosition(sp.spawnX,sp.spawnY);
        enemy.setPosition(50,50);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                actionEvent -> {
                    gameCanvas.getGraphicsContext2D().clearRect(0,0,GAMEPANEL_SIZE,GAMEPANEL_SIZE);
                    savior.update();
                    enemy.update(savior);
                    sp.setPosition(savior.getx()-GAMEPANEL_SIZE/2,savior.gety()-GAMEPANEL_SIZE/2);
                    sp.drawMap(gameCanvas.getGraphicsContext2D());
                    sp.drawLayer2(gameCanvas.getGraphicsContext2D());
                    sp.drawLayer3(gameCanvas.getGraphicsContext2D());
                    savior.draw(gameCanvas.getGraphicsContext2D());
                    enemy.draw(gameCanvas.getGraphicsContext2D());
                    inven.render(gameCanvas.getGraphicsContext2D());


                }
        );

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

    public Inventory getInven() {
        return inven;
    }

    public void setInven(Inventory inven) {
        this.inven = inven;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }
}


