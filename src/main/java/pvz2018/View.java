package pvz2018;

import java.io.Console;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
//import Controller;//dev

public class View{
	Console console;
	Controller gc; //game controller
	String sunFlowerView = "Sun Flower |.....|\n";
	String peaShooterView = "Pea Shooter |...|\n";
    String gardenView = "  0 1 2 3 4 5 6 7\n"+
                        "0 _ _ _ _ _ _ _ _\n"+
						"1 _ _ _ _ _ _ _ _\n"+
						"2 _ _ _ _ _ _ _ _\n"+
						"3 _ _ _ _ _ _ _ _\n"+
						"3 _ _ _ _ _ _ _ _\n";
    int suns = 0;
    String statusView = "suns: "+suns +"\n";
	//String welcome = 
	public View(Controller controller){

		

		console = System.console();
		if(console == null){
			System.err.println("No console..your OS doesn't support");
			System.exit(1);
		}

		gc = controller;
		newGame();
		printGame();

	}

	private void newGame(){
		String init ="";
		while(true){
			init = console.readLine("Start new game?(Y/N)\n>");
			init = init.trim().toUpperCase();
			//console.printf(init);

			if(init.equals("Y")) break;
			if(init.equals("N")) {
				console.printf("bye bye..");
				System.exit(0);
			}
		}

		gc.command_newGame();

	}

    public void plant(String plantName, int row, int col){
        console.printf("choose your defense");
    }

	public void printGame(){
        console.printf("+++++++++++++++++\n");
		console.printf(sunFlowerView);
        console.printf(peaShooterView);
        console.printf(statusView);
		console.printf(gardenView);
		console.printf("+++++++++++++++++\n");
	}

	//update view
	public void gardenPropertyChange(java.beans.PropertyChangeEvent e){
		//String propertyName
		switch(e.getPropertyName()){
			/*case "model initialization complete":
				console.printf("model initialization complete");
				printGame();
				break;*/
			default:
				console.printf("unknown model change...omg...");
		}
	}
	
}