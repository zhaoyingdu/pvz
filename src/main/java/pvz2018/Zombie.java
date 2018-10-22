package pvz2018;

import java.util.Random;

public class Zombie {

    Random rand = new Random();
    private int health;
    private int damage, rowNumber;
    private double speed;
    private boolean isDead, isStopped;
    

    public Zombie(){

    }


    public Zombie(int health, int damage, int rowNumber, double speed){
        this.health = health;
        this.damage = damage;
        this.rowNumber = rowNumber;
        this.speed = speed;
        this.isDead = false;
        this.isStopped = false;
    }
    
    public void stop(){
        this.isStopped = true;
    }
    
    public void dead(){
        if(this.health <= 0){
            this.isDead = true;
        }
    }
    
    public void setHealth(int newHealth){
        this.health = newHealth;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    // need to implement
    public double getPosition(int step){
        return 0;
    }
}
