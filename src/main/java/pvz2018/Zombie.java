package pvz2018;

import java.util.Random;

public class Zombie extends Movable implements Comparable<Zombie>{

    Random rand = new Random();
    private int health;
    private double speed;
    public final double SPEED = -0.25;
    

    public Zombie(int row,int col){
        this.speed = SPEED;
        this.row = row;
        this.col = col;
        initCol = col;
        //this.displace = col;
        displacement =0;
        name="zombie";
        damage = 20;
    }

    
 
    
    public boolean dead(){
        if(this.health <= 0){
            return true;
        }
        return false;
    }
 
    // need to implement
    public double getPosition(){
        return initCol+displacement;
    }

    public void takeDamage(Greenpea p){
        health -= p.hit();
    }

    

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getInitCol() {
        return initCol;
    }

    @Override
    public double getDisplacement() {
        return 0;
    }

    @Override
    public boolean propagate() {
        double oldDisplacement= displacement;
        displacement+=speed;
        return Math.floor(oldDisplacement) == Math.floor(displacement);
    }
    @Override
    public int getCol(){
        return (int)Math.floor(getPosition());
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Zombie o) {
        if(o.getPosition()>getPosition()){
            return 1;
        }else if(o.getPosition() == getPosition()){
            return 0;
        }else{
            return -1;
        }
    }
}
