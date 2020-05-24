package Entity;

import AStar.AStarSearch;
import Entity.MapObject;
import Map.Tile;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Enemy extends MapObject {

    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....

    //index
    private int dame;
    private int health;
    private double speed;

    //action
    private static final int IDLE=0;
    private static final int WALKING=1;

    //epsilon for double precision
    public static double epsilon=0.001;

    //hero detection
    private Hero hero;
    //algorithm implementation
    //localMap //range of enemy
    public Tile[][] localMap;
    int range;



    public Enemy(TileMap tileMap,int range,Hero hero)
    {
        super(tileMap);
        width=40;
        height=40;
        cheight=40;
        cwidth=30;

        veclocity=1.0;
        maxVec=1.0;

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


        //hero detection
        this.hero=hero;

        //algorithm initialization
        localMap=new Tile[range][range];
        this.range=range;


    }



    //path
    public List<Tile> optimumPath;
    //khong co path , return null
    private int nextPosIndex;




    public void updateLocalMap()// phai le?
    {

        int enemyRow=(int)y/tileSize;
        int enemyColumn=(int)x/tileSize;
        int startRowInMap=enemyRow-(range/2);
        int startColumnInMap=enemyColumn-(range/2);

        for(int row=0;row<range;row++)
        {
            for(int column=0;column<range;column++)
            {

                localMap[row][column]=new Tile(null,tileMap.getType(startRowInMap,startColumnInMap));

                localMap[row][column].setPos(startColumnInMap*tileSize,startRowInMap*tileSize);

                startColumnInMap++;
            }
            startRowInMap++;
            startColumnInMap=enemyColumn-(range/2);
        }
    }
    public Tile findHero()//test
    {
        int heroRow=(int)(hero.gety()/tileSize);
        int heroCol=(int)(hero.getx()/tileSize);
        int enemyRow=(int)y/tileSize;
        int enemyColumn=(int)x/tileSize;

        int startRow=enemyRow-range/2;
        int startCol=enemyColumn-range/2;
        int localHeroRow=heroRow-startRow;
        int localHeroCol=heroCol-startCol;

        return localMap[localHeroRow][localHeroCol];
    }
    public boolean heroInRange()//test
    {
        int heroRow=(int)(hero.gety()/tileSize);
        int heroCol=(int)(hero.getx()/tileSize);
        int enemyRow=(int)y/tileSize;
        int enemyColumn=(int)x/tileSize;
        if(Math.abs(heroCol-enemyColumn)>range/2||Math.abs(heroRow-enemyRow)>range/2)
        {
            return false;
        }


        return true;
    }
    public Tile findEnemy()//tested
    {

        return localMap[range/2][range/2];
    }


    public void updatePath()
    {
        updateLocalMap();
        Tile heroInLocalMap=findHero();
        Tile enemyInMap=findEnemy();
        optimumPath=AStarSearch.findPath(enemyInMap,heroInLocalMap,localMap,range);
        nextPosIndex=0;
    }

    double distancesX=0;
    double distancesY=0;//per tile
    public void detectHero()
    {

        Tile currentPosition=findEnemy();
        if(optimumPath==null||optimumPath.size()==0)
        {
            System.out.println("no path");

            return;
        }

        Tile nextPosition=optimumPath.get(nextPosIndex);

        updateDxDy(currentPosition,nextPosition);
        updatedX=x+dx;
        updatedY=y+dy;



        if(up||down)
        {

            distancesX+=veclocity;
            if(Math.abs(distancesX-tileSize)<epsilon)
            {

                distancesX=0;
                nextPosIndex++;

            }
        }
        if(left||right)
        {

            distancesY+=veclocity;
            if(Math.abs(distancesY-tileSize)<epsilon)
            {

                distancesY=0;
                nextPosIndex++;

            }
        }
        setPosition(updatedX,updatedY);
        System.out.println("FUCKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println(updatedX);
        System.out.println(updatedY);
        System.out.println("End Fuckkkkkkkkkkkkkkkkkkkkkkk");
        updateAnimation();

    }
    public void updateDxDy(Tile currentTile,Tile nextTile)
    {


        if((int)distancesX!=0||(int)distancesY!=0)
        {
            return;
        }

        int currentPosX=currentTile.getPosX();
        int currentPosY=currentTile.getPosY();
        int nextTileX=nextTile.getPosX();
        int nextTileY=nextTile.getPosY();
        if(currentPosX<nextTileX)
        {
            dx=veclocity;
            setRight(true);
            setLeft(false);
            setDown(false);
            setUp(false);
            dy=0;
        }
        else if(currentPosX>nextTileX)
        {
            dx=-veclocity;
            setLeft(true);
            setRight(false);
            setUp(false);
            setDown(false);
            dy=0;
        }
        else {
            if(currentPosY<nextTileY)
            {
                dy=veclocity;
                setDown(true);
                setUp(false);
                setRight(false);
                setLeft(false);
                dx=0;
            }
            else if(currentPosY>nextTileY)
            {
                dy=-veclocity;
                setUp(true);
                setDown(false);
                setLeft(false);
                setRight(false);
                dx=0;
            }
        }

    }



    public void updateAnimation()
    {


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




    }
    public void draw(GraphicsContext gc)
    {

        setLocalMapPosition();

        super.draw(gc);
    }
    public void printPath()
    {
        int size=optimumPath.size();
        if(size!=0)
        {
            for(int i=0;i<size;i++)
            {
                System.out.println("SIZEEEE:"+optimumPath.size());
                System.out.println("+Y"+optimumPath.get(i).getPosY());
                System.out.println("+++++++++++++++++++++++++");
                System.out.println("+X"+optimumPath.get(i).getPosX());
            }
            System.out.println("endPathhhhhhhh");

        }


    }
//    public void printPos()
//    {
//        System.out.println("+++++++++++++++++++++++++++");
//        System.out.println(x);
//        System.out.println(y);
//    }




}
