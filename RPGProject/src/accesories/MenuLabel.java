package accesories;

import HUD.Menu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

public class MenuLabel  extends Label {
    public final String FONT_PATh="src/accesories/resources/Kenney Rocket.ttf";
    public MenuLabel(String text)
    {
        setPrefSize(600,400);
        setPadding(Insets.EMPTY);
        setText(text);
        setWrapText(true);
        setFontLabel();
        setAlignment(Pos.TOP_LEFT);
    }

    private void setFontLabel()
    {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATh),23));
        }catch (IOException e)
        {
            setFont(Font.font("Times New Roman",20));
        }
    }
}
