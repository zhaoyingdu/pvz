package pvz2018;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Garden extends AbstractGarden implements PropertyChangeListener{// extends AbstractModel{

    PlantFactory plantFactory = new PlantFactory();
    int screenWidth = 15;
    int lotWidth = 8;
    int lotHeight = 5;
    Plant[][] lots = new Plant[lotHeight][lotWidth];
    List<Plant> plants = new ArrayList<>();
    List<Movable> movables = new ArrayList<>();

    //Map<Integer,List<Movable>> movables = new HashMap<>();

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
        /*movables.put(0,new ArrayList<Movable>());
        movables.put(1,new ArrayList<Movable>());
        movables.put(2,new ArrayList<Movable>());
        movables.put(3,new ArrayList<Movable>());
        movables.put(4,new ArrayList<Movable>());*/
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
        if(lots[row][col] != null){
            System.out.println("slot occupied");
            return;
        }
        Plant newPlant;
        try {
            newPlant = plantFactory.createPlant(plantName,row,col);

            money -= newPlant.getPrice();
            lots[row][col]=newPlant;
            Map<String,Object> newState = packState();
            firePropertyChange("render", null, newState);
        } catch (NotEnoughMoneyException | InCooldownException e) {
            firePropertyChange("plant failed",null,"plant "+plantName+" failed. "+e.getMessage());
            //System.out.println(e.getMessage());
        }   
        
    }

    public void dig(int row, int col){
        if(lots[row][col] == null){ 
            return;
        }else{
            lots[row][col] = null; //hopefully it get garbage collected..
            packState();
            firePropertyChange("render",null,packState());
        }
    }
    //game progress not updated
    public void collectSuns(){
        money += suns*25;
        suns = 0;
        firePropertyChange("render",null,packState());
    }

    public void idle(int rounds){
        idle = true;
        for(int i=0; i<rounds;i++){
            updateProgress();
        }
        idle = false;
        firePropertyChange("render", null, packState());
    }

    private void updateProgress(){
        gameProgress++;
        plantFactory.decreaseCD();
        for(int row = 0;row<lotHeight;row++){
			for(int col = 0; col<lotWidth;col++){
                Plant p = lots[row][col];
				if(p!=null){
					p.grow();
				}
			}
        }
        
        Iterator<Movable> itr = movables.iterator();
            
        while(itr.hasNext()) {
            Movable m = (Movable)itr.next();
            m.propagate();
            if(!checkBound(m)){
                itr.remove();
            }
        }

        /*for(Integer i : movables.keySet()){
            for(Movable m : movables.get(i)){
                m.propagate();
            }
            Iterator<Movable> itr = movables.get(i).iterator();
            
            while(itr.hasNext()) {
                Movable m = (Movable)itr.next();
                if(!checkBound(m)){
                    itr.remove();
                }
            }
        }*/
        if(gameProgress%3==0) dropSun();
    }
    //garden or sunflower is dropping sun
    private void dropSun(){
        suns++;
        if(!idle)firePropertyChange("render",null,packState());
    }


    private Map<String,Object> packState(){  
        Map<String,Object> state = new HashMap<>();
        state.put("layout",lots);
        state.put("suns",suns);
        state.put("money",money);
        state.putAll(plantFactory.getFactoriesCD());
        state.put("movables",movables);

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
            case "fire green pea":
                //int row = ((Greenpea)evt.getNewValue()).getRow();
                Movable m = (Greenpea)evt.getNewValue();
                movables.add(m);
                break;
           
        }
    }


    private boolean checkBound(Movable movable){
        double position = movable.getPosition();
        if(position>=screenWidth){
            return false;
        }else{
            return true;
        }
    }
    

    private void formLayoutMessage(){
        ArrayList<String> layoutMessage = new ArrayList<>();
        String pixelMessage;
        for(int i =0;i<lotHeight;i++){
            for (int j=0;j<lotWidth;j++){
                if(lots[i][j]!=null){
                    pixelMessgae = lots[i][j]
                }
            }
        }
    }
}