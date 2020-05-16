package Inventory;

import Entity.MapObject;
import ImageProcesser.Assets;
import Map.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class  Item {

    public static Item[] items= new Item[592];

    public final int WIDTH=32,HEIGHT=32;
    //Tileset
    private final int TILESIZE=32;
    protected Image texture;
    private int numTileHorizontal;
    private int numTileVertical;
    private static Tile[][] tiles;
    //Image
    private Image tileset;
    protected String name;
    protected String type;
    protected int dame;
    protected int health;
    protected int def;
    protected int crit;
    protected double attackspeed;
    protected int magicaldame;
    protected int magicaldef;
    protected int hphealingpersec;
    protected int mphealingpersec;
    protected int timehealing;
    protected double speedup;
    protected double powerup;
    protected final int id;

    protected Rectangle bounds;

    protected int x,y,count;
    protected boolean pickedUp=false;



    public static Item kiemcui = new Item(Assets.kiemcui,"Kiem Cui","Weapon",300,20,0,1.1,2);
    public static Item kiemhong = new Item(Assets.kiemhong,"Kiem Hong","Weapon",100,0,0,1.0,1);
    //method

    //Khai bao danh cho Gang Tay &&Giap &&Nhan && Shield
    public Item(Image texture,String name,String type,int health,int def,int magicaldef,int id){

        this.texture=texture;
        this.name=name;
        this.type =type;
        this.health=health;
        this.magicaldef=magicaldef;
        this.def=def;
        this.id=id;
        count=1;

        bounds = new Rectangle(x,y,WIDTH,HEIGHT);
        items[id]=this;
    }
    // Khai bao danh cho Kiem && Guom && Gay && Cung &Giao
    public Item(Image texture,String name,String type,int dame,int crit,int magicaldame,double attackspeed,int id){

        this.texture=texture;
        this.name=name;
        this.type =type;
        this.dame=dame;
        this.crit=crit;
        this.magicaldame=magicaldame;
        this.attackspeed=attackspeed;
        this.id=id;

        count=1;

        bounds = new Rectangle(x,y,WIDTH,HEIGHT);
        items[id]=this;
    }
    //Khai bao danh cho Vat Pham Bo Tro
    public Item(Image texture,String name,String type,int hphealingpersec,int mphealingpersec,int timehealing,double speedup,double powerup,int id){

        this.texture=texture;
        this.name=name;
        this.type =type;
        this.hphealingpersec=hphealingpersec;
        this.mphealingpersec=mphealingpersec;
        this.timehealing=timehealing;
        this.speedup=speedup;
        this.powerup=powerup;
        this.id=id;
        count=1;

        bounds = new Rectangle(x,y,WIDTH,HEIGHT);
        items[id]=this;
    }

    public void tick() throws FileNotFoundException {
    }
    public void render(GraphicsContext g){
        render(g,(int)x,(int)y);
    }

    public void render(GraphicsContext g,int x,int y){
        g.drawImage(texture,x,y,WIDTH,HEIGHT);
    }


    //Image texture,String name,String type,int dame,int crit,int magicaldame,double attackspeed,int id
    public Item createNewWeapon(int count){
        Item i= new Item(texture,name,type,dame,crit,magicaldame,attackspeed,id);
        i.setPickedUp(true);
        i.setCount(count);
        return i;
    }

    public Item createNew(int x,int y){
        Item i =new Item(texture,name,type,dame,crit,magicaldame,attackspeed,id);
        i.setPosition(x, y);
        return i;
    }
    public void setPosition(int x,int y) {
        this.x=x;
        this.y=y;
        bounds.setX(x);
        bounds.setY(y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
}
