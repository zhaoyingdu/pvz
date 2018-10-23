package pvz2018;

import java.util.Random;

public abstract class Plant {
    Random rand = new Random();

	protected int health;
	public static int coolDown;
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
    
    public abstract void resetCD();

    public void deceaseCD(){
        if(coolDown == 0) return;
        coolDown --;
    }

	public int getCD(){
		return coolDown;
	}
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