package pvz2018;

public class Sunflower extends Plant{
    private final int HEALTH = 100;
    private final int COOLDOWN = 5;
    private final int PRICE = 25;

    public Sunflower(int rowNumber,int colNumber){
        super(rowNumber,colNumber);

        health = HEALTH;
        price = PRICE;
        coolDown = 0;
    }

    @Override
    public void resetCD(){
        coolDown = COOLDOWN;
    }
}