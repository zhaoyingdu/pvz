package pvz2018;

import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Garden extends AbstractGarden{// extends AbstractModel{

    
    List<Plant> plants;
    List<Zombie> zombies;

    //PropertyChangeSupport propertyChangeSupport;

    public Garden(){
        super();
        //propertyChangeSupport = new PropertyChangeSupport(this);
        System.out.println("Garden: new garden.");
        zombies = new ArrayList<>();
        //add five demo zombies;
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());
        zombies.add(new Zombie());

        plants = new ArrayList<>();
        System.out.println("Garden: plant holder");
        //firePropertyChange("model initialization complete", null, null);
        
    }
}