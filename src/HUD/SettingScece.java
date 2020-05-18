package HUD;

import accesories.RPGButton;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SettingScece extends SubScene {
    boolean isVisible=false;
    TranslateTransition transition=new TranslateTransition();
    private RPGButton resume;
    public SettingScece(AnchorPane anchorPane)
    {
        super(anchorPane,500,500);
        try {
            Image image = new Image(new FileInputStream("src/HUD/Reso/Asset 115.png"), 500, 500, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

            anchorPane.setBackground(new Background(backgroundImage));
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        setLayoutX(1000);
        setLayoutY(50);
        addButton();


    }
    public void transition()
    {
        transition.setDuration(Duration.millis(200));
        transition.setNode(this);
        if(isVisible==false)
        {
            transition.setToX(-950);
            isVisible=true;
        }
        else
        {
            transition.setToX(0);
            isVisible=false;
        }
        transition.play();
    }
    public void addButton() {


        ImageView imageViewl=null;
        Label label=null;
        try{
            Image pauseLabel=new Image(new FileInputStream("src/HUD/Reso/Asset 122.png"),268,115,true,true);
            imageViewl=new ImageView(pauseLabel);
            label=new Label("PAUSE");
            label.setFont(Font.loadFont(new FileInputStream("src/HUD/Reso/Equestria Bold Italic.otf"),48));

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        resume=new RPGButton("RESUME");

        RPGButton restart=new RPGButton("RESTART");
        RPGButton music=new RPGButton("MUSIC");

        AnchorPane root=(AnchorPane) this.getRoot();
        root.getChildren().addAll(resume,restart,music,imageViewl,label);
        AnchorPane.setTopAnchor(resume,131.0);
        AnchorPane.setLeftAnchor(resume,127.0);
        AnchorPane.setTopAnchor(restart,250.0);
        AnchorPane.setLeftAnchor(restart,127.0);
        AnchorPane.setTopAnchor(music,373.0);
        AnchorPane.setLeftAnchor(music,127.0);
        AnchorPane.setTopAnchor(imageViewl,0.0);
        AnchorPane.setLeftAnchor(imageViewl,104.0);
        AnchorPane.setTopAnchor(label,15.0);
        AnchorPane.setLeftAnchor(label,169.0);




    }
    public SettingScece getScence()
    {
        return this;
    }
    public RPGButton getResumeButton()
    {
        return resume;
    }
}
