package Entity;

import Inventory.Inventory;
import Map.TileMap;
import accesories.HeroClass;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Hero extends MapObject {
    //hero attributes
    private HeroClass heroClass;
    private final int HEALTH_DEFAULT=100,DAME_DEFAULT=100,
                      DEF_DEFAULT=50,CRIT_DEFAULT=100,
                      MAGICALDAME_DEFAULT=100, MAGICALDEL_DEFAULT=100;
    private final double ATTACKSPEED_DEFAULT=1.0, HPHEALINGPERSEC_DEFAULT=1.0,
                         MPHEALINGPERSEC_DEFAULT=1.0,SPEEDUP_DEFAULT=0,
                         POWERUP_DEFAULT=0;
    private int health=HEALTH_DEFAULT;
    private int level=1;
    private int dame=DAME_DEFAULT;
    private int def=DEF_DEFAULT;
    private int crit=CRIT_DEFAULT;
    private double attackspeed=ATTACKSPEED_DEFAULT;
    private int magicaldame=MAGICALDAME_DEFAULT;
    private int magicaldef=MAGICALDEL_DEFAULT;
    private double hphealingpersec=HPHEALINGPERSEC_DEFAULT;
    private double mphealingpersec=MPHEALINGPERSEC_DEFAULT;
    private double speedup=SPEEDUP_DEFAULT;
    private double powerup=POWERUP_DEFAULT;
    private boolean dead =false;


    //inventory
    Inventory inven = new Inventory();
    //hero animation

    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....

    //action
    private static final int IDLE=0;
    private static final int WALKING=1;
    public Hero(TileMap tileMap) throws FileNotFoundException {
        super(tileMap);

        width=40;
        height=40;
        cheight=28;
        cwidth=23;

        veclocity=0.3;
        maxVec=2.0;

        faceRight=true;
        faceDown=true;
        Image[] movingHero=new Image[4];
        try{



            for(int i=0;i<4;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/monkMovingFrames/tile00"+i+".png"));

                movingHero[i]=new WritableImage(origin.getPixelReader(),0,6,40,44);

            }
            sprites.add(movingHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Image[] idleHero=new Image[2];
        try{
            for(int i=0;i<2;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/monkIdleFrames/tile00"+i+".png"));
                idleHero[i]=new WritableImage(origin.getPixelReader(),0,6,40,44);
            }
            sprites.add(idleHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        animation=new Animation();
        currentAction=IDLE;
        animation.setFrames(movingHero);
        animation.setDuration(0.2);

    }
    public void getNextPosition()
    {
        if(left) {
            dy=0;
            dx -= veclocity;
            if(dx < -maxVec) {
                dx = -maxVec;
            }

        }
        else if(right) {
            dy=0;
            dx += veclocity;
            if(dx > maxVec) {
                dx = maxVec;
            }

        }
        else if(up)
        {
            dx=0;
            dy-=veclocity;
            if(dy < -maxVec) {
                dy = -maxVec;
            }
        }

        else if(down) {
            dx=0;
            dy+=veclocity;

            if(dy > maxVec) {
                dy = maxVec;
            }
        }
        else {
            dx=0;
            dy=0;
        }


    }
    public void returnWeaponIndex(){
        this.dame=DAME_DEFAULT;
        this.crit=CRIT_DEFAULT;
        this.magicaldame=MAGICALDAME_DEFAULT;
        this.attackspeed=ATTACKSPEED_DEFAULT;
    }
    public void returnCapIndex(){
        this.health=HEALTH_DEFAULT;
        this.def=DEF_DEFAULT;
        this.magicaldef=MAGICALDEL_DEFAULT;
    }
    public void returnBootIndex(){
        this.speedup=SPEEDUP_DEFAULT;
        this.powerup=POWERUP_DEFAULT;
    }
    public void returnArmorIndex(){
        this.health=HEALTH_DEFAULT;
        this.def=DEF_DEFAULT;
        this.magicaldef=MAGICALDEL_DEFAULT;
    }

    public void update()
    {
        //update inventory
        for (int i=0;i<inven.getInventoryItems().size();i++){
            if(inven.getInventoryItems().get(i).isEquipped()&&!inven.getInventoryItems().get(i).isAddedindex()){
            if (inven.getInventoryItems().get(i).getType().compareTo("Weapon")==0){
                this.dame=DAME_DEFAULT+inven.getInventoryItems().get(i).getDame();
                this.crit=CRIT_DEFAULT+inven.getInventoryItems().get(i).getCrit();
                this.magicaldame=MAGICALDAME_DEFAULT+inven.getInventoryItems().get(i).getMagicaldame();
                this.attackspeed=ATTACKSPEED_DEFAULT+inven.getInventoryItems().get(i).getAttackspeed();
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Cap")==0){
                this.health=HEALTH_DEFAULT+inven.getInventoryItems().get(i).getHealth();
                this.def=DEF_DEFAULT+inven.getInventoryItems().get(i).getDef();
                this.magicaldef=MAGICALDEL_DEFAULT+inven.getInventoryItems().get(i).getMagicaldef();
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Boot")==0){
                this.speedup=SPEEDUP_DEFAULT+inven.getInventoryItems().get(i).getSpeedup();
                this.powerup=POWERUP_DEFAULT+inven.getInventoryItems().get(i).getPowerup();
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Armor")==0){
                this.health=HEALTH_DEFAULT+inven.getInventoryItems().get(i).getHealth();
                this.def=DEF_DEFAULT+inven.getInventoryItems().get(i).getDef();
                this.magicaldef=MAGICALDEL_DEFAULT+inven.getInventoryItems().get(i).getMagicaldef();
            }
            inven.getInventoryItems().get(i).setAddedindex(true);
            }
        }
        //update position
        getNextPosition();
        checkTileMapCollision();//and update constraint X Y stimultaneously// real X, real Y

        setPosition(constraintX,constraintY); //set Constrain to x,y //real movement of Hero

        //set animation
        if(left||right||up||down)
        {
            if(currentAction!=WALKING)
            {
                currentAction=WALKING;
            animation.setFrames(sprites.get(0));

            animation.setDuration(0.2);
            }

        }
        else
        {
            if(currentAction!=IDLE){
                currentAction=IDLE;
            animation.setFrames(sprites.get(1));
            animation.setDuration(0.5);}
        }
        animation.updateFrame();
        //set face direction
        if(right) faceRight=true;
        if(left) faceRight=false;
        if(up) faceDown =false;
        if(down) faceDown=true;




    }

    public void draw(GraphicsContext gc)
    {
        setLocalMapPosition();
        super.draw(gc);
    }
    public double getx()
    {
        return x;

    }
    public double gety()

    {
        return y;
    }
    public double getConstrantX()
    {
        return constraintX;
    }
    public double getConStrantY()
    {
        return constraintY;
    }
    public double getdx()
    {
        return dx;
    }
    public boolean getRight()
    {
        return right;
    }
    public void drawRec(AnchorPane anchorPane)
    {
        Rectangle rectangle2D=new Rectangle(cheight,cwidth,x-cwidth/2,y-cheight/2);
        rectangle2D.setFill(Color.GREEN);
        anchorPane.getChildren().add(rectangle2D);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Inventory getInven() {
        return inven;
    }

    public int getDame() {
        return dame;
    }
}
