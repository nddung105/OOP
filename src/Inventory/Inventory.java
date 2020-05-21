package Inventory;

import Entity.Hero;
import HUD.MainGameWindow;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Inventory {

    private  ArrayList<Item> inventoryItems;


    //character
    private Image character = new Image(new FileInputStream("src\\Entity\\monkMovingFrames\\tile000.png"));
    private boolean active=false;
    //text
    String itemname,itemcount ;
    String itemtype,itemdame,itemhealth,itemdef,itemcrit,itemattackspeed,itemmagicaldame;

    private int invX=90,invY=90,
                invWidth=300,invHeight=300;
    private int invImageX =498, invImageY =160,
            invImageWidth =80, invImageHeight =80;
    private int charX=135,charY=90,
                charWidth=100,charHeight=140;
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
        addItem(Item.muphuthuy.createNewCap(1));
        addItem(Item.giaycodong.createNewBoot(1));
        addItem(Item.giapgai.createNewCap(2));

    }

    public void tick(){

        
    }

    public void render(GraphicsContext g){
        if(!active)
            return;
        g.drawImage(inventoryScreen, invX, invY, invWidth, invHeight);
        g.drawImage(character,charX,charY,charWidth,charHeight);

        int len = inventoryItems.size();

        for(int i=0;i<72;i++) {
            if(i<len)
            if (i<36){
                if(i<9)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * i, itemY, itemWidth, itemHeight);
                else if(9<=i&&i<18)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-9), itemY+32.5, itemWidth, itemHeight);
                else if(18<=i&&i<27)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-18), itemY+32.5*2, itemWidth, itemHeight);
                else if(27<=i&&i<36)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-27), itemY+32.5*3, itemWidth, itemHeight);
            }
            else if (i<45){
                if(i<18)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-9), itemY, itemWidth, itemHeight);
                else if(18<=i&&i<27)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-18), itemY+32.5, itemWidth, itemHeight);
                else if(27<=i&&i<36)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-27), itemY+32.5*2, itemWidth, itemHeight);
                else if(36<=i&&i<45)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-36), itemY+32.5*3, itemWidth, itemHeight);
            }
            else if (i<54){
                if(i<27)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-18), itemY, itemWidth, itemHeight);
                else if(27<=i&&i<36)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-27), itemY+32.5, itemWidth, itemHeight);
                else if(36<=i&&i<45)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-36), itemY+32.5*2, itemWidth, itemHeight);
                else if(45<=i&&i<54)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-45), itemY+32.5*3, itemWidth, itemHeight);
            }
            else if (i<63){
                if(i<36)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-27), itemY, itemWidth, itemHeight);
                else if(36<=i&&i<45)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-36), itemY+32.5, itemWidth, itemHeight);
                else if(45<=i&&i<54)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-45), itemY+32.5*2, itemWidth, itemHeight);
                else if(54<=i&&i<63)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-54), itemY+32.5*3, itemWidth, itemHeight);
            }
            else if (i<72){
                if(i<45)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-36), itemY, itemWidth, itemHeight);
                else if(45<=i&&i<54)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-45), itemY+32.5, itemWidth, itemHeight);
                else if(54<=i&&i<63)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-54), itemY+32.5*2, itemWidth, itemHeight);
                else if(63<=i&&i<72)
                    g.drawImage(inventoryItems.get(i).getTexture(), itemX + 30.5 * (i-63), itemY+32.5*3, itemWidth, itemHeight);
            }
        }
        //draw hightlight
        if(selectedItem<9)
            g.drawImage(hightlight,itemX+selectedItem*30.5,itemY,itemWidth,itemHeight);
        else if(9<=selectedItem&&selectedItem<18)
            g.drawImage(hightlight,itemX+(selectedItem-9)*30.5,itemY+32.5,itemWidth,itemHeight);
        else if(18<=selectedItem&&selectedItem<27)
            g.drawImage(hightlight,itemX+(selectedItem-18)*30.5,itemY+32.5*2,itemWidth,itemHeight);
        else if(27<=selectedItem&&selectedItem<36)
            g.drawImage(hightlight,itemX+(selectedItem-27)*30.5,itemY+35*3,itemWidth,itemHeight);
        //draw hien thi
        if(selectedItem<len)
        {
            g.drawImage(inventoryItems.get(selectedItem).getTexture(),
                itemSelectedX,itemSelectedY,itemSelectedWidth,itemSelectedHeight);

            itemname=inventoryItems.get(selectedItem).getName();
            itemcount=String.valueOf(inventoryItems.get(selectedItem).getCount());
            g.setFont(Font.font(null, FontWeight.MEDIUM, 10));
            g.strokeText(itemname,300,130);
            g.setFont(Font.font(null, FontWeight.MEDIUM, 18));
            g.fillText(itemcount,340,175);

        }
        //draw item equipped
        for (int i=0;i<36;i++){
            if(i<len) {
                if (inventoryItems.get(i).isEquipped() == true) {
                    if (inventoryItems.get(i).getType().compareTo("Weapon") == 0)
                        g.drawImage(inventoryItems.get(i).getTexture(), 103, 136.5, itemWidth, itemHeight);
                    else if (inventoryItems.get(i).getType().compareTo("Cap") == 0)
                        g.drawImage(inventoryItems.get(i).getTexture(), 103, 105, itemWidth, itemHeight);
                    else if (inventoryItems.get(i).getType().compareTo("Armor") == 0)
                        g.drawImage(inventoryItems.get(i).getTexture(), 103, 167, itemWidth, itemHeight);
                    else if (inventoryItems.get(i).getType().compareTo("Boot") == 0)
                        g.drawImage(inventoryItems.get(i).getTexture(), 103, 200, itemWidth, itemHeight);
                }
            }
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

    public ArrayList<Item> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(ArrayList<Item> inventoryItems) {
        this.inventoryItems = inventoryItems;
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
    public Item selectedItem(){
        return inventoryItems.get(selectedItem);
    }

}
