package View;



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

	
	public MyView()
	{
		super();
	}
	public MyView(Controller controller)
	{
		super();
		this.controller = controller;
		cli = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out),viewCommandMap);
	}
	
	public void setController(Controller controller)
	{this.controller = controller;
	cli = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out),viewCommandMap);
	if(cliMenu!=null)
		cli.cliMenu = cliMenu;
	
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
		System.out.println(strOfMazeMatrix);
	}

	@Override
	public void printFilesAndDirectories(String filesAndDirOfPath) {System.out.println(filesAndDirOfPath);}
	
	@Override
	public void tellTheUserMazeIsReady(String name) {System.out.println("View: Maze "+name+" is Ready, you can take it!");}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {System.out.println("Maze: "+name+"\n"+mazeWithName.toString());}
	
	@Override
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		System.out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		printArr(crossedArr);
	}
	
	@Override
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename) {System.out.println("Maze: "+mazeName+ " saved to:"+ filename);}
	
	@Override
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName) {System.out.println("Maze: "+mazeName+ " has been loaded from:"+ fileName);}
	
	@Override
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size) {System.out.println("The size of maze: "+mazeName+" in ram memory is:" +size+"b");}
	
	@Override
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile) {System.out.println("The size of file: "+fileName+" is: "+sizeOfFile+"b");	}
	
	@Override
	public void tellTheUserSolutionIsReady(String mazeName) {System.out.println("Solution for "+mazeName+" is Ready, you can take it!");}
	
	@Override
	public void printSolutionToUser(String mazeName,Solution<Position> solution) {
		System.out.println("Solution of: "+mazeName+"\n");
		for (State<Position> p: solution.getSolution()){System.out.println(p.getCameFromAction() + " To: "+p.toString());}
	}
	
	@Override
	public void setCommands(HashMap<String, Command> viewCommandMap) 
	{this.viewCommandMap = viewCommandMap;
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
