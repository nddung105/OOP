package Map;

import AStar.AStarNode;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tile extends AStarNode {
    private Image image;
    private int type;

    //tile types
    public static final int NORMAL=0;
    public static final int BLOCKED=1;

    public Tile(Image image,int type)
    {
        this.image=image;
        this.type=type;
    }
    public Image getImage()
    {
        return image;
    }
    public int getType()
    {
        return type;
    }



    //implementing algorithm


    private int posX;//left corner in map
    private int posY;//right corner in map

    public int getPosX()
    {
        return posX;
    }
    public int getPosY()
    {
        return posY;
    }

    public void setPos(int posX,int posY)
    {
        this.posX=posX;
        this.posY=posY;
    }
    @Override
    //neibornode
    public float getCost(AStarNode node) {
        if(((Tile)node).posX==this.posX)
        {
            return Math.abs(((Tile)node).posY-this.posY);
        }
        else {
            return Math.abs(((Tile)node).posX-this.posX);
        }



    }

    @Override
    //remaining distance to goal
    public float getEstimatedCost(AStarNode node) {
        float a=Math.abs(((Tile)node).posY-this.posY);
        float b=Math.abs(((Tile)node).posX-this.posX);
        return a+b;
    }

    @Override
    public List getNeighbors(Tile[][] enemyRange,int range) {


        List<Tile> neighbor = new LinkedList<>();
        int column = 0;
        int row = 0;

        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                if (enemyRange[i][j] == this) {

                    row = i;
                    column = j;
                    break;
                }
            }
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || (i != 0 && j != 0)) {
                    continue;
                }
                if(row + i<0 ||row + i>range-1||column + j<0||column+j>range-1)
                {
                    continue;
                }


                if (enemyRange[row + i][column + j].getType() == Tile.NORMAL) {

                    neighbor.add(enemyRange[row + i][column + j]);

                }
            }

        }


        return neighbor;
    }


}
