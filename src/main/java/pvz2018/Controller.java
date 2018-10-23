package pvz2018;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;

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
        view.gardenPropertyChange(e);
    }

    private void setGardenProperty(String propertyName, Object newValue){
        

    }

    public void plantNewDefense(Object[] newValue){
        garden.plantDefense((String)newValue[0], (int)newValue[1], (int)newValue[1]);
    }
}
