package HUD;

import accesories.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Menu {
    private AnchorPane anchorPane;
    private Scene scene;
    private Stage primaryWindow;
    private List<RPGButton> menuButtons=new LinkedList<>();
    private RPGButton currentRPGButton=null;
    private RPGSubSence currentRPGSubScece=null;
    public Menu() throws FileNotFoundException {

        anchorPane=new AnchorPane();
        scene=new Scene(anchorPane,1000,700);
        primaryWindow=new Stage();
        primaryWindow.setScene(scene);
        addBackGround();
        createLogo();
        createPlayButton(new RPGButton("Hero"));
        addMenuButton(new RPGButton("New Game"));
        addMenuButton(new RPGButton("Score"));
        addMenuButton(new RPGButton("Load Game"));
        addMenuButton(new RPGButton("Achivement"));
        addMenuButton(new RPGButton("Quit Game"));
    }
    public Stage getPrimaryWindow()
    {
        return primaryWindow;
    }
    private void addBackGround() throws FileNotFoundException {
        Image background=new Image(new FileInputStream("src/accesories/resources/MapDemo.png"),1000,700,false,true);
        BackgroundImage backgroundImage=new BackgroundImage(background,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void addMenuButton(RPGButton button) throws FileNotFoundException {

        RPGSubSence rpgSubSence=new RPGSubSence();
        anchorPane.getChildren().addAll(button);
        AnchorPane.setTopAnchor(button,20+menuButtons.size()*150.0); menuButtons.add(button);
        AnchorPane.setLeftAnchor(button,20.0);anchorPane.getChildren().add(rpgSubSence);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                RPGButton pressedButton=(RPGButton)actionEvent.getSource();

               if(currentRPGButton==null)//bam lan dau tien
               {

                   rpgSubSence.transition();
                   currentRPGButton=pressedButton;
                   currentRPGSubScece=rpgSubSence;
               }
               else if(currentRPGButton==pressedButton)
               {
                  rpgSubSence.transition();
               }
               else {

                   if(currentRPGSubScece.getVisible())
                   {
                       currentRPGSubScece.transition();
                       rpgSubSence.transition();
                       currentRPGButton=pressedButton;
                       currentRPGSubScece=rpgSubSence;
                   }
                   else {
                       rpgSubSence.transition();
                       currentRPGButton=pressedButton;
                       currentRPGSubScece=rpgSubSence;
                   }
               }
            }
        });
    }
    private ClassVbox currentChosen=null;
    private void createPlayButton(RPGButton button) throws FileNotFoundException {//with choosing hero scece
        menuButtons.add(button);
        RPGSubSence heroScece=new RPGSubSence();
        MenuLabel menuLabel=new MenuLabel("Choose your hero");
        anchorPane.getChildren().addAll(button,heroScece);
        AnchorPane.setTopAnchor(button,20.0);
        AnchorPane.setLeftAnchor(button,20.0);
        ClassVbox Mage=new ClassVbox(HeroClass.MAGE);
        ClassVbox Assasin=new ClassVbox(HeroClass.ASSASIN);
        ClassVbox Tanker=new ClassVbox(HeroClass.TANKER);
        heroScece.getAnchorRoot().getChildren().addAll(menuLabel,Mage,Assasin,Tanker);

        AnchorPane.setLeftAnchor(Tanker,23.0);
        AnchorPane.setLeftAnchor(Mage,200.0);
        AnchorPane.setLeftAnchor(Assasin,370.0);
        AnchorPane.setTopAnchor(Tanker,250.0);
        AnchorPane.setTopAnchor(Mage,250.0);
        AnchorPane.setTopAnchor(Assasin,250.0);

        // continue
        heroTickBoxInteraction(Mage);
        heroTickBoxInteraction(Assasin);
        heroTickBoxInteraction(Tanker);

        AnchorPane.setLeftAnchor(menuLabel,70.0);
        AnchorPane.setTopAnchor(menuLabel,50.0);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                RPGButton pressedButton=(RPGButton)actionEvent.getSource();

                if(currentRPGButton==null)//bam lan dau tien
                {

                    heroScece.transition();
                    currentRPGButton=pressedButton;
                    currentRPGSubScece=heroScece;
                }
                else if(currentRPGButton==pressedButton)
                {
                    heroScece.transition();
                }
                else {

                    if(currentRPGSubScece.getVisible())
                    {

                        currentRPGSubScece.transition();
                        heroScece.transition();

                        currentRPGButton=pressedButton;
                        currentRPGSubScece=heroScece;

                    }
                    else {
                        heroScece.transition();

                        currentRPGButton=pressedButton;
                        currentRPGSubScece=heroScece;
                    }
                }
            }
        });
    }

    private void heroTickBoxInteraction(ClassVbox heroClass)
    {
        heroClass.getTickBox().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                if (currentChosen == null||currentChosen==heroClass) {
                    currentChosen = heroClass;
                    heroClass.setChosenTickBox(heroClass.isTicked());

                }
                else
                {
                   if(currentChosen.isTicked()==true)
                   {
                       currentChosen.setChosenTickBox(currentChosen.isTicked());
                       currentChosen=heroClass;
                       heroClass.setChosenTickBox(heroClass.isTicked());
                   }
                   else {
                       currentChosen=heroClass;
                       heroClass.setChosenTickBox(heroClass.isTicked());
                   }
                }


            }
        });
    }
    private void createLogo() throws FileNotFoundException {
        Image image=new Image(new FileInputStream("src/HUD/Reso/logo.png"));
        ImageView logo=new ImageView(image);
       logo.setPreserveRatio(false);
        logo.setFitWidth(200);
        logo.setFitHeight(200);
        logo.setLayoutX(450);
        logo.setLayoutY(100);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new Glow());
            }

        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        anchorPane.getChildren().addAll(logo);

    }






}
