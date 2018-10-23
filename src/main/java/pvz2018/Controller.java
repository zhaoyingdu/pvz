package pvz2018;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Controller implements PropertyChangeListener{
    View view;
    Garden garden;

    private void attachModel(Garden garden){
        this.garden=garden;
        garden.addPropertyChangeListener(this);
    }
    public void attachView(View ui){
        this.view = ui;
    }

    public void command_newGame(){
        System.out.println("CTL: init new game model");
        garden = new Garden();
        attachModel(garden);
        //view.printGame();//dirty..print game should private..but this is just to test
    }

    public void propertyChange(PropertyChangeEvent e){
        System.out.println("Propergating Event");
        if(view!=null){
            view.gardenPropertyChange(e);
        }else{
            System.out.println("view not initialized");
        }
    }

    public void nextStep(Object[] stepInfo){
        switch((String)stepInfo[0]){
            case "plant":
                plantNewDefense(Arrays.copyOfRange(stepInfo, 1, 4));
                break;
            case "collect":
                garden.collectSuns();
                break;
            case "idle":
                garden.idle(stepInfo[1]);
                break;
                
        }

    }

    private void plantNewDefense(Object[] newValue){
        garden.plantDefense((String)newValue[0], (int)newValue[1], (int)newValue[2]);
    }
}
