package View;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyView </h1>
 * MyView class implements View interface, 
 * class goal is to act as MVC View layer and to display applications to end-user.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import Controller.Command;
import Controller.Controller;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class MyView implements View {
	Controller controller;
	CLI cli;
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in;
	PrintWriter out;

	//Constructors
		/**
		 * Instantiates a new  my own maze3d generator.
		 */
	public MyView()
	{
		super();
	}
	public MyView(Controller controller)
	{
		super();
		this.controller = controller;
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
		//cli = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out),controller.viewCommandMap);
	}
	public MyView(Controller controller, BufferedReader in, PrintWriter out,HashMap<String, Command> viewCommandMap)
	{
		super();
		this.controller = controller;
		cli = new CLI(in, out,viewCommandMap);
	}
	public MyView(Controller controller, BufferedReader in, PrintWriter out)
	{
		super();
		this.controller = controller;
		this.in = in;
		this.out = out;
	}
	public void setController(Controller controller)
	{
		this.controller = controller;
	}
	
	@Override
	public void start() {cli.start();}


	
	public void printArr(int[][] arr)
	{
		String strOfMazeMatrix="";
		for (int i=0;i<arr.length;i++)
		{
			strOfMazeMatrix+="{";
			for(int j=0;j<arr[0].length;j++)
			{
						strOfMazeMatrix+=arr[i][j];
			}
			strOfMazeMatrix+="}\n";
		}
		out.println(strOfMazeMatrix);
	}

	@Override
	public void printFilesAndDirectories(String filesAndDirOfPath) {out.println(filesAndDirOfPath);}
	
	@Override
	public void tellTheUserMazeIsReady(String name) {out.println("View: Maze "+name+" is Ready, you can take it!");}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {out.println("Maze: "+name+"\n"+mazeWithName.toString());}
	
	@Override
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		printArr(crossedArr);
	}
	
	@Override
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename) {out.println("Maze: "+mazeName+ " saved to:"+ filename);}
	
	@Override
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName) {out.println("Maze: "+mazeName+ " has been loaded from:"+ fileName);}
	
	@Override
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size) {out.println("The size of maze: "+mazeName+" in ram memory is:" +size+"b");}
	
	@Override
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile) {out.println("The size of file: "+fileName+" is: "+sizeOfFile+"b");	}
	
	@Override
	public void tellTheUserSolutionIsReady(String mazeName) {out.println("Solution for "+mazeName+" is Ready, you can take it!");}
	
	@Override
	public void printSolutionToUser(String mazeName,Solution<Position> solution) {
		out.println("Solution of: "+mazeName+"\n");
		for (State<Position> p: solution.getSolution()){out.println(p.getCameFromAction() + " To: "+p.toString());}
	}
	
	@Override
	public void setCommands(HashMap<String, Command> viewCommandMap) 
	{
		this.viewCommandMap = viewCommandMap;
		cli = new CLI(in, out,viewCommandMap);
		if(cliMenu!=null)
			cli.cliMenu = cliMenu;
	}
	@Override
	public void setCommandsMenu(String cliMenu) {
		this.cliMenu = cliMenu;
	}
	@Override
	public void errorNoticeToUser(String s) {
		System.out.println("Error:\n"+s);
		
	}
	
	
}
