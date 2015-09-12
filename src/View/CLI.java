package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI 
{
BufferedReader in;
PrintWriter out;
	public CLI(BufferedReader in, PrintWriter out) {
		super();
		this.in = in;
		this.out = out;
	}

	public void start()
	{
		String inputWord = "";
		Scanner s = new Scanner(System.in);
		while(!s.next().equals("exit"))
		{
			
		}
	}

}