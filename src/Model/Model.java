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


	public String dir(String string);



	public void generateMazeWithName(String name, String generator, String floors, String lines,String columns);



	public Maze3d getMazeWithName(String string);



	public int[][] getCrossSectionByAxe(String axe, String index, String mazeName);



	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException;



	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException;



	public double getSizeOfMazeInRam(String mazeName);



	public double getSizeOfMazeInFile(String fileName);



	public void solveMaze(String mazeName, String algorithm);



	public Solution<Position> getSolutionOfMaze(String mazeName);


	public void exit();

}
