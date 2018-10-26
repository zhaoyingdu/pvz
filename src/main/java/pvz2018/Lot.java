package pvz2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class Lot {
    int row;
    int col;
    Plant plant;
    ArrayList<Greenpea> greenpeas;
    ArrayList<Zombie> zombies;
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Lot(){
       
        greenpeas = new ArrayList<>();
        zombies = new ArrayList<>();
    }

    //public static Lot getNewInstance

    void addZombie(Zombie z){   
        zombies.add(z);
    }

    void addGreenpea(Greenpea p){
        greenpeas.add(p);
    }

    Greenpea removGreenpea(Greenpea p){
        if(greenpeas.remove(p)){
            return p;
        }else{
            return null;
        }
    }

    Zombie removeZombie(Zombie z){
        if(zombies.remove(z)){
            return z;
        }else{
            return null;
        }
    }

    ArrayList<Movable> checkSanity(){
        ArrayList<Movable> ret = new ArrayList<>();
        if(!zombies.isEmpty()){
            Collections.sort(zombies);
            while(!greenpeas.isEmpty()){
                Iterator<Greenpea> peaItr = greenpeas.iterator();
                while(peaItr.hasNext()){
                    Greenpea pea = peaItr.next();
                    zombies.get(0).takeDamage(pea);
                    if(zombies.get(0).dead()){
                        ret.add(zombies.get(0));
                        zombies.remove(zombies.get(0));//should use propertychange or something to notify garden
                        
                    }
                    ret.add(pea);
                    peaItr.remove();
                    if(zombies.isEmpty()){
                        break;
                    }
                }
            }
        }

        return ret;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }


}