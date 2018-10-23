package pvz2018;

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
//import Controller;//dev

public class View{

	enum plants{sunflower,peashooter};



	Console console;
    Controller gc; //game controller
	
	String sunFlowerView = "Sun Flower ||\n";
	String peaShooterView = "Pea Shooter ||\n";
    StringBuilder gardenView = new StringBuilder(
						"  0 1 2 3 4 5 6 7\n"+
                        "0 _ _ _ _ _ _ _ _\n"+
						"1 _ _ _ _ _ _ _ _\n"+
						"2 _ _ _ _ _ _ _ _\n"+
						"3 _ _ _ _ _ _ _ _\n"+
						"4 _ _ _ _ _ _ _ _\n");
	int suns = 0;
	int money = 0;
    String statusView = "suns: "+suns+" money: "+money+"\n";
	//String welcome = 
	public View(Controller controller){
		//plantCD.put("sunflower",Sunflower.coolDown);
		console = System.console();
		if(console == null){
			System.err.println("No console..your OS doesn't support");
			System.exit(1);
		}

		gc = controller;
		newGame();
		

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
		gc.attachView(this);
        printGame();
        next();
	}


    public void next(){
        //"plant\s"
        Pattern commandRegex = Pattern.compile("(plant)\\s+(sunflower|peashooter)\\s+([0-4])\\s+([0-7])");
        String command=console.readLine("enter command:\n"+
                        "plant [plant name] [row] [column]\n"+
						"dig [row] [column]\n>"+
						"collect"+
						"idle [rounds]");
        Matcher matcher = commandRegex.matcher(command); 
		if(matcher.find()){
			Object[] commandInfo =null;
			String commandName = matcher.group(1);
			switch(commandName){
				case "plant":				
					String plantName = matcher.group(2);
					int row = Integer.parseInt(matcher.group(3));
					int col = Integer.parseInt(matcher.group(4));
					commandInfo = new Object[]{commandName,plantName,row,col};
					break;
				case "collect":
					commandInfo = new Object[]{commandName};
					break;
				case "idle":
					int rounds = Integer.parseInt(matcher.group(2));
					commandInfo = new Object[]{commandName,rounds};
					break;
			}
			gc.nextStep(commandInfo);
		}
    }


	public void printGame(){
        console.printf("\n+++++++++++++++++\n");
		console.printf(sunFlowerView);
        console.printf(peaShooterView);
        console.printf(statusView);
		console.printf(gardenView.toString());
		console.printf("+++++++++++++++++\n\n");
	}

	//update view
	public void gardenPropertyChange(PropertyChangeEvent e){
		console.printf(e.getPropertyName());
		switch(e.getPropertyName()){
			case "planted":
				Object[] newValue = (Object[])e.getNewValue();
				gardenView.setCharAt(18+(int)newValue[1]*18+2+(int)newValue[2]*2,(char)newValue[0]);
				updateCD((char)newValue[0]);
				printGame();
				break;
			case "sun droped":
				console.printf("new sun\n");
				suns++;
				printGame();
				break;
			case "sunCollected":
				suns=0;
				money = (int)e.getNewValue();
				printGame();
				break;
			case "back";
				
			default:
				console.printf("unknown model change...omg...");
		}
	}
	//utility to update the view;
	private void updateCD(char plantInitial){
		switch(plantInitial){
			case 's':
				sunFlowerView = "Sun Flower |";//".....|\n";
				for(int i=0;i<Sunflower.coolDown;i++){
					sunFlowerView+='.';
				}
				sunFlowerView += "|\n";
				break;
			default:

		}
	}
	
}