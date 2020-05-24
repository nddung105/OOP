package HUD;

import Entity.Enemy;
import Entity.Hero;
import Inventory.res.ItemManager;
import Map.TileMap;
import accesories.HeroClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainGameWindow {
    public  final int GAMEPANEL_SIZE_WIDTH=32*30;
    public  final int GAMEPANEL_SIZE_HEIGHT=32*25;
    private int currentmap=0;


    private Stage gameStage;
    private Scene gameScece;
    private AnchorPane gamePane;
    private Canvas gameCanvas;


    //item manager
    private ItemManager itemManager= new ItemManager();
    //inventory

    private boolean inventory=false;
    //Map
    TileMap tm = new TileMap(32,GAMEPANEL_SIZE_WIDTH,GAMEPANEL_SIZE_HEIGHT);
    //player
    private Hero savior=new Hero(tm);

    //    private Stage menuStage

    private Enemy enemy1=new Enemy(tm,15,savior);
    //constructor

    // Timeline
    private Timeline gameLoop = new Timeline();
    private boolean pause=false;



    public MainGameWindow() throws FileNotFoundException {
        initStage();
        keyListener();
        createOptionButton();
    }

    public void keyListener() {
        gameScece.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.E) {
                    inventory=!inventory;
                    savior.getInven().setActive(inventory);
                }
                if(!inventory)
                {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D){
                    savior.setRightButtonPressed(true);
                        System.out.println("right");
                }
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        savior.setLeftButtonPressed(true);
                        System.out.println("left");
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        savior.setUpButtonPressed(true);
                        System.out.println("up");
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        savior.setDownButtonPressed(true);

                        System.out.println("down");
                    }
                }
                else {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        if (savior.getInven().getSelectedItem()!=35)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()+1);
                        else savior.getInven().setSelectedItem(0);
                    }
                    else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A ) {
                        if (savior.getInven().getSelectedItem()!=0)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()-1);
                        else savior.getInven().setSelectedItem(35);
                    }
                    else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W ) {
                        if(savior.getInven().getSelectedItem()>=9)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()-9);
                    }
                    else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        if(savior.getInven().getSelectedItem()<=26)
                        savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()+9);
                    }
                    else if(keyEvent.getCode()==KeyCode.ENTER){
                        if(savior.getInven().getSelectedItem()<savior.getInven().getInventoryItems().size()) {
                            for(int i=0;i<savior.getInven().getInventoryItems().size();i++){
                                if(savior.getInven().getInventoryItems().get(i).getType().compareTo( savior.getInven().selectedItem().getType())==0 &&
                                savior.getInven().getInventoryItems().get(i).isEquipped()==true && i!=savior.getInven().getSelectedItem()){
                                    savior.getInven().getInventoryItems().get(i).setEquipped(!savior.getInven().getInventoryItems().get(i).isEquipped());
                                    savior.getInven().getInventoryItems().get(i).setAddedindex(false);
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Weapon")==0) savior.returnWeaponIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Cap")==0) savior.returnCapIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Boot")==0) savior.returnBootIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Armor")==0) savior.returnArmorIndex();
                                    System.out.println(savior.getInven().getInventoryItems().get(i).getName() +" is unequipped !");
                                }

                            }
                            savior.getInven().selectedItem().setEquipped(!savior.getInven().selectedItem().isEquipped());
                            if (savior.getInven().selectedItem().isEquipped() == true)
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is equipped !");
                            else {
                                savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).setAddedindex(false);
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Weapon")==0) savior.returnWeaponIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Cap")==0) savior.returnCapIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Boot")==0) savior.returnBootIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Armor")==0) savior.returnArmorIndex();
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is unequipped !");
                            }
                        }
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
                    savior.setRightButtonPressed(false);
                    System.out.println("right relea");
                }
                if (keyEvent.getCode() == KeyCode.LEFT||keyEvent.getCode() == KeyCode.A) {
                    savior.setLeftButtonPressed(false);
                    System.out.println("left release");
                    System.out.println(savior.getRight());

                }
                if (keyEvent.getCode() == KeyCode.UP||keyEvent.getCode() == KeyCode.W) {
                    savior.setUpButtonPressed(false);
                    System.out.println("Up release");
                }
                if (keyEvent.getCode() == KeyCode.DOWN||keyEvent.getCode() == KeyCode.S) {
                    savior.setDownButtonPressed(false);
                    System.out.println("down release");
                }
                }
            }
        });
    }

    private void initStage() {
        gamePane = new AnchorPane();
        gameCanvas = new Canvas(GAMEPANEL_SIZE_WIDTH, GAMEPANEL_SIZE_HEIGHT);

        gamePane.getChildren().add(gameCanvas);
        gameScece = new Scene(gamePane, GAMEPANEL_SIZE_WIDTH, GAMEPANEL_SIZE_HEIGHT);
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

            tm.loadTile("src/Map/tileshet.png");
        if(savior.isDead()==true) {
            currentmap=0;
            savior.setDead(false);
        }
        if (currentmap==0) {
            tm.loadMap("src/Map/RPG/spawnDesert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);//position in tilemap//not in gamePane
            enemy1.setPosition(16, 944);
        }
        else if (currentmap==1){
            tm.loadMap("src/Map/RPG/midDeasert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==2){
            tm.loadMap("src/Map/RPG/Temple.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
           // enemy1.setPosition(50, 50);
        }
        else if (currentmap==3){
            tm.loadMap("src/Map/RPG/underDesert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==4){
            tm.loadMap("src/Map/RPG/Under2desert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
           // enemy1.setPosition(50, 50);
        }
        else if (currentmap==5){
            tm.loadMap("src/Map/RPG/Right1Desert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==6){
            tm.loadMap("src/Map/RPG/aboveright1desert.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==7){
            tm.loadMap("src/Map/RPG/farm.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==8){
            tm.loadMap("src/Map/RPG/underFarm.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
            //enemy1.setPosition(50, 50);
        }
        else if (currentmap==9){
            tm.loadMap("src/Map/RPG/Village.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
           // enemy1.setPosition(50, 50);
        }
        else if (currentmap==10){
            tm.loadMap("src/Map/RPG/rightfarm.tmx");
            tm.setPosition(0.0, 0.0);
            savior.setPosition(tm.spawnX, tm.spawnY);
           // enemy1.setPosition(50, 50);
        }



            KeyFrame kf = new KeyFrame(
                    Duration.seconds(0.017),
                    actionEvent -> {
                        gameCanvas.getGraphicsContext2D().clearRect(0, 0, GAMEPANEL_SIZE_WIDTH, GAMEPANEL_SIZE_HEIGHT);
                        savior.update();
                        if(enemy1.heroInRange()) {
                            enemy1.updatePath();
                            enemy1.detectHero();

                   }
                        tm.setPosition(savior.getx() - GAMEPANEL_SIZE_WIDTH / 2, savior.gety() - GAMEPANEL_SIZE_HEIGHT / 2);
                        tm.drawMap(gameCanvas.getGraphicsContext2D());
                        tm.drawLayer2(gameCanvas.getGraphicsContext2D());
                        tm.drawLayer3(gameCanvas.getGraphicsContext2D());
                        savior.draw(gameCanvas.getGraphicsContext2D());
                        enemy1.draw(gameCanvas.getGraphicsContext2D());
                        savior.getInven().render(gameCanvas.getGraphicsContext2D());

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

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }



}


