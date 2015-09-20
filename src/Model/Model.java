package Model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1>Model</h1>
 * Model interface represent a generally model layer of MVC
 */

import java.io.IOException;
import java.util.HashMap;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	
	HashMap<String, Maze3d> maze3dMap = new HashMap<String, Maze3d>();
	HashMap<String, Solution<Position>> solutionMap = new HashMap<String, Solution<Position>>();
	HashMap<String, Thread> openThreads = new HashMap<String,Thread>();

	/**
	* This method will return a string of files and directories of given a local path
	* @param path String represrnt the local path
	* @return String of files and directories of given a local path
	*/
	public String dir(String path);


	/**
	* This method will generate 3d maze with given name, generator and dimensions.
	* @param name String represent the name of the maze
	* @param generator represent a maze generator, default is MyMaze3dGenerator
	* @param floors represent the number of floors as string
	* @param lines represent the number of lines as string
	* @param columns represent the number of columns
	*/
	public void generateMazeWithName(String name, String generator, String floors, String lines,String columns);


	/**
	* This method return a Maze3d object with given name.
	* @param name String represent the name of the Maze3d instance to return.
	* @return Maze3d maze with the correct name.
	*/
	public Maze3d getMazeWithName(String string);


	/**
	* This method return int[][] represent the cross section by some axe with given index of maze
	* @param axe String represent the axe, options are X,Y,Z.
	* @param index String represent the index of the axe
	* @param name String represent the name of the Maze3d instance
	* @return int[][] represent the cross section by some axe with given index of maze
	*/
	public int[][] getCrossSectionByAxe(String axe, String index, String mazeName);


	/**
	* This method save an maze object in file via simple compress algorithem
	* @see MyCompressorOutputStream API. 
	* @param mazeName String represent instance name to save
	* @param fileName String represent name of the file that the maze will be save to.
	* @throws IOException of problems with writing to files\open new files and etc'
	*/
	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException;


	/**
	* This method will load an maze object from file as byte array via simple compress algorithem
	* @see MyCompressorOutputStream API. 
	* @param mazeName String represent instance name to save
	* @param fileName String represent name of the file that the maze will be save to.
	* @throws IOException of problems with writing to files\open new files and etc'
	*/
	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException;



	public double getSizeOfMazeInRam(String mazeName);



	public double getSizeOfMazeInFile(String fileName);



	public void solveMaze(String mazeName, String algorithm);



	public Solution<Position> getSolutionOfMaze(String mazeName);


	public void exit();



	public boolean isLoaded(String mazeName);

}
