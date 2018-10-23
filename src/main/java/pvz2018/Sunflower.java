package pvz2018;

public class Sunflower extends Plant{
    private final int HEALTH = 100;
    public static int COOLDOWNSEC = 5;
    public static int coolDown;
    public static int PRICE = 25;

    public Sunflower(){
        super();
        name = "sunflower";
        health = HEALTH;
        price = PRICE;
        coolDown = 0;
    }

    @Override
    public String getName(){
        return name;
    }
    public static void resetCD(){
		coolDown = COOLDOWNSEC;
	}
	public static boolean inCD(){
		return coolDown !=0;
	}
    public static void deceaseCD(){
        if(coolDown == 0) return;
        coolDown --;
    }

    
}