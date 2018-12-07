package pvz2018;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import javax.print.attribute.standard.DateTimeAtCreation;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Garden extends AbstractGarden implements PropertyChangeListener, Serializable{// extends AbstractModel{

    GardenDOM gardenDOM = GardenDOM.getInstance();

    int lotWidth = 8;
    int lotHeight = 5;

    Map<String, String> plantXML = new HashMap<>();
    Map<String, String> ammunitionXML = new HashMap<>();
    Map<String, String> zombieXML = new HashMap<>();
    Stack<String> undoStack = new Stack<>();
    Stack<String> redoStack = new Stack<>();
   // Map<Integer,Stack<String>> roundStacks = new HashMap<>();
    Map<Integer,String> domStacks = new HashMap<>();
    GameStatus gameStatus;
    String tempPath = Utility.getResourceFilePath("temp");
    String savePath = Utility.getResourceFilePath("save");

    private static Garden garden;
    private Garden(){
        super();
        gameStatus = new GameStatus();
        saveStatus();
        zombieXML.put("plainZombie","<zombie side = \"0\" coordinateX=\"%d\" coordinateY=\""+lotWidth+"\" id=\"%s\" health=\"150\" name=\"zombiePlain\" speed = \"-1\" damage = \"1\"/>");
        plantXML.put("sunflower", "<plant side = \"1\" coordinateX=\"%d\" coordinateY=\"%d\" id=\"%s\" life=\"0\" health=\"50\" name=\"sunflower\" type=\"harmless\"/>");
        plantXML.put("peaShooter","<plant side = \"1\" coordinateX=\"%d\" coordinateY=\"%d\" id=\"%s\" life=\"0\" health=\"50\" name=\"peaShooter\" type=\"aggresive\"/>");
        plantXML.put("walnut", "<plant side = \"1\" coordinateX=\"%d\" coordinateY=\"%d\" id=\"%s\" health=\"200\" name=\"walnut\" type=\"harmless\"/>");
        ammunitionXML.put("greenPea","<ammunition health = \"0\" side = \"1\" coordinateX=\"%d\" coordinateY=\"%d\" id=\"%s\"  name=\"ogreenPea\" speed = \"2\" damage = \"200\"/>");
    }

    public static Garden getInstance(){
        if(garden==null){
            garden = new Garden();
        }
        return garden;
    }


    public void plantDefense(String plantName, int x, int y){
        if(gameStatus.money<gameStatus.getCost(plantName)){
            System.out.println("plant defense failed, not enough money");
            return;
        }
        if(gameStatus.plantInCD(plantName)){
            System.out.println(plantName+" is in CD");
            return;
        }
        String nodeAsString = String.format(plantXML.get(plantName),x,y,gameStatus.plantIndex);
        Node planted =gardenDOM.addPlant(nodeAsString,x,y);
        if(planted !=null){
            gameStatus.toggleCD(plantName, 1);
            gameStatus.plantIndex++;
            saveStatus();
        } 
    }
    public void removePlant(int row, int col){
        Element removedNode = (Element)gardenDOM.removePlant(Integer.toString(row),Integer.toString(col));
        if(removedNode!=null){
            saveStatus();
        }
    }
    private void saveStatus(){
        try {
            String path = Files.createTempFile(Paths.get(tempPath),gameStatus.gameProgress+"", ".ser").toString();
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameStatus);
            out.close();
            fileOut.close();
            undoStack.push(path);
            //System.out.printf("Serialized data is saved in %s",path);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void undo(){
        if(undoStack.size()==0){
            print("you are at the init state of this round, cant go ealier.");
        }
        String readPath = undoStack.pop();
        redoStack.push(readPath);
        readStatusFromFile(readPath);
    }
    

    public void redo(){
        if(redoStack.size()==0){
            print("already at last state of cur round. can't go further");

        }
        String readPath = redoStack.pop();
        undoStack.push(readPath);
        readStatusFromFile(readPath);
    }
    private void readStatusFromFile(String fileName){
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameStatus = (GameStatus)in.readObject();
            in.close();
            fileIn.close();
         } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            return;
         } 
    }

    void printStatus(){
        Field[] fields = GameStatus.class.getDeclaredFields();
        for(Field f : fields){
            if((f.getModifiers() & Modifier.FINAL) == Modifier.FINAL){
                continue;
            }
            try {
                print(f.getName() + ": " + f.getInt(gameStatus));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int plantCount = (Integer)gardenDOM.find(null, "count(//plant)", Integer.class);
        int zombieCount = (Integer)gardenDOM.find(null, "count(//zombie)", Integer.class);
        int amoCount = (Integer)gardenDOM.find(null, "count(//ammunition)", Integer.class);
        print("plant count:"+ plantCount);
        print("zombie count"+ zombieCount);
        print("amo count :"+amoCount);
    }

    public void load(String filename){
        if(gardenDOM.load(filename)){
            readStatusFromFile(savePath+"/"+filename+".ser");
        }
    }

    public void updateGame(){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        gameStatus.gameProgress++;
        if(gameStatus.gameProgress%3==0){
            gameStatus.money += 10;
        }
        gameStatus.decreaseCD();
        generateZombie();
        updatePlants();
        moveZombie();
        moveAmo();
        //serialize game status;
    }
    private void updatePlants(){
        NodeList plants = (NodeList)gardenDOM.find(null,"//plant",NodeList.class);
        for(int i = 0;i<plants.getLength();i++){
            Element plant = (Element)plants.item(i);
            String plantName = (String)gardenDOM.find(plant,".//@name",String.class);
            
            if(plantName == "walnut"){ //ignore those plant that doen's dependant on game progress
                continue;
            }

            Integer life = (Integer)gardenDOM.find(plant,".//@life",Integer.class);
            
            switch(plantName){
                case "sunflower":
                    plant.setAttribute("life",life+1+"");
                    if((life+1)%3 == 0){
                        gameStatus.money+=25;
                    }
                    break;
                case "peaShooter":
                    Integer coordX = (Integer)gardenDOM.find(plant,".//@coordinateX",Integer.class);
                    Integer coordY = (Integer)gardenDOM.find(plant,".//@coordinateY",Integer.class);
                    if(gardenDOM.scanZombie(coordX) && life % gameStatus.attackFreqPeaShooter == 0){
                        
                        String amo = String.format(ammunitionXML.get("greenPea"),coordX,coordY,++gameStatus.amoIndex);
                        gardenDOM.addMovables("ammunitions",amo);
                    }
                    plant.setAttribute("life",life+1+"");
                    break;
            }
        }
    }


    public String format(){
        StringBuffer str = new StringBuffer();
        NodeList creatures = (NodeList)gardenDOM.find(null, "./child::*/child::*/*", NodeList.class);
        for(int i =0;i<creatures.getLength();i++){
            Node creature = creatures.item(i);
            str.append(String.format("%c,%d,%d-",
            gardenDOM.getObjectName(creature).charAt(0),
            gardenDOM.getObjectX(creature),
            gardenDOM.getObjectY(creature)));
        }
       return str.toString();
    }

    private void print(Object s){
        System.out.println(s);
    }

    private void moveZombie(){
        NodeList zombies = (NodeList)gardenDOM.find(null,"//zombie",NodeList.class);
        for(int i = 0;i<zombies.getLength();i++){
            Element zombie = (Element)zombies.item(i);

            if(!gardenDOM.move(zombie)&&  gardenDOM.getObjectY(zombie)<0){ //zombie did not die after this move
                throw new Error("you lost your brain..");
            }

        }
    }

    private void generateZombie(){
        if(gameStatus.gameProgress % 5 == 0){
            for(int i = 0; i< gameStatus.gameProgress/2;i++){
                
                int randX = (new Random()).nextInt(lotHeight);
                String zombie = String.format(zombieXML.get("plainZombie"),randX,++gameStatus.zombieIndex);
                gardenDOM.addMovables("zombies",zombie);
            }
        }
    }

    public void save(String fn){
        if(gardenDOM.saveAs(fn)) {
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(savePath+"/" + fn + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(gameStatus);
                 out.close();
                fileOut.close();
            } catch ( IOException e) {
                e.printStackTrace();
			}
            
        }
    }
    private void moveAmo(){
        NodeList amos = (NodeList)gardenDOM.find(null,"//ammunition",NodeList.class);
        for(int i = 0;i<amos.getLength();i++){
            Element amo = (Element)amos.item(i);
            //Integer coordX = (Integer)gardenDOM.getObjectX(amo);
            Integer coordY =(Integer)gardenDOM.getObjectY(amo);
            if(coordY>lotWidth) {
                amo.getParentNode().removeChild(amo);
               // print("amo disappeared: "+GardenDOM.nodeToString(amo));
            }
            gardenDOM.move(amo);
          
        }
        
   }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    

  
    
}