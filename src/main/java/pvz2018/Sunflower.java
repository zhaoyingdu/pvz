package pvz2018;

public class Sunflower extends Plant{
    private final int HEALTH = 100;
    public static final int COOLDOWNSEC = 5;
    public static int coolDown;
    public static int PRICE = 25;

    public Sunflower(){
        super();
        health = HEALTH;
        price = PRICE;
        coolDown = 0;
    }

    public static boolean inCD(){
        return coolDown != 0;
    }

    @Override
    public void resetCD(){
        coolDown = COOLDOWNSEC;
    }

    
}