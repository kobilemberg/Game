package View;

import java.util.HashMap;

import Controller.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	void start();

	void printFilesAndDirectories(String filesAndDirOfPath);

	void tellTheUserMazeIsReady(String name);

	void printMazeToUser(Maze3d mazeWithName,String name);
	
	//args[1] = Axe,args[2] = index ,args[3] = Name
	void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name);

	//args[1] = name, args[2] = filename
	void tellTheUserTheMazeIsSaved(String mazeName, String filename);

	void tellTheUserTheMazeIsLoaded(String fileName, String mazeName);

	void tellTheUsersizeOfMazeInRam(String mazeName,Double size);

	void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile);

	void tellTheUserSolutionIsReady(String mazeName);

	void printSolutionToUser(String mazeName,Solution<Position> solution);

	void setCommands(HashMap<String, Command> viewCommandMap);
	

}
