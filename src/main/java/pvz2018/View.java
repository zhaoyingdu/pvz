package pvz2018;

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
//import Controller;//dev

public class View {

	Garden garden = Garden.getInstance();
	Scanner keyIn = new Scanner(System.in);

	public View() {
		newGame();
	}

	private void newGame() {
		String init = "";
		while (true) {
			System.out.print("Start new game?(Y/N)\n>");
			init = keyIn.next();
			init = init.trim().toUpperCase();
			// console.printf(init);

			if (init.equals("Y"))
				break;
			if (init.equals("N")) {
				// console.printf("bye bye..");
				System.exit(0);
			}
		}
		next();
	}

	public void next() {
		while (true) {

			Pattern commandRegex = Pattern.compile("(\\w+)\\s*");// finding a command

			Pattern plantCommand = Pattern.compile("(plant)\\s+(sunflower|peaShooter|walnut)\\s+([0-4])\\s+([0-7])");
			Pattern digCommand = Pattern.compile("(dig)\\s+([0-4])\\s+([0-7])");
			Pattern undoCommand = Pattern.compile("(undo)");
			Pattern redoCommand = Pattern.compile("(redo)");
			Pattern nextCommand = Pattern.compile("(next)");
			Pattern exitCommand = Pattern.compile("(save)");
			Pattern loadCommand = Pattern.compile("(load)");
			Pattern dataCommand = Pattern.compile("(getData)");

			Pattern helpCommand = Pattern.compile("(print)");
			System.out.print(">");
			/*print("enter command:\n" + ">plant [plant name] [row] [column]\n" + ">dig [row] [column]\n>" + ">undo\n"
					+ ">redo\n" + ">next\n" + ">exit\n" + ">help\n" + ">");*/
			String command = keyIn.nextLine();
			Matcher matcher = commandRegex.matcher(command);
			if (matcher.find()) {
				Object[] commandInfo = null;
				// String commandName = matcher.group(1);
				String commandName = matcher.group(1);
				// console.printf("_"+commandName+"_\n");
				switch (commandName.trim()) {
				case "plant":
					matcher = plantCommand.matcher(command);
					if (matcher.find()) {
						String plantName = matcher.group(2);
						int row = Integer.parseInt(matcher.group(3));
						int col = Integer.parseInt(matcher.group(4));
						garden.plantDefense(plantName, row, col);
					} else {
						print("plant command not correct\n");
						continue;
					}
					break;
				case "dig":
					matcher = digCommand.matcher(command);
					if (matcher.find()) {
						int row = Integer.parseInt(matcher.group(2));
						int col = Integer.parseInt(matcher.group(3));
						garden.removePlant(row, col);
					} else {
						print("dig command not correct\n");
						continue;
					}
					break;
				case "undo":
					matcher = undoCommand.matcher(command);
					if (matcher.find()) {
						garden.undo();
					} else {
						print("undo command not correct\n");
						continue;
					}
					break;
				case "redo":
					matcher = redoCommand.matcher(command);
					if (matcher.find()) {
						garden.redo();
					} else {
						print("redo command not correct\n");
						continue;
					}
					break;

				case "next":
					matcher = nextCommand.matcher(command);
					if (matcher.find()) {
						garden.updateGame();
					} else {
						print("next command not correct\n");
						continue;
					}
					break;
				case "save":
					matcher = exitCommand.matcher(command);
					if (matcher.find()) {
						System.out.print("enter file name to save");
						String filename = keyIn.next();
						garden.save(filename);//System.exit(0);
					}
					break;
				case "load":
					matcher = loadCommand.matcher(command);
					if (matcher.find()) {
						System.out.print("enter file name to load");
						String filename = keyIn.next();
						garden.load(filename);//System.exit(0);
					}
					break;
				case "print":
					matcher = helpCommand.matcher(command);
					if (matcher.find()) {
						garden.printStatus();
					} else {
						print("print command not correct\n");
					}
					break;
				case "getData":
					matcher = dataCommand.matcher(command);
					if (matcher.find()) {
						print(garden.format());
					} else {
						print("getData command not correct\n");
					}
					break;
				default:
					print("unrecognized command.\n");
					continue;
				}
			}
		}
	}

	private void print(String s){
			System.out.println(s);
		}

	

}
