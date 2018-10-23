package pvz2018;

import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Garden extends AbstractGarden{// extends AbstractModel{

    PlantFactory plantFactory = new PlantFactory();
    int layoutWidth = 8;
    int layoutHeight = 5;
    Plant[][] layout = new Plant[layoutWidth][layoutHeight];
    

    int money;

    //List<Plant> plants;
    List<Zombie> zombies;

    //PropertyChangeSupport propertyChangeSupport;

    public Garden(){
        super();
        //propertyChangeSupport = new PropertyChangeSupport(this);
        System.out.println("Garden: new garden.");
        zombies = new ArrayList<>();
        money = 100;
        //add five demo zombies;
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());

        //plants = new ArrayList<>();
        System.out.println("Garden: plant holder");
        //firePropertyChange("model initialization complete", null, null);
        
    }

    public void plantDefense(String plantName, int row, int col){
        System.out.println("Garden-plantDefense");
        if(layout[row][col] != null){
            System.out.println("slot occupied");
            return;
        }
        Plant newPlant = plantFactory.createPlant(this, "sunflower");
        if(newPlant == null){
            System.out.println("Garden-plantDefense-null");
            
        }else{
            System.out.println("Garden-plantDefense-planted");
            layout[row][col]=newPlant;
            Object[] plantinfo = new Object[]{'S',row,col};
            firePropertyChange("planted", null, plantinfo);
        }
        
        

    }


}