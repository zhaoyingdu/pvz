package pvz2018;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class Garden extends AbstractGarden{// extends AbstractModel{

    PlantFactory plantFactory = new PlantFactory();
    int layoutWidth = 8;
    int layoutHeight = 5;
    Plant[][] layout = new Plant[layoutHeight][layoutWidth];


    int gameProgress = 0;
    
    int suns=0;
    int money;

    boolean idle = false;
    List<Zombie> zombies;


    


    public Garden(){
        super();
        System.out.println("Garden: new garden.");
        zombies = new ArrayList<>();
        money = 100;
        System.out.println("Garden: plant holder");
    }

    public void plantDefense(String plantName, int row, int col){
        System.out.println("Garden-plantDefense");
        if(layout[row][col] != null){
            System.out.println("slot occupied");
            return;
        }
        Plant newPlant = plantFactory.createPlant(this, plantName);
        if(newPlant == null){
            System.out.println("Garden-plantDefense-null");   
        }else{
            System.out.println("Garden-plantDefense-planted");
            money -= newPlant.getPrice();
            layout[row][col]=newPlant;
            
            Object[] plantinfo = new Object[]{newPlant.getName().charAt(0),row,col};
            firePropertyChange("planted", null, plantinfo);
            updateProgress();
        }
    }
    //game progress not updated
    public void collectSuns(){
        money = suns*25;
        suns = 0;
        firePropertyChange("sunCollected",null,money);
    }

    public void idle(int rounds){
        idle = true;
        for(int i=0; i<rounds;i++){
            updateProgress();
        }
        firePropertyChange("back", null, packState());
    }

    private void updateProgress(){
        gameProgress++;
        if(gameProgress%3==0) dropSun();
    }
    //garden is dropping sun
    private void dropSun(){
        suns++;
        if(!idle)firePropertyChange("sun droped",null,null);
    }


    private Map<String,Object> packState(){  
        Map<String,Object> state = new HashMap<>();
        state.put("game layout",layout);
        state.put("suns",suns);
        state.put("money",money);

        return state;
    }

    
}