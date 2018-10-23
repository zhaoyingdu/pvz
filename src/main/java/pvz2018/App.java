package pvz2018;

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
    
    }
}
