package pvz2018;

import java.util.Random;

public abstract class Plant {
    Random rand = new Random();
	protected String name;
	protected int health;
	//public static int COOLDOWNSEC;
	//public static int coolDown;
	protected boolean isDead, isCooledDown;
	protected int damage, rowNumber, colNumber, price;
        

	public Plant(){
		//this.health = health;
		//this.damage = damage;
		//this.rowNumber = rowNumber;
		//this.colNumber = colNumber;
		//this.price = price;
		//this.coolDown = coolDown;
		//this.isDead = false;
	}
	
	public abstract String getName();
    
	
	public int getRow(){
        return rowNumber;
    }
	
    public int getColumn(){
        return colNumber;
    }
	public void setHealth(int newHealth){
		this.health = newHealth;
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public int getPrice(){
		return this.price;
	}
}