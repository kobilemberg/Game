package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import Controller.Command;

public class CLI implements Runnable
{
BufferedReader in;
PrintWriter out;
HashMap<String,Command> commands;

	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		super();
		this.in = in;
		this.out = out;
		this.commands=commands;
	}

	public void start()
	{
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String inputWord = s.next();
		String[] args = inputWord.split(" ");
		while(!args[0].equals("exit"))
		{
			if(commands.containsKey(inputWord))
				commands.get(inputWord).doCommand(args);
			else{System.out.println("Enter a valid command.");}
			inputWord = s.next();
			 args = inputWord.split(" ");
		}
		commands.get("exit").doCommand(args);
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

	public void setCommands(HashMap<String, Command> commands) {this.commands = commands;}

	
	
	
}