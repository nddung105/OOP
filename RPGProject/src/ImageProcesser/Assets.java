package ImageProcesser;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Assets {

    public static Image itemsheet;
    public static Image kiemcui,kiemhong;
    public static Image muphuthuy;
    public static Image giaycodong;
    public static Image giapgai;

    public static void init(){
        {
            try {
                itemsheet = new Image (new FileInputStream("src\\Inventory\\res\\itemsheet.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        SpriteSheet s = new SpriteSheet(itemsheet);
        kiemhong=s.crop(1,0);
        kiemcui =s.crop(2,0);
        muphuthuy=s.crop(7,14);
        giaycodong=s.crop(7,20);
        giapgai=s.crop(6,8);


    }
}
