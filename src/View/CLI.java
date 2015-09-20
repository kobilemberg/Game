package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import Controller.Command;

public class CLI implements Runnable
{
BufferedReader in;
PrintWriter out;
HashMap<String,Command> commands;
HashSet<String> commandsStrings;
String cliMenu;

	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		super();
		this.in = in;
		this.out = out;
		this.commands=commands;
		commandsStrings = new HashSet<>(commands.keySet());
	}

	public void start()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String inputLineString = "";
		String[] inputLineAsArray = {};
		
		
		// Input command. 
		// While input is not valid ("" or "    "..), do it again.
		while (inputLineString.length() == 0){
			//Print Menu (cliMenu). 
			if (!cliMenu.equals(""))
			{
				System.out.println("	******************************Menu******************************");
				System.out.println(cliMenu);
			}
			
			inputLineString = scanner.nextLine();
			inputLineString = inputLineString.toLowerCase().replaceAll("\\s+", " ").trim();
			inputLineAsArray = inputLineString.split(" ");
		}
		
		while(!inputLineString.startsWith("exit"))
		{
			String commandString = new String(inputLineString); //Building the command
			String commandArgs   = new String(); //The commands arguments 
			int i = 0;
			
			boolean foundCommand = false;
			// Go through the input string backwards, and find the longest command possible. 
			// Once found, the loop stops, and sends the command with the arguments found.
			// flag: foundCommand
			while(!foundCommand){
				commandString.trim();
				if (commands.containsKey(commandString))
				{
					foundCommand = true;
					commandArgs=commandArgs.trim();
					if (commandArgs.length()>0)
					{
						List<String> list = Arrays.asList(commandArgs.split(" "));
					    Collections.reverse(list);
					    commands.get(commandString).doCommand(list.toArray(inputLineAsArray));
					}
					else if (commandArgs.length() == 0)
					{
						//Argument array is empty, send the command as it is.
						commands.get(commandString).doCommand(new String[1]);	
					}
				}
				else if (!commands.containsKey(commandString))
				{
					if(commandString.length()-inputLineAsArray[inputLineAsArray.length-1].length()-1<=-1)//if we can't remove the word
					{
						break;
					}
					else //remove the parameter and add to parameters string
					{
						i++;
						commandString = commandString.substring(0, commandString.length()-inputLineAsArray[inputLineAsArray.length-i].length()-1);
						commandArgs+=inputLineAsArray[inputLineAsArray.length-i]+" ";
					}
				}
			}
			
			//if we didn't see command after removing all the words we will notice
			if(!commands.containsKey(commandString))
				{System.out.println("Enter a valid command.");}
			
			inputLineString = ""; 
			// Input command. 
			// While input is not valid ("" or "    "..), do it again.
			while (inputLineString.length() == 0){
				//Print Menu (cliMenu). 
				if (!cliMenu.equals(""))
				{
					System.out.println("	******************************Menu******************************");
					System.out.println(cliMenu);
				}
				
				inputLineString = scanner.nextLine();
				inputLineString = inputLineString.toLowerCase().replaceAll("\\s+", " ").trim();
				inputLineAsArray = inputLineString.split(" ");
			}
		}
		commands.get(inputLineAsArray[0]).doCommand(inputLineAsArray);
		System.out.println("Exiting from the program...Bye!");
	}
	@Override
	public void run() {
		System.out.println("Running Run() Method of CLI class" );
		start();	
	}

	public BufferedReader getIn() {return in;}

	public void setIn(BufferedReader in) {this.in = in;}

	public PrintWriter getOut() {return out;}

	public void setOut(PrintWriter out) {this.out = out;}

	public HashMap<String, Command> getCommands() {return commands;}

	public void setCommands(HashMap<String, Command> commands) 
	{
		this.commands = commands;
		commandsStrings = new HashSet<>(commands.keySet());}

	public void setCLIMenu(String cliMenu) {this.cliMenu=cliMenu;}

	
	
	
}