package Controller;

import java.util.HashMap;

import Model.Model;
import View.View;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyController implements Controller {
	
	View view;
	Model model;
	public MyController(View view, Model model) {
		super();
		this.view = view;
		this.model = model;
		HashMap<String, Command> viewCommandMap = new HashMap<String, Command>();
		viewCommandMap.put("dir",new Command() 
		{
			@Override
			public void doCommand(String[] args) {view.printFilesAndDirectories(model.dir(args[1]));}
		});
		
		viewCommandMap.put("GenerateMaze",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				//args[1] = name, args[2] = GeneratorType, args[3] = Floors, args[4] = Lines, args[5] = Cols 
				 model.generateMazeWithName(args[1],args[2], args[3], args[4],args[5]);
			}
		});
		
		viewCommandMap.put("Diaplay",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				//args[1] = name
				try {
					view.printMazeToUser(model.getMazeWithName(args[1]),args[1]);
				} catch (NullPointerException e) {
					System.out.println("Exception: there is no maze named"+args[1]);
					e.printStackTrace();
				}
			}
		});
		
		viewCommandMap.put("GetCrossSectionByAxe",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				//args[1] = Axe,args[2] = index ,args[3] = Name
				try {
					int[][] crossedArr = model.getCrossSectionByAxe(args[1],args[2],args[3]);
					view.printToUserCrossedArray(crossedArr,args[1],args[2],args[3]);
				} catch (NullPointerException e) {
					System.out.println("Exception: problem with args");
					e.printStackTrace();
				}
			}
		});
		
		viewCommandMap.put("SaveMaze",new Command() 
		{
			@Override
			//args[1] = name, args[2] = filename
			public void doCommand(String[] args) {
				
				try {
					model.saveCompressedMazeToFile(args[1],args[2]);
					System.out.println("Maze saved.");
					view.tellTheUserTheMazeIsSaved(args[1],args[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		viewCommandMap.put("LoadMaze",new Command() 
		{
			@Override
			//args[1] = fileName, args[2] = name
			public void doCommand(String[] args) {
				
				try {
					model.loadAndDeCompressedMazeToFile(args[1],args[2]);
					
					System.out.println("Maze: "+args[2]+" saved to: "+args[1]);
					view.tellTheUserTheMazeIsLoaded(args[1],args[2]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		viewCommandMap.put("MazeSize",new Command() 
		{
			@Override
			//args[1] = mazeName
			public void doCommand(String[] args) {
					double sizeOfMazeInRam = model.getSizeOfMazeInRam(args[1]);
					view.tellTheUsersizeOfMazeInRam(args[1],sizeOfMazeInRam);
			}
		});
		
		viewCommandMap.put("FileSize",new Command() 
		{
			@Override
			//args[1] = Filename
			public void doCommand(String[] args) {
					double sizeOfFile = model.getSizeOfMazeInFile(args[1]);
					view.tellTheUsersizeOfMazeInFile(args[1],sizeOfFile);
			}
		});
		
		viewCommandMap.put("SolveMaze",new Command() 
		{
			@Override
			//args[1] = mazeName,args[2] = Algorithm
			public void doCommand(String[] args) {
					try {
						model.solveMaze(args[1],args[2]);
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					
					
			}
		});
		
		viewCommandMap.put("DisplaySolution",new Command() 
		{
			@Override
			//args[1] = mazeName
			public void doCommand(String[] args) {
					try {
						Solution<Position> solution = model.getSolutionOfMaze(args[1]);
						view.printSolutionToUser(args[1],solution);
					} catch (NullPointerException e) {
						System.out.println("Unsolvable maze\\unexisted solution");
						e.printStackTrace();
					}
					
					
			}
		});
		
		viewCommandMap.put("Exit",new Command() 
		{
			@Override
			//args[1] = mazeName
			public void doCommand(String[] args) {
					model.exit();
			}
		});
		
		view.setCommands(viewCommandMap);
	}
	
	public void setView(View view){this.view = view;}
	public void setModel(Model model){this.model = model;}

	@Override
	public void mazeIsReady(String name) {
		view.tellTheUserMazeIsReady(name);
		
	}

	@Override
	public void solutionIsReady(String mazeName) {
		view.tellTheUserSolutionIsReady(mazeName);
		
	}

	

	
	
	

}
