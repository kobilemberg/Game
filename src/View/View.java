package View;

import java.util.HashMap;

import Controller.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	public void start();

	public void printFilesAndDirectories(String filesAndDirOfPath);

	public void tellTheUserMazeIsReady(String name);

	public void printMazeToUser(Maze3d mazeWithName,String name);
	
	//args[1] = Axe,args[2] = index ,args[3] = Name
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name);

	//args[1] = name, args[2] = filename
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename);

	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName);

	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size);

	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile);

	public void tellTheUserSolutionIsReady(String mazeName);

	public void printSolutionToUser(String mazeName,Solution<Position> solution);

	public void setCommands(HashMap<String, Command> viewCommandMap);
	

}
