package accesories;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

public class RPGSubSence extends SubScene {
    boolean isVisible=false;
    TranslateTransition transition=new TranslateTransition();
    public RPGSubSence() throws FileNotFoundException {


        super(new AnchorPane(),500,500);
        Image image=new Image(new FileInputStream("src/accesories/resources/paper background.png"),500,500,false,true);
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        AnchorPane currentScece= (AnchorPane) this.getRoot();
        currentScece.setBackground(new Background(backgroundImage));
        setLayoutX(1300);
        setLayoutY(100);


    }
    public void transition()
    {
        transition.setDuration(Duration.millis(200));
        transition.setNode(this);
        if(isVisible==false)
        {
            transition.setToX(-1000);
            isVisible=true;
        }
        else
        {
            transition.setToX(0);
            isVisible=false;
        }
        transition.play();
    }
    public void optionTransition()//in pause-resume
    {
        transition.setDuration(Duration.millis(200));
        setHeight(100);
        setWidth(150);

        transition.setNode(this);
        if(isVisible==false)
        {
            transition.setToX(-1200);
            isVisible=true;
        }
        else
        {
            transition.setToX(0);
            isVisible=false;
        }
        transition.play();
    }

    public boolean getVisible()
    {
        return isVisible;
    }
    public AnchorPane getAnchorRoot()
    {
        return (AnchorPane)this.getRoot();
    }
}
