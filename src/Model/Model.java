package Model;


import java.io.IOException;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	
	HashMap<String, Maze3d> maze3dMap = new HashMap<String, Maze3d>();
	HashMap<String, Solution<Position>> solutionMap = new HashMap<String, Solution<Position>>();
	HashMap<String, Thread> openThreads = new HashMap<String,Thread>();


	String dir(String string);



	void generateMazeWithName(String name, String generator, String floors, String lines,String columns);



	Maze3d getMazeWithName(String string);



	int[][] getCrossSectionByAxe(String axe, String index, String mazeName);



	void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException;



	void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException;



	double getSizeOfMazeInRam(String mazeName);



	double getSizeOfMazeInFile(String fileName);



	void solveMaze(String mazeName, String algorithm);



	Solution<Position> getSolutionOfMaze(String mazeName);



	void exit();

}
