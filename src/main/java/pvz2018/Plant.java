package pvz2018;

import java.util.Random;

public abstract class Plant {
	protected String name;
	protected int health;
	protected boolean dead;
	protected int damage;
        

	public Plant(){}
	
	public abstract String getName();
	public abstract int getPrice();
    public abstract void decreaseHealth(int damage);
    
	
}