package pvz2018;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Garden extends AbstractGarden implements PropertyChangeListener{// extends AbstractModel{

    PlantFactory plantFactory = new PlantFactory();
    GardenDOM gardenDOM = GardenDOM.getInstance();

    //int screenWidth = 15;
    int lotWidth = 8;
    int lotHeight = 5;
    Lot[][] lot = new Lot[lotHeight][lotWidth];
    Plant[][] lots = new Plant[lotHeight][lotWidth];

    
    List<Plant> plants = new ArrayList<>();
    List<Movable> movables = new ArrayList<>();

    //Map<Integer,List<Movable>> movables = new HashMap<>();

    SunflowerFactory sFactory = SunflowerFactory.getInstance();

    int gameProgress = 0;
    
    int suns=0;
    int money ;

    boolean idle = false;
    //List<Zombie> zombies;


    private static Garden garden;
    private Garden(){
        super();
        gardenDOM.createNewXML();
        //System.out.println("Garden: new garden.");
        money = 100;
        for(int i=0;i<lotHeight;i++){
            for(int j=0;j<lotWidth;j++){
                lot[i][j]=new Lot();
            }
        }

    }

    public static Garden getInstance(){
        if(garden==null){
            garden = new Garden();
            //System.out.println("new garden hahaha....");
        }
        return garden;
    }

    public void plantDefense(String plantName, int row, int col){
        //System.out.println("Garden-plantDefense");
        if(lots[row][col] != null){
            System.out.println("\n current slot is occupied\n");
            return;
        }
        Plant newPlant;
        try {
            newPlant = plantFactory.createPlant(plantName,row,col);

            money -= newPlant.getPrice();
            lots[row][col]=newPlant;
          
            plants.add(newPlant);
            firePropertyChange("planted", null, packState());
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
           // packState();
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
            updateGame();
        }
        idle = false;
        firePropertyChange("render", null, packState());
    }

    private void updateGame(){
        gameProgress++;
        if(gameProgress%3==0){
            suns++;
        }
        updatePlants();
        updateMovable();
        generateZombie();
    }
    private void updatePlants(){
        plantFactory.decreaseCD();
        for(int row = 0;row<lotHeight;row++){
			for(int col = 0; col<lotWidth;col++){
                Plant p = lots[row][col];
				if(p!=null){
					p.grow();
				}
			}
        }
        
        
    }


    //propagate movable and move a movable to next slot if needed
    private void updateMovable(){
        
        Iterator<Movable> itr = movables.iterator();    
        //remove out of bound amo
        while(itr.hasNext()) {
            Movable m = (Movable)itr.next();
            if(m.propagate()==false){//movable has move to next lot
                //System.out.println("movable has move to next lot");
                if(!checkBound(m)){
                    itr.remove();
                }else{
                    switch(m.getName()){
                        case "greenpea":
                            lot[m.getRow()][m.getCol()-1].removeGreenpea((Greenpea)m);
                            lot[m.getRow()][m.getCol()].addGreenpea((Greenpea)m);
                            break;
                        case "zombie":
                            lot[m.getRow()][m.getCol()+1].removeZombie((Zombie)m);
                            lot[m.getRow()][m.getCol()].addZombie((Zombie)m);
                            break;
                    }                 
                }
            }  
        }    
        removeDeadZombieAndCollidedAmo();
    }

    private void generateZombie(){
        Random rand = new Random();
        if(gameProgress%10==0){
            Zombie zombie = new Zombie(rand.nextInt(lotHeight),lotWidth-1);
            movables.add(zombie);
            lot[zombie.getRow()][zombie.getCol()].addZombie(zombie);
        }
    }

    private void removeDeadZombieAndCollidedAmo(){
        ArrayList<Movable> remove = new ArrayList<>();
        for(int i=0;i<lotHeight;i++){
            for(int j=0;j<lotWidth;j++){
                remove.addAll(lot[i][j].checkSanity());
            }
        }
        for(Movable m:remove){
            movables.remove(m);
        }
    }
    //garden or sunflower is dropping sun

    private ArrayList<String> packState(){
        gardenDOM.updateStatus(money, gameProgress, suns);
        gardenDOM.updateDOM(movables,plants);
        //gardenDOM.updateStatic(plants);
        //gardenDOM.updateMovable(movables);
        return gardenDOM.parseGardenXML();
    }

   

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "new sun":
                int[] plantinfo = (int[])evt.getNewValue();
                //String log = "sunflower at "+ plantinfo[0]+" "+plantinfo[1];
                //System.out.println(log);
                suns++;
                break;
            case "fire green pea":
                //int row = ((Greenpea)evt.getNewValue()).getRow();
                Movable m = (Greenpea)evt.getNewValue();
                movables.add(m);
                lot[m.getRow()][m.getCol()].addGreenpea((Greenpea)m);
                //greenpeas[m.getRow()][(int)Math.floor(m.getPosition())]
                break;
           
        }
    }


    private boolean checkBound(Movable movable){
        int col = movable.getCol();
        if(col>=lotWidth || col<0){
            return false;
        }else{
            return true;
        }
    }
    
    
    
}