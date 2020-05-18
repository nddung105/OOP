package Inventory;

import Entity.Hero;
import HUD.MainGameWindow;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Inventory {

    private  ArrayList<Item> inventoryItems;

    private boolean active=false;
    //text
    Stage stage =new Stage();
    Text itemname = new Text();
            Text itemtype,itemdame,itemhealth,itemdef,itemcrit,itemattackspeed,itemmagicaldame;

    private int invX=90,invY=90,
                invWidth=300,invHeight=300;
    private int invImageX =498, invImageY =160,
            invImageWidth =80, invImageHeight =80;
    private int invTextX= 0,invTextY=0;
    private int invCountX=645 , invCountY=205;
    private double itemX=103,itemY=241,
            itemWidth=30.5,itemHeight=30.5;
    private double itemSelectedX=240 ,itemSelectedY=137,
                    itemSelectedWidth=60,itemSelectedHeight=60;
    private int selectedItem=0;
    //hightlight
    Image hightlight = new Image (new FileInputStream("src\\Inventory\\res\\hightlight.png"),itemWidth,itemHeight,false,true);
    Image inventoryScreen = new Image(new FileInputStream("src\\Inventory\\res\\inventory.png"),invWidth,invHeight,false,true);


    public Inventory() throws FileNotFoundException {
        inventoryItems =new ArrayList<Item>();
        addItem(Item.kiemcui.createNewWeapon(1));
        addItem(Item.kiemhong.createNewWeapon(2));

    }

    public void tick(){

    }

    public void render(GraphicsContext g){
        if(!active)
            return;
        g.drawImage(inventoryScreen, invX, invY, invWidth, invHeight);


        int len = inventoryItems.size();
        for(int i=0;i<35;i++) {
            if (i < len){
                if(i<9)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * i, itemY, itemWidth, itemHeight);
                else if(9<=i&&i<18)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * i, itemY+32.5, itemWidth, itemHeight);
                else if(18<=i&&i<27)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * i, itemY+32.5*2, itemWidth, itemHeight);
                else if(27<=i&&i<36)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * i, itemY+32.5*3, itemWidth, itemHeight);
            }
        }
        if(selectedItem<9)
            g.drawImage(hightlight,itemX+selectedItem*30.5,itemY,itemWidth,itemHeight);
        else if(9<=selectedItem&&selectedItem<18)
            g.drawImage(hightlight,itemX+(selectedItem-9)*30.5,itemY+32.5,itemWidth,itemHeight);
        else if(18<=selectedItem&&selectedItem<27)
            g.drawImage(hightlight,itemX+(selectedItem-18)*30.5,itemY+32.5*2,itemWidth,itemHeight);
        else if(27<=selectedItem&&selectedItem<36)
            g.drawImage(hightlight,itemX+(selectedItem-27)*30.5,itemY+35*3,itemWidth,itemHeight);
        if(selectedItem<len)
        {
            g.drawImage(inventoryItems.get(selectedItem).getTexture(),
                itemSelectedX,itemSelectedY,itemSelectedWidth,itemSelectedHeight);
            /*
            itemname.setText(inventoryItems.get(selectedItem).getName());
            itemname.setX(20.0f);
            itemname.setY(65.0f);
            itemname.setFill(Color.YELLOW);
            itemname.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
            Group group = new Group(itemname);
            Scene scene = new Scene(group, 500, 150, Color.WHITE);
            stage.setTitle(inventoryItems.get(selectedItem).getName());
            stage.setScene(scene);
            stage.show();
            */
        }

    }
    public void addItem(Item item){
        for(Item i : inventoryItems) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
}
