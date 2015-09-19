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
String cliMenue;

	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		super();
		this.in = in;
		this.out = out;
		this.commands=commands;
		commandsStrings = new HashSet<>(commands.keySet());
	}

	public void start()
	{
		if (!cliMenue.equals(""))//Checking if menu is exists.
			System.out.println("	******************************Menue******************************\n"+cliMenue);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String inputLineString = scanner.nextLine();
		String[] inputLineAsArray = inputLineString.split(" ");
		if(inputLineString.toLowerCase().equals(inputLineString.toUpperCase()))//Checking if the input is null or: " "..
			start();//Start again
		while(!inputLineAsArray[0].equals("exit"))//While we need to continue
		{
			if(inputLineString.toLowerCase().equals(inputLineString.toUpperCase()))//Checking if the input is null or: " ".
				start();
			String commandString = new String(inputLineString);//Buillding the command
			String commandArgs =new String();//The commands arguments
			for (int i = inputLineAsArray.length-1; i >= 0; i--) {//For each word in the line from the end
				
				if(commandString.endsWith(" "))//If this is word line with word that already been cutted, remove the space between them 
					commandString = commandString.substring(0, commandString.length()-1);
				if(!commands.containsKey(commandString))//if this is not a command so the last word is parameter of command so
				{//we will remove the parameter and add to parameters string
					if(commandString.length()-inputLineAsArray[i].length()-1<=-1)//if we can't remove the word
					{
						break;
					}
					else //remove the parameter and add to parameters string
					{
						commandString = commandString.substring(0, commandString.length()-inputLineAsArray[i].length()-1);
						commandArgs+=inputLineAsArray[i]+" ";
					}

				}
				else if(commands.containsKey(commandString))//Else if command, 
				{//we need to remove the last " " from the string of args (if we can), make it string [] and reverse

					if(commandArgs.length()-1!=-1)
					{
						//System.out.println("CommandArgsFinal="+commandArgs.substring(0, commandArgs.length()-1));
						commandArgs=commandArgs.substring(0, commandArgs.length()-1);
						//commands.get(commandString).doCommand(    (  (new StringBuilder(commandArgs)).reverse().toString().split(" ")));
						//commands.get(commandString).doCommand(   commandArgs.split(" "));
						 List<String> list = Arrays.asList(commandArgs.split(" "));
					     Collections.reverse(list);
					     commands.get(commandString).doCommand(list.toArray(inputLineAsArray));
						break;
					}
					else//argument string is empty and we recognized a command before so we dont need a parameter 
						//so we make one in order to send to command
					{
						commands.get(commandString).doCommand(new String[1]);	
					}
					
				}
			}
			//if we didnt saw command after removing all the words we will notice
			if(!commands.containsKey(commandString))
				{System.out.println("Enter a valid command.");}
			inputLineString = scanner.nextLine();
			inputLineAsArray = inputLineString.split(" ");
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

	public void setCLIMenu(String cliMenu) {this.cliMenue=cliMenu;}

	
	
	
}