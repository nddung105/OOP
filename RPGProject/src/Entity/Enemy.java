package Entity;

import Entity.MapObject;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends MapObject {

    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....

    //action
    private static final int IDLE=0;
    private static final int WALKING=1;


    public Enemy(TileMap tileMap)
    {
        super(tileMap);
        width=40;
        height=40;
        cheight=40;
        cwidth=30;

        veclocity=0.3;
        maxVec=0.5;

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

    public void detectHero(Hero hero)
    {
        double posX=hero.getx();
        double posY=hero.gety();

        setUp(false);
        setDown(false);
        setRight(false);
        setLeft(false);


        if((posX-x)*(posX-x)+(posY-y)*(posY-y)<100000) {
            if (x > posX) {
                setLeft(true);

                dx -= veclocity;
                if (dx < -maxVec) {
                    dx = -maxVec;
                }

            } else if (x < posX) {

                setRight(true);
                dx += veclocity;
                if (dx > maxVec) {
                    dx = maxVec;
                }

            }  if (y > posY) {

                setDown(true);
                dy -= veclocity;
                if (dy < -maxVec) {
                    dy = -maxVec;
                }
            } else if (y < posY ) {

                setUp(true);
                dy += veclocity;

                if (dy > maxVec) {
                    dy = maxVec;
                }
            }
        }
        else {
            dx=0;
            dy=0;
        }



    }
    public void update(Hero hero)
    {
        //update position
        detectHero(hero);
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




}
