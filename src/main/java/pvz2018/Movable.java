package pvz2018;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Movable{
    protected int damage;
    protected String name;
    protected double speed;
    //protected double intMove;//just to determine when to move the movable on view
    protected int row;
    protected int col;
    protected int initCol;
    protected double displacement;
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


    public abstract int getRow();
    public abstract int getInitCol();
    public abstract int getCol();
    public abstract double getDisplacement();
    public abstract boolean propagate();
    public abstract String getName();
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public abstract double getPosition();

}