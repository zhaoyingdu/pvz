package pvz2018;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

public abstract class Plant {
	protected String name;
	protected int health;
	protected boolean dead;
	protected int damage;
	protected int life = 0;
	protected int row;
	protected int col;
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
        

	public Plant(){}
	
	public abstract String getName();
	public abstract int getPrice();
    public abstract void decreaseHealth(int damage);
    public abstract void grow();
	//public abstract boolean produceSun();

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