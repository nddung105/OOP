package ImageProcesser;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {
    protected Image sheet;
    private Image subPixel;
    private final int TILESIZE=32;

    public SpriteSheet(Image sheet){
        this.sheet = sheet;
    }

    public Image crop(int x, int y){
        subPixel= new WritableImage(sheet.getPixelReader(),TILESIZE*x,
                TILESIZE*y,
                TILESIZE,TILESIZE);
        return subPixel;
    }
}
