package pvz2018;

public class SunflowerFactory {
    private final int HEALTH = 100;
    private int COOLDOWNSEC = 5;
    private int coolDown;
    private boolean resetted = false;
    private static SunflowerFactory sunflowerFactory;

    private SunflowerFactory(){}

    public static SunflowerFactory getInstance(){
        if(sunflowerFactory==null){
            sunflowerFactory = new SunflowerFactory();
        }

        return sunflowerFactory;
    }

    public Sunflower createSunflower(){
        if(inCD()){
            System.out.println("sunflower factory is in cd");
            return null;
        }else{
            return new Sunflower();
        }
        
    }

    public void resetCD(){
        coolDown = COOLDOWNSEC;
        resetted =true;
	}
	public boolean inCD(){
		return coolDown !=0;
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