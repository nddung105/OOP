package accesories;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RPGButton  extends Button {
    private final String FONT_LOCATION="src/accesories/resources/Kenney Rocket.ttf";
    public RPGButton(String text)  {
       try{
        setText(text);
       setFont(Font.loadFont(new FileInputStream(FONT_LOCATION),19));
       Image image=new Image(new FileInputStream("src/accesories/resources/buttonLong_beige.png"),190,49,false,true);
       setPadding(Insets.EMPTY);
       BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
       setBackground(new Background(backgroundImage));
       }catch (FileNotFoundException e)
       {
           e.printStackTrace();
       }
       setPrefHeight(49);
       setPrefWidth(190);
       mouseListener();
    }

    private void setBUTTON_PRESSED() throws FileNotFoundException {
//Pressed
        Image image=new Image(new FileInputStream("src/accesories/resources/buttonLong_beige_pressed.png"),190,45,false,true);
        setPadding(Insets.EMPTY);
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
        setPrefHeight(45);
        setPrefWidth(190);
        setWrapText(true);


    }
    private void setIDLE_BUTTON() throws FileNotFoundException {
        //setStyle(BUTTON_IDLE);
        Image image=new Image(new FileInputStream("src/accesories/resources/buttonLong_beige.png"),190,45,false,true);
        setPadding(Insets.EMPTY);
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(backgroundImage));
        setPrefHeight(49);
        setPrefWidth(190);

    }
    private void mouseListener()
    {
        setOnMousePressed(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                try {
                    setBUTTON_PRESSED();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        setOnMouseReleased(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                try {
                    setIDLE_BUTTON();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }




}
