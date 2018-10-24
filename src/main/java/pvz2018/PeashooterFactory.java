package pvz2018;

public class PeashooterFactory {
    private final int HEALTH = 100;
    private int COOLDOWNSEC = 5;
    private int coolDown;
    private boolean resetted = false;
    private int price = 50;
    private static PeashooterFactory peashooterFactory;

    private PeashooterFactory(){}

    public static PeashooterFactory getInstance(){
        if(peashooterFactory==null){
            peashooterFactory = new PeashooterFactory();
        }

        return peashooterFactory;
    }

    public Peashooter createPeashooter(int row,int col) throws InCooldownException{
        if(inCD()){
            throw new InCooldownException("peashooter factory is in cd\n");
        }else{
            resetCD();
            return new Peashooter(row,col);
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