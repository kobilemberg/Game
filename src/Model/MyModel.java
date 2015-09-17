package Model;

import java.io.File;
import java.io.FileInputStream;
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


	public String dir(String dir)
	{
		File folder = new File(dir);
		String strOfDir ="Files and Directories in: "+dir+"\n";
		for (String fileOrDirectory: folder.list())
		{
			strOfDir+=fileOrDirectory+"\n";
		}
		return strOfDir;
	}
	@Override
	public void generateMazeWithName(String name, String generator, String floors, String lines, String columns) {
		
		Thread generateMazeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Maze3dGenerator maze = new MyMaze3dGenerator();
				if(generator.equals("MyMaze3dGenerator"))
					maze = new MyMaze3dGenerator();
				else if(generator.equals("SimpleMazeGenerator"))
					maze = new SimpleMaze3dGenerator();
				else System.out.println("Name of generator, arg2 is not legal so the default generator will be MyMaze3dGenerator();");
				maze3dMap.put(name, maze.generate(new Integer(floors),new Integer(lines),new Integer(columns)));
				System.out.println("Model: Maze "+name+" is ready!");
				controller.mazeIsReady(name);
			}
		}, "generateMazeThread");
		openThreads.put(generateMazeThread.getName(), generateMazeThread);
		TP.submit(generateMazeThread);

		
	}
	@Override
	public Maze3d getMazeWithName(String nameOfMaze) {
		
		if(maze3dMap.containsKey(nameOfMaze))
			return maze3dMap.get(nameOfMaze);
		System.out.println("There is no maze named: "+nameOfMaze);
		return null;
	}
	@Override
	public int[][] getCrossSectionByAxe(String axe, String index, String mazeName) {
		
		int[][] arrToRet = null;
		if(maze3dMap.containsKey(mazeName))
		{
			Maze3d maze = maze3dMap.get(mazeName);
			//Floors
			if(axe.equals("Floors"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze().length)
				{
					arrToRet = maze.getCrossSectionByX(new Integer(index));
				}
				else{System.out.println("illegal index, llegal indexes are:0-"+maze.getMaze().length);}
			}
			//Lines
			else if(axe.equals("Lines"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0].length)
				{
					arrToRet = maze.getCrossSectionByY(new Integer(index));
				}
				else{System.out.println("illegal index, llegal indexes are:0-"+maze.getMaze()[0].length);}
			}
			//Columns
			else if(axe.equals("Columns"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0][0].length)
				{
					arrToRet = maze.getCrossSectionByZ(new Integer(index));
				}
				else{System.out.println("illegal index, llegal indexes are:0-"+maze.getMaze()[0][0].length);}
			}
			
			else{System.out.println("incorrect axe, the options are: Floors,Lines,Columns");}
		}
			
		System.out.println("There is no maze named: "+mazeName);
		return arrToRet;
		
	}
	@Override
	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException {
		
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
				else{System.out.println("It seems that maze exists");}
					
			}
		
		}
		else
		{
			System.out.println("The name is incorrect");
		}
	
		
	}
	@Override
	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException {
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
			System.out.println("Model: maze is saved");
			
		}
		else
		{
			System.out.println("File not found");
		}
	}
	@Override
	public double getSizeOfMazeInRam(String mazeName) {
		if(maze3dMap.containsKey(mazeName))
			return maze3dMap.get(mazeName).toByteArray().length;
		System.out.println("There is no maze named: "+mazeName);
		return 0;
	}
	@Override
	public double getSizeOfMazeInFile(String fileName) {
		File f = new File(fileName);
		if(!f.exists())
		{
			System.out.println("File "+fileName+" doesnt exists");
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
						if(algorithm.equals("BFS"))
						{		
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							controller.solutionIsReady(mazeName);
						}
						else if(algorithm.equals("A*"))
						{
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							controller.solutionIsReady(mazeName);
						}
						else
						{System.out.println("Algorithm: "+algorithm+" is not valid!");}
					}
				}
				else
				{System.out.println("There is no maze named: "+mazeName);}
			}
		}),"MazeSolverThread");
		openThreads.put(mazeSolver.getName(), mazeSolver);
		TP.submit(mazeSolver);
	}
	@Override
	public Solution<Position> getSolutionOfMaze(String mazeName) {
		if(solutionMap.containsKey(mazeName)){return solutionMap.get(mazeName);}
		System.out.println("maze wasn't solve!");
		return null;
	}
	@Override
	public void exit() {
		try {
			TP.shutdownNow();
		} catch (Exception e) {
			System.out.println("ThreadPool exit error");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

	

	
	
	
}
