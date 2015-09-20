package View;
import java.io.IOException;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> View </h1>
 * View interface represent a generally view layer of MVC
 */
import java.util.HashMap;

import Controller.Command;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	/**
	 * Starting the View layer
	 */
	public void start();

	/**
	 * Displays the directory/file path. 
	 * @param filesAndDirOfPath file location
	 */
	public void printFilesAndDirectories(String filesAndDirOfPath);

	/**
	 * Notify when the maze has finished building. 
	 * @param name Maze name. 
	 */
	public void tellTheUserMazeIsReady(String name);

	/**
	 * Display the maze to the user. 
	 * @param mazeWithName - maze3d maze
	 * @param name - maze name 
	 */
	public void printMazeToUser(Maze3d mazeWithName,String name);
	/**
	 * Get an Axe and an Index, and print the 2d matrix of the axe. 
	 * @param crossedArr - the array to print
	 * @param axe - the axe to print (x/y/z)
	 * @param index - the index to print
	 * @param name - maze name
	 */
	//args[1] = Axe,args[2] = index ,args[3] = Name
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name);
	/**
	 * Notify the user when the maze finishes saving.
	 * @param mazeName - maze name
	 * @param filename - file name
	 */
	//args[1] = name, args[2] = filename
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename);
	/**
	 * Notify the user the maze has finished loading. 
	 * @param fileName - file name
	 * @param mazeName - maze name  
	 */
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName);
	
	/**
	 * 
	 * @param mazeName
	 * @param size
	 */
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size);
	/**
	 * 
	 * @param fileName
	 * @param sizeOfFile
	 */
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile);
	/**
	 * 
	 * @param mazeName
	 */
	public void tellTheUserSolutionIsReady(String mazeName);
	/**
	 * 
	 * @param mazeName
	 * @param solution
	 */
	public void printSolutionToUser(String mazeName,Solution<Position> solution);
	/**
	 * 
	 * @param viewCommandMap
	 */
	public void setCommands(HashMap<String, Command> viewCommandMap);
	/**
	 * 
	 * @param cliMenu
	 */
	public void setCommandsMenu(String cliMenu);
	/**
	 * 
	 * @param s
	 */
	public void errorNoticeToUser(String s);
	

}
