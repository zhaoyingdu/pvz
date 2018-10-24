package pvz2018;

public class Sunflower extends Plant{
    private final int HEALTH = 100;
    private int health;
    private String name;
    private boolean dead = false;
    private int price = 25;

    public Sunflower(){
        name = "sunflower";
        health = HEALTH;
    }
    
    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public void decreaseHealth(int damage){
        health-=damage;
        if(health<0) dead = true;
    }
    
}