package ImageProcesser;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Assets {

    public static Image itemsheet;
    public static Image kiemcui,kiemhong;

    public static void init(){
        {
            try {
                itemsheet = new Image (new FileInputStream("src\\Inventory\\res\\itemsheet.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        SpriteSheet s = new SpriteSheet(itemsheet);
        kiemhong=s.crop(0,1);
        kiemcui =s.crop(0,2);

    }
}
