package Model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyModel </h1>
 * MyModel class implements Model interface, 
 * class goal is to act as MVC Model and perform all business logic calculations.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Controller.Controller;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.demo.Demo;
import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;

public class MyModel implements Model {
	Controller controller;
	ExecutorService TP = Executors.newCachedThreadPool();
	
	
	
	//Constructors
		/**
		 * Instantiates a new  my own maze3d generator.
		 */
	public MyModel()
	{
		super();
	}
	public MyModel(Controller controller)
	{
		super();
		this.controller = controller;
	}
	public void setController(Controller controller){this.controller = controller;}


	public String dir(String dir) throws NullPointerException
	{
		File folder = new File(dir);
		
		if(folder.exists()&&folder.isDirectory())
		{
			
			//if(folder.isDirectory())
			{
				String strOfDir ="Files and Directories in: "+dir+"\n";
				for (String fileOrDirectory: folder.list())
				{
					strOfDir+=fileOrDirectory+"\n";
				}
				System.out.println("StrOfDir: "+strOfDir);
				return strOfDir;
			}
		}
		errorNoticeToControlelr("Error in path");
		return null;
	}
	@Override
	public void generateMazeWithName(String name, String generator, String floors, String lines, String columns) {
		
		if(floors.isEmpty()||lines.isEmpty()||columns.isEmpty())
		{
			throw new NullPointerException();
		}
		
		Thread generateMazeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Maze3dGenerator maze = new MyMaze3dGenerator();
				if(generator.equals("mymaze3dgenerator"))
					maze = new MyMaze3dGenerator();
				else if(generator.equals("simplemazegenerator"))
					maze = new SimpleMaze3dGenerator();
				else 
					maze = new MyMaze3dGenerator();
				
				if(!floors.isEmpty()&&!lines.isEmpty()&&!columns.isEmpty())
				{
					maze3dMap.put(name, maze.generate(new Integer(floors),new Integer(lines),new Integer(columns)));
					controller.mazeIsReady(name);
					
				}
				else
				{	
					errorNoticeToControlelr("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
				}
				
			}
		}, "generateMazeThread");
		openThreads.put(generateMazeThread.getName(), generateMazeThread);
		TP.submit(generateMazeThread);

		
	}
	@Override
	public Maze3d getMazeWithName(String nameOfMaze) {
		
		if(maze3dMap.containsKey(nameOfMaze))
			return maze3dMap.get(nameOfMaze);
	//	System.out.println("There is no maze named: "+nameOfMaze);
		return null;
	}
	@Override
	public int[][] getCrossSectionByAxe(String axe, String index, String mazeName) {
		int[][] arrToRet = null;
		if(maze3dMap.containsKey(mazeName))
		{
			Maze3d maze = maze3dMap.get(mazeName);
			//Floors
			if(axe.equals("x"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze().length)
				{
					arrToRet = maze.getCrossSectionByX(new Integer(index));
				}
				else{errorNoticeToControlelr("illegal index, llegal indexes are:0-"+maze.getMaze().length);}
			}
			//Lines
			else if(axe.equals("y"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0].length)
				{
					arrToRet = maze.getCrossSectionByY(new Integer(index));
				}
				else{errorNoticeToControlelr("illegal index, llegal indexes are:0-"+maze.getMaze()[0].length);}
			}
			//Columns
			else if(axe.equals("z"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0][0].length)
				{
					arrToRet = maze.getCrossSectionByZ(new Integer(index));
				}
				else{System.out.println("illegal index, llegal indexes are:0-"+maze.getMaze()[0][0].length);}
			}
			
			else{errorNoticeToControlelr("incorrect axe, the options are: X,Y,Z");}
		}
			
		//System.out.println("There is no maze named: "+mazeName);
		return arrToRet;
		
	}
	@Override
	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException {
		
		if(fileName.isEmpty()||mazeName.isEmpty())
		{
			errorNoticeToControlelr("Cannot resolve filename\\maze name");
		}
		else
		{
			if(maze3dMap.containsKey(mazeName))
			{
				File fileCreator = new File(fileName);
				if(fileCreator.exists())
				{
					@SuppressWarnings("resource")
					OutputStream out=new MyCompressorOutputStream(new FileOutputStream(fileName));
					out.write(maze3dMap.get(mazeName).toByteArray());
				}
				else if(!fileCreator.exists())
				{
					if(fileCreator.createNewFile())
					{
						OutputStream out=new MyCompressorOutputStream(new FileOutputStream(fileName));
						out.write(maze3dMap.get(mazeName).toByteArray());
						out.flush();
						out.close();
					}
					else{errorNoticeToControlelr("It seems that file exists/Cannot create file.");}
						
				}
			
			}
			else
			{
				
				errorNoticeToControlelr("The name is incorrect");
				throw new NullPointerException("There is no maze " +mazeName);
			}
		}
	}
	@Override
	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException {
		if(fileName.isEmpty()||mazeName.isEmpty())
		{
			errorNoticeToControlelr("File not found\\Cannot resolve maze name");
		}
		else
		{
			File file = new File(fileName);
			if(file.exists())
			{
				
				@SuppressWarnings("resource")
				FileInputStream fileIn = new FileInputStream(fileName);//Opening the file
				byte[] dimensionsArr = new byte[12];//array of diemensions
				fileIn.read(dimensionsArr, 0, 12);//reading from file to the array
				int xLength,yLength,zLength;//setting parameters
				byte[] copyArr = new byte[4];//extracting int
				//xLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i];}
				xLength = ByteBuffer.wrap(copyArr).getInt();
				
				//yLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i+4];}
				yLength = ByteBuffer.wrap(copyArr).getInt();
				
				//zLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i+8];}
				zLength = ByteBuffer.wrap(copyArr).getInt();
				
				
				byte[] mazeMatrix = new byte[xLength*yLength*zLength + 36];
				
				InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName));
				in.read(mazeMatrix);
				in.close();
				Maze3d mazeToSave = new Maze3d(mazeMatrix);
				maze3dMap.put(mazeName, mazeToSave);

				
			}
			else
			{

				errorNoticeToControlelr("File not found");
				throw new FileNotFoundException("File not found");
			}
		}
		
	}
	@Override
	public double getSizeOfMazeInRam(String mazeName) {
		if(maze3dMap.containsKey(mazeName))
			return maze3dMap.get(mazeName).toByteArray().length;
		errorNoticeToControlelr("There is no maze named: "+mazeName);
		return 0;
	}
	@Override
	public double getSizeOfMazeInFile(String fileName) {
		File f = new File(fileName);
		if(!f.exists())
		{
			errorNoticeToControlelr("File "+fileName+" doesnt exists");
			return 0;
		}
		return f.length();

	}
	@Override
	public void solveMaze(String mazeName, String algorithm) 
	{
		Thread mazeSolver = new Thread((new Runnable() {		
			@Override
			public void run() 
			{
				if(maze3dMap.containsKey(mazeName))
				{
					Demo d = new Demo();
					SearchableMaze3d searchableMaze = new SearchableMaze3d(maze3dMap.get(mazeName));
					if(solutionMap.containsKey(mazeName))
						controller.solutionIsReady(mazeName);
					else
					{
						if(algorithm.equals("bfs"))
						{		
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							controller.solutionIsReady(mazeName);
						}
						else if(algorithm.equals("a*"))
						{
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							controller.solutionIsReady(mazeName);
						}
						else
						{errorNoticeToControlelr("Algorithm: "+algorithm+" is not valid!");}
					}
				}
				else
				{errorNoticeToControlelr("There is no maze named: "+mazeName);}
			}
		}),"MazeSolverThread");
		openThreads.put(mazeSolver.getName(), mazeSolver);
		TP.submit(mazeSolver);
	}
	@Override
	public Solution<Position> getSolutionOfMaze(String mazeName) {
		if(solutionMap.containsKey(mazeName)){return solutionMap.get(mazeName);}
		errorNoticeToControlelr("maze wasn't solve!");
		return null;
	}
	@Override
	public void exit() {
		try {
			TP.shutdownNow();
		} catch (Exception e) {
			errorNoticeToControlelr("ThreadPool exit error");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public void errorNoticeToControlelr(String s)
	{
		controller.errorNoticeToViewr(s);
	}
	@Override
	public boolean isLoaded(String mazeName) {
		return maze3dMap.containsKey(mazeName);
	}
	

	

	
	
	
}
