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
        while(true){

			Pattern commandRegex = Pattern.compile("(\\w+)\\s");//finding a command
			
			Pattern plantCommand = Pattern.compile("(plant)\\s+(sunflower|peashooter)\\s+([0-4])\\s+([0-7])");
			Pattern digCommand = Pattern.compile("(dig)\\s+([0-4])\\s+([0-7])");
			Pattern collectCommand = Pattern.compile("(collect)");
			Pattern idleCommand = Pattern.compile("(idle)\\s+([0-9])");
			Pattern exitCommand = Pattern.compile("(exit)");


			String command=console.readLine("enter command:\n"+
							">plant [plant name] [row] [column]\n"+
							">dig [row] [column]\n>"+
							">collect\n"+
							">idle [rounds]\n"+
							">exit\n"+
							">");
			Matcher matcher = commandRegex.matcher(command); 
			if(matcher.find()){
				Object[] commandInfo =null;
				//String commandName = matcher.group(1);
				String commandName = matcher.group(1);
				console.printf(commandName+"\n");
				switch(commandName.trim()){
					case "plant":	
						matcher = plantCommand.matcher(command);
						if(matcher.find()){
							String plantName = matcher.group(2);
							int row = Integer.parseInt(matcher.group(3));
							int col = Integer.parseInt(matcher.group(4));
							commandInfo = new Object[]{commandName,plantName,row,col};
						}else{
							console.printf("plant command not correct");
						}
						break;
					case "dig":
						matcher = digCommand.matcher(command);
						if(matcher.find()){
							int row = Integer.parseInt(matcher.group(2));
							int col = Integer.parseInt(matcher.group(3));
							commandInfo = new Object[]{commandName, row,col};
						}else{
							console.printf("dig command not correct");
						}
						break;
					case "collect":
						matcher = collectCommand.matcher(command);
						if(matcher.find()){
							commandInfo = new Object[]{commandName};
						}else{
							console.printf("collect command not correct");
						}
						break;
					case "idle":
						matcher = idleCommand.matcher(command);
						if(matcher.find()){
							int rounds = Integer.parseInt(matcher.group(2));
							commandInfo = new Object[]{commandName,rounds};
						}else{
							console.printf("idle command not correct");
						}
						break;
					case "exit":
						matcher = exitCommand.matcher(command);
						if(matcher.find()){
							System.exit(0);
						}
						break;
						
				}
				gc.nextStep(commandInfo);
			}
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
		//console.printf(e.getPropertyName());
		switch(e.getPropertyName()){
			case "planted":
				unpackState((Map<String,Object>)e.getNewValue());
				printGame();
				break;
			case "plant removed":
				unpackState((Map<String,Object>)e.getNewValue());
				printGame();
				break;
			case "sun droped":
				console.printf("new sun\n");
				suns++;
				printGame();
				break;
			case "sun collected":
				unpackState((Map<String,Object>)e.getNewValue());
				printGame();
				break;
			case "back":
				unpackState((Map<String,Object>)e.getNewValue());
				printGame();
				break;
			case "plant failed":
				console.printf((String)e.getNewValue());
				break;
			default:
				console.printf("unknown model change...omg...");
		}
	}
	//utility to update the view;

	private void unpackState(Map<String,Object> state){
		suns=(int)state.get("suns");
		money=(int)state.get("money");
		statusView = "suns: "+suns+" money: "+money+"\n";
		updateCD(state);
		parseLayout((Plant[][])state.get("layout"));
		//printGame();
	}

	private void updateCD(Map<String,Object> state){
		sunFlowerView = "Sun Flower |";//".....|\n";
		for(int i=0;i<(int)state.get("sunflowerCD");i++){
			sunFlowerView+='.';
		}
		sunFlowerView += "|\n";	

	}

	public void parseLayout(Plant[][] layout){

		int rows=layout.length;
		int cols = layout[0].length;
		
		for(int row = 0;row<rows;row++){
			for(int col = 0; col<cols;col++){
				if(layout[row][col]!=null){
					gardenView.setCharAt(18+row*18+2+col*2,layout[row][col].getName().charAt(0));
				}else{
					gardenView.setCharAt(18+row*18+2+col*2,'_');
				}
			}
		}
	}
	
}


	
	
