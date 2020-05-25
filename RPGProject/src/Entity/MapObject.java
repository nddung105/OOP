package Entity;

import Map.Tile;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class MapObject {


    protected TileMap tileMap;
    protected  int tileSize;
    protected double xCamera;//map position
    protected double yCamera;
    public final int MAP_SIZE_WIDTH=32*40;
    public final int MAP_SIZE_HEIGHT=32*30;

    //object position
    protected double x;// in the whole map
    protected double y;
    protected double dx;
    protected double dy;

    //dimension
    protected  int width;
    protected int height;

    //colision rec
    protected  int cwidth;
    protected  int cheight;

    //colision
    protected int currentRow;
    protected int currentCol;
    protected  double nextX;//hypothesis
    protected double nextY;
    protected  double updatedX;//real after move(colision detected or not)
    protected double updatedY;
    protected  boolean topLeft;
    protected boolean topRight;
    protected  boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int prevAction;
    protected boolean faceRight;
    protected boolean faceDown;

    //movement
    protected boolean left;

    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }

    public void setUpButtonPressed(boolean upButtonPressed) {
        this.upButtonPressed = upButtonPressed;
    }

    public void setDownButtonPressed(boolean downButtonPressed) {
        this.downButtonPressed = downButtonPressed;
    }

    protected boolean right;
    protected boolean up;
    protected boolean down;
    //keyevent
    protected boolean leftButtonPressed;
    protected boolean rightButtonPressed;
    protected boolean upButtonPressed;
    protected boolean downButtonPressed;

    //movement attribute
    protected  double veclocity;
    protected double maxVec;
    protected  double decelerateSpeed;

    //constructor
    public MapObject(TileMap tileMap)
    {
        this.tileMap=tileMap;
        tileSize=tileMap.getTileSize();



    }
    public boolean collide(MapObject o)
    {
        Rectangle r1=new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
        Rectangle r2=o.getRec();
        Shape intesect=Shape.intersect(r1,r2);
        if(intesect.getBoundsInParent().getWidth()<0)//getBoundLocal
        {
            return true;
        }
        return false;

    }
    public Rectangle getRec()
    {
        return new Rectangle(
                (int)x-cwidth,(int)y-cheight,cwidth,cheight

        );
    }

    public void calculateCorner(double x,double y)
    {
        int leftTileCorner=(int)(x-cwidth/2+0.0001)/tileSize; // +-1 de xu ly cho giao cua 2 tile do se bi chong` type (block/normal)
        int rightTileCorner=(int)(x+cwidth/2-0.0001)/tileSize;
        int topTile=(int)((y-cheight/2+0.0001)/tileSize);
        int bottomTile=(int)(y+cheight/2-0.0001)/tileSize;



        int typeTopLeft=tileMap.getType(topTile,leftTileCorner);
        int typeTopRight=tileMap.getType(topTile,rightTileCorner);
        int typeBottomLeft=tileMap.getType(bottomTile,leftTileCorner);
        int typeBottomRight=tileMap.getType(bottomTile,rightTileCorner);

        this.topLeft= (typeTopLeft== Tile.BLOCKED);
        this.topRight=(typeTopRight==Tile.BLOCKED);
        this.bottomLeft=(typeBottomLeft==Tile.BLOCKED);
        this.bottomRight=(typeBottomRight==Tile.BLOCKED);

    }

    public void checkTileMapCollision()
    {

        currentCol=(int)x/tileSize;
        currentRow=(int)y/tileSize;

        nextX=x+dx;
        nextY=y+dy;

        updatedX =x;
        updatedY =y;

        if(nextX>0+cwidth/2  &&  nextX<MAP_SIZE_WIDTH-cwidth/2) {


            calculateCorner(nextX, y);
            if (dx > 0) {
                if (topRight || bottomRight) {
                    dx = 0;
                    updatedX = (currentCol + 1) * tileSize - cwidth / 2;
                } else {
                    updatedX += dx;
                }

            }
            if (dx < 0) {
                if (topLeft || bottomLeft) {
                    dx = 0;
                    updatedX = (currentCol) * tileSize + cwidth / 2;
                } else {
                    updatedX += dx;
                }
            }


        }

        if(nextY>0+cheight/2  &&  nextY<MAP_SIZE_HEIGHT-cheight/2) {

            calculateCorner(x, nextY);
            if (dy < 0)//go upward
            {

                if (topLeft || topRight) {
                    dy = 0;
                    updatedY = currentRow * tileSize + cheight / 2;

                } else {

                    updatedY += dy;
                }

            }
            if (dy > 0) {
                if (bottomLeft || bottomRight) {
                    dy = 0;
                    updatedY = (currentRow + 1) * tileSize - cheight / 2;
                } else {
                    updatedY += dy;
                }
            }//go right

        }


    }
    public void setPosition(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    public void setLocalMapPosition()
    {
        xCamera=tileMap.getX();
        yCamera=tileMap.getY();
    }
    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setDown(boolean b) { down = b; }
    public void draw(GraphicsContext gc)
    {
                if(!faceRight) {
            gc.drawImage(animation.getImage(),x-xCamera+width/2,y-yCamera-height/2,-width,height);

        }
        else
        {
            gc.drawImage(animation.getImage(),x-xCamera-width/2,y-yCamera-height/2,width,height);

        }
    }




}
