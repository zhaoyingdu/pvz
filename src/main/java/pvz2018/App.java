package pvz2018;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Controller ctl = new Controller();
		View ui = new View(ctl);
        ctl.attachView(ui);
       
    
            
        /*
        debugger-test invoking propertyChange in Controller
        try {
            Method fireChange = ctl.getClass().getMethod("propertyChange",
                    new Class[] { PropertyChangeEvent.class });
            try {
                Object returnValue = fireChange.invoke(ctl, new PropertyChangeEvent(garden,"sunflower",1,1));
                System.out.println("firechangeinvoked");
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    
    }
}
