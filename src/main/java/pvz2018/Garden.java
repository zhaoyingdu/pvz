package pvz2018;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

public class Garden extends AbstractGarden implements PropertyChangeListener{// extends AbstractModel{

    PlantFactory plantFactory = new PlantFactory();
    int layoutWidth = 8;
    int layoutHeight = 5;
    Plant[][] layout = new Plant[layoutHeight][layoutWidth];
    SunflowerFactory sFactory = SunflowerFactory.getInstance();

    int gameProgress = 0;
    
    int suns=0;
    int money ;

    boolean idle = false;
    List<Zombie> zombies;


    private static Garden garden;

    private Garden(){
        super();
        System.out.println("Garden: new garden.");
        zombies = new ArrayList<>();
        money = 100;
        System.out.println("Garden: plant holder");
    }

    public static Garden getInstance(){
        if(garden==null){
            garden = new Garden();
            System.out.println("new garden hahaha....");
        }
        return garden;
    }

    public void plantDefense(String plantName, int row, int col){
        System.out.println("Garden-plantDefense");
        if(layout[row][col] != null){
            System.out.println("slot occupied");
            return;
        }
        Plant newPlant;
        try {
            newPlant = plantFactory.createPlant(plantName,row,col);

            money -= newPlant.getPrice();
            layout[row][col]=newPlant;
            Map<String,Object> newState = packState();
            firePropertyChange("planted", null, newState);
            updateProgress();
        } catch (NotEnoughMoneyException | InCooldownException e) {
            firePropertyChange("plant failed",null,"plant "+plantName+" failed."+e.getMessage());
            //System.out.println(e.getMessage());
        }   
        
    }

    public void dig(int row, int col){
        if(layout[row][col] == null){ 
            return;
        }else{
            layout[row][col] = null; //hopefully it get garbage collected..
            packState();
            firePropertyChange("plant removed",null,packState());
        }
    }
    //game progress not updated
    public void collectSuns(){
        money += suns*25;
        suns = 0;
        firePropertyChange("sun collected",null,packState());
    }

    public void idle(int rounds){
        idle = true;
        for(int i=0; i<rounds;i++){
            updateProgress();
        }
        idle = false;
        firePropertyChange("back", null, packState());
    }

    private void updateProgress(){
        gameProgress++;
        plantFactory.decreaseCD();
        for(int row = 0;row<layoutHeight;row++){
			for(int col = 0; col<layoutWidth;col++){
                Plant p = layout[row][col];
				if(p!=null){
					p.grow();
				}
			}
		}
        if(gameProgress%3==0) dropSun();
    }
    //garden or sunflower is dropping sun
    private void dropSun(){
        suns++;
        if(!idle)firePropertyChange("sun droped",null,packState());
    }


    private Map<String,Object> packState(){  
        Map<String,Object> state = new HashMap<>();
        state.put("layout",layout);
        state.put("suns",suns);
        state.put("money",money);
        state.putAll(plantFactory.getFactoriesCD());

        return state;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "new sun":
                int[] plantinfo = (int[])evt.getNewValue();
                String log = "sunflower at "+ plantinfo[0]+" "+plantinfo[1];
                System.out.println(log);
                dropSun();
                break;
        }
    }

    
}