package pvz2018;

public class SunflowerFactory {
    private final int HEALTH = 100;
    private int COOLDOWNSEC = 5;
    private int coolDown;
    private boolean resetted = false;
    private int price = 25;
    private static SunflowerFactory sunflowerFactory;

    private SunflowerFactory(){}

    public static SunflowerFactory getInstance(){
        if(sunflowerFactory==null){
            sunflowerFactory = new SunflowerFactory();
        }

        return sunflowerFactory;
    }

    public Sunflower createSunflower() throws InCooldownException{
        if(inCD()){
            throw new InCooldownException("sunflower factory is in cd");
        }else{
            resetCD();
            return new Sunflower();
        }
        
    }
 
    public int getPrice(){return price;}
    public void resetCD(){
        coolDown = COOLDOWNSEC;
        resetted =true;
	}
	public boolean inCD(){
		return coolDown !=0;
    }
    public int getCD(){
        return coolDown;
    }
    public void deceaseCD(){
        if(coolDown == 0) return;
        if(!resetted){
            coolDown --;
        }else{
            resetted = false;
        }
    }

    
}