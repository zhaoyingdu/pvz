package pvz2018;

public class Peashooter extends Plant{
    private final int HEALTH = 100;
    private int health;
    private String name;
    private boolean dead = false;
    private int price = 25;

    private int damage = 10;
    private int range = 6;


    public Peashooter(int row,int col){
        name = "peashooter";
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
        if(life%3==0){
            Greenpea bullet = new Greenpea(row,col); 
            bullet.addPropertyChangeListener(Garden.getInstance());
            firePropertyChange("fire green pea", null, bullet);
        }
    }
    
}