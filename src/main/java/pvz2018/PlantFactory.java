package pvz2018;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PlantFactory {
    
    SunflowerFactory sunflowerFactory = SunflowerFactory.getInstance();
    PeashooterFactory peashooterFactory = PeashooterFactory.getInstance();
    //Garden garden = Garden.getInstance();


    public PlantFactory(){  
    }

    public void decreaseCD(){
        sunflowerFactory.deceaseCD();
        peashooterFactory.deceaseCD();
    }

    public Map<String,Object> getFactoriesCD(){
        Map<String,Object> fcd = new HashMap<String,Object>();
        fcd.put("sunflowerCD",sunflowerFactory.getCD());
        fcd.put("peashooterCD",peashooterFactory.getCD());
        return fcd;
    }
    public Plant createPlant(String plantName,int row, int col) throws NotEnoughMoneyException, InCooldownException {
        Plant newPlant =null;
        switch(plantName){
            case "sunflower":
                if(checkPrice(sunflowerFactory.getPrice())){
                    newPlant = sunflowerFactory.createSunflower(row,col);
                    newPlant.addPropertyChangeListener(Garden.getInstance());
                }else{
                    //do nothing
                }
                break;
            case "peashooter":
                if(checkPrice(peashooterFactory.getPrice())){
                    newPlant = peashooterFactory.createPeashooter(row,col);
                    newPlant.addPropertyChangeListener(Garden.getInstance());
                }else{
                    //do nothing
                }
                break;
            default:
                return null;
        }
        return newPlant;
    }

    private boolean checkPrice(int factoryPrice) throws NotEnoughMoneyException{
        Field money;
        Garden garden = Garden.getInstance();
        try {
            money = garden.getClass().getDeclaredField("money");
            money.setAccessible(true);
            try {
                if ((int) money.get(garden) < factoryPrice){
                    throw new NotEnoughMoneyException("you don't have enough suns.");
                }else{
                    return true;
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("checkPrice not executed");
        return false;
    }
}