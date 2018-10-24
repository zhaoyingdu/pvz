package pvz2018;

public class Sunflower extends Plant{
    private final int HEALTH = 100;
    private int health;
    private String name;
    private boolean dead = false;
    private int price = 25;


    public Sunflower(int row,int col){
        name = "sunflower";
        health = HEALTH;
        life =0;
        this.row = row;
        this.col = col;
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

    public void grow(){
        life++;
        if(life%4==0){
            firePropertyChange("new sun", null, new int[]{row,col});
        }
    }

    
    
}