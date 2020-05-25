package Entity;

import Inventory.Inventory;
import Map.TileMap;
import accesories.HeroClass;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

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
        up=true;//khoi tao bat ky
        down=false;
        right=true;
        left=false;

        width=40;
        height=40;
        cheight=28;
        cwidth=23;

        veclocity=0.5;
        maxVec=1.5;

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
    private boolean oldUp=true;
    private boolean oldRight=true;




    protected boolean bothRightLeftPressed=false;
    protected boolean bothUpDownPressed=false;
    public void updateXY()
    {

        //leftright confliction
        if(leftButtonPressed&&rightButtonPressed)//fixed
        {

            if(bothRightLeftPressed==false) {
                bothRightLeftPressed=true;
                if (right) {
                    right = false;
                    left = true;
                } else {
                    right = true;
                    left = false;
                }
            }
        }
        else if(leftButtonPressed)
        {

            bothRightLeftPressed=false;
            left=true;
            right=false;
        }
        else if(rightButtonPressed)
        {
            bothRightLeftPressed=false;
            right=true;
            left=false;
        }
        else {
            bothRightLeftPressed=false;
            left=false;
            right=false;
        }


        if(left)
        {

            dx -= veclocity;
            if(dx < -maxVec) {
                dx = -maxVec;
            }
            System.out.println(dx);
        }
        else if(right)
        {

            dx+=veclocity;
            if(dx>maxVec)
            {
                dx=maxVec;
            }
        }
        else if(!left&&!right){

            dx=0;
        }

        //updown confliction
        if(upButtonPressed&&downButtonPressed)//fixed
        {

            if(bothUpDownPressed==false) {
                bothUpDownPressed=true;
                if (up) {
                    up = false;
                    down=true;
                } else {
                    up = true;
                    down = false;
                }
            }
        }
        else if(upButtonPressed)
        {

            bothUpDownPressed=false;
            up=true;
            down=false;
        }
        else if(downButtonPressed)
        {
            bothUpDownPressed=false;
            down=true;
            up=false;
        }
        else {
            bothUpDownPressed=false;
            up=false;
            down=false;
        }


        if(up)
        {

            dy -= veclocity;
            if(dy < -maxVec) {
                dy = -maxVec;
            }
            System.out.println(dx);
        }
        else if(down)
        {

            dy+=veclocity;
            if(dy>maxVec)
            {
                dy=maxVec;
            }
        }
        else {

            dy=0;
        }


    }


    public void updatexy(boolean isLeftButtonPressed,boolean isRightButtonPressed)
    {

        if(isLeftButtonPressed&&isRightButtonPressed)
        {
            if(oldRight==true)
            {

                oldRight=false;
                right=false;
                left=true;
            }
            else {
                oldRight=true;
                right=true;
                left=false;
            }

        }

        else if(isLeftButtonPressed)
        {
            left=true;
            oldRight=false;
        }
        else if(isRightButtonPressed)
        {
            right=true;
            oldRight=true;
        }

    }

    public void updateDxDy()
    {
        if(oldRight==true)
        {
            if(right)
            {
                right=true;
                oldRight=true;
            }
            if(left) {


                left=true;
                oldRight=false;
                System.out.println(right);
            }
            if(right&&left)
            {

                right=false;
                left=true;
                oldRight=false;

            }



        }
        else  {


            if(right&&left)
            {
                System.out.println("================================");

                right=true;
                left=false;
                oldRight=true;

            }
            if(right)
            {



                right=true;
                oldRight=true;
            }
            if(left) {

                left=true;
                oldRight=false;
            }

        }
        if(left)
        {
            dx -= veclocity;
            if(dx < -maxVec) {
                dx = -maxVec;
            }
        }
        else if(right)
        {
            dx+=veclocity;
            if(dx>maxVec)
            {
                dx=maxVec;
            }
        }
        else {
            dx=0;

        }


        if(oldUp==true)
        {
            if(up&&down)
            {
                up=false;
                down=true;
                oldUp=false;

            }
            if(up)
            {
                up=true;
                oldUp=true;
            }
            if(down) {
                down=true;
                oldUp=false;
            }

        }
        if(down)
        {
            dy += veclocity;
            if(dy > maxVec) {
                dy = maxVec;
            }
        }
        else if(up)
        {
            dy-=veclocity;
            if(dy<-maxVec)
            {
                dy=-maxVec;
            }
        }
        else {

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
        updateXY();
        checkTileMapCollision();//and update constraint X Y stimultaneously// real X, real Y
        

        setPosition(updatedX, updatedY); //set Constrain to x,y //real movement of Hero

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
    public boolean getRight()
    {
        return right;
    }
}
