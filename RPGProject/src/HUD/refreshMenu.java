package HUD;

import accesories.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class refreshMenu {
    private AnchorPane anchorPane;
    private Scene scene;
    private Stage primaryWindow;
    private List<RPGButton> menuButtons=new LinkedList<>();
    public refreshMenu() throws FileNotFoundException {
        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, 1000, 700);
        primaryWindow = new Stage();
        primaryWindow.setScene(scene);
        addBackGround();
        createLogo();
        //hero picker module
        RPGButton HeroButton=new RPGButton("Hero");
        addMenuButton(HeroButton);
        RPGSubSence heroPickerScece=createHeroPickerScece();
        addCorrespondSubsence(HeroButton,heroPickerScece);
        createStartButton(heroPickerScece);
        //load game module
        RPGButton loadGame=new RPGButton("Load Game");
        addMenuButton(loadGame);
        addCorrespondSubsence(loadGame,new RPGSubSence());
        //Achivement module
        RPGButton Achivement=new RPGButton("Achivement");
        addMenuButton(Achivement);
        addCorrespondSubsence(Achivement,new RPGSubSence());
        //Score module
        RPGButton Score=new RPGButton("Score");
        addMenuButton(Score);
        addCorrespondSubsence(Score,new RPGSubSence());
        //exit module
        RPGButton Exit=new RPGButton("Exit");
        createExitButton(Exit);
        addMenuButton(Exit);



    }
    public Stage getPrimaryWindow()
    {
        return primaryWindow;
    }
    private void createLogo() throws FileNotFoundException {
        Image image=new Image(new FileInputStream("src/HUD/Reso/contra-png-9.png"));
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
    private void addBackGround() throws FileNotFoundException {
        Image background=new Image(new FileInputStream("src/accesories/resources/MapDemo.png"),1000,700,false,true);
        BackgroundImage backgroundImage=new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void addMenuButton(RPGButton button) throws FileNotFoundException {
        anchorPane.getChildren().addAll(button);
        AnchorPane.setTopAnchor(button,20+menuButtons.size()*150.0); menuButtons.add(button);
        AnchorPane.setLeftAnchor(button,20.0);

    }

    private RPGButton currentRPGButton=null;
    private RPGSubSence currentRPGSubScece=null;

    private void addCorrespondSubsence(RPGButton button,RPGSubSence rpgSubSence) throws FileNotFoundException // with interation( tuong tac giua cac tab)
    {

        anchorPane.getChildren().add(rpgSubSence);
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

    private RPGSubSence createHeroPickerScece() throws FileNotFoundException {
        RPGSubSence heroPickerScece=new RPGSubSence();
        MenuLabel menuLabel=new MenuLabel("Choose your hero");
        ClassVbox Mage=new ClassVbox(HeroClass.MAGE);
        ClassVbox Assasin=new ClassVbox(HeroClass.ASSASIN);
        ClassVbox Tanker=new ClassVbox(HeroClass.TANKER);
        heroPickerScece.getAnchorRoot().getChildren().addAll(menuLabel,Mage,Assasin,Tanker);
        AnchorPane.setLeftAnchor(Tanker,23.0);
        AnchorPane.setLeftAnchor(Mage,200.0);
        AnchorPane.setLeftAnchor(Assasin,370.0);
        AnchorPane.setTopAnchor(Tanker,250.0);
        AnchorPane.setTopAnchor(Mage,250.0);
        AnchorPane.setTopAnchor(Assasin,250.0);
        AnchorPane.setLeftAnchor(menuLabel,70.0);
        AnchorPane.setTopAnchor(menuLabel,50.0);
        heroTickBoxInteraction(Mage);
        heroTickBoxInteraction(Assasin);
        heroTickBoxInteraction(Tanker);
        return heroPickerScece;
    }

    private ClassVbox currentChosen=null;
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
    private void createExitButton(RPGButton exitButton)
    {
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Exiting....");
                Platform.exit();

            }
        });
    }
    public void createStartButton(RPGSubSence heroPickerScece)
    {
        try
        {
            RPGButton rpgButton=new RPGButton("Start");
            heroPickerScece.getAnchorRoot().getChildren().add(rpgButton);
            AnchorPane.setTopAnchor(rpgButton,400.0);
            AnchorPane.setLeftAnchor(rpgButton,273.0);
            MainGameWindow mainGameWindow=new MainGameWindow();

            rpgButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if(currentChosen!=null){

                    primaryWindow.hide();
                        try {
                            mainGameWindow.createNewGame(currentChosen.getHeroClass());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        mainGameWindow.getGameStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            primaryWindow.show();
                        }

                    });}
                }
            });
        }catch (FileNotFoundException e)
        {
            System.out.println("File loi");
            e.printStackTrace();
        }
    }
}