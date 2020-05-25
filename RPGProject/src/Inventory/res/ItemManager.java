package Inventory.res;

import Inventory.Item;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private ArrayList<Item> items;

    public ItemManager(){
        items = new ArrayList<Item>();
    }

    public void tick() throws FileNotFoundException {
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            Item i = it.next();
            i.tick();
            if(i.isPickedUp())
                it.remove();
        }
    }

    public void render(GraphicsContext g){
        for(Item i : items)
            i.render(g);
    }

    public void addItem(Item i){
        items.add(i);
    }
}
