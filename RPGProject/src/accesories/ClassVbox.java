package accesories;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClassVbox extends VBox {
    private HeroClass heroClass;
    private  ImageView heroImage;
    private ImageView tickBox;
    private boolean isChosen;


    public ClassVbox(HeroClass heroClass)  {
        this.heroClass = heroClass;
        Image resoImage;
        try{
            resoImage=new Image(new FileInputStream(heroClass.getUrlClass()));
        }
        catch (IOException e)
        {
            System.out.println("File anh loi");
            e.printStackTrace();
            return;
        }
        heroImage=new ImageView(resoImage);
        try{
        resoImage=new Image(new FileInputStream("src/accesories/resources/blue_button06.png"));
        }
        catch (FileNotFoundException e)
        {
            return;
        }
        tickBox=new ImageView(resoImage);
        this.isChosen=false;
        setAlignment(Pos.CENTER);
        setSpacing(30.0);

        getChildren().add(this.heroImage);
        getChildren().add(this.tickBox);
    }

    public HeroClass getHeroClass()
    {
        return heroClass;
    }
    public boolean isTicked()
    {
        return isChosen;
    }
    public void setChosenTickBox(boolean isChosen)  {
        try{
        //this.tickBox.setImage(null);
        if(isChosen==false)
        {
            this.isChosen=true;
            Image temp=new Image(new FileInputStream("src/accesories/resources/blue_button07.png"));
            this.tickBox.setImage(temp);
        }
        else
        {
            this.isChosen=false;
            Image temp=new Image(new FileInputStream("src/accesories/resources/blue_button06.png"));
            this.tickBox.setImage(temp);
        }}
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    public ImageView getTickBox()
    {
        return tickBox;
    }

}
