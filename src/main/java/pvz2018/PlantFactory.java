package pvz2018;

import java.lang.reflect.Field;

public class PlantFactory {
    
    public PlantFactory(){}

    public Plant createPlant(Garden garden, String plantName){
        Plant newPlant =null;
        switch(plantName){
            case "sunflower":
                if(!Sunflower.inCD()){
                    Field money;
                    try {
                        money = garden.getClass().getDeclaredField("money");
                        money.setAccessible(true);
                        try {
                            if ((int) money.get(garden) < Sunflower.PRICE){
                                System.out.println("no enough money");
                                return null;
                            }else{
                                Sunflower newSunflower = new Sunflower();
                                Sunflower.resetCD();
                                return newSunflower;
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
                    
                }else{
                    return null;
                }
                newPlant = new Sunflower();
                break;
            case "peashooter":
                break;
            default:
                return null;
        }
        return newPlant;
    }
}