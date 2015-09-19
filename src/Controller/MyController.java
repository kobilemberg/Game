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
			public void doCommand(String[] args) 
			{
				try {
					String files = model.dir(args[0]);
					view.printFilesAndDirectories(files.toString());
				} catch (NullPointerException e) {
					System.out.println(args[0]+" is not a valid directory");
				}
				
				
			}
		});
		
		viewCommandMap.put("generate 3d maze",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				try {
					
					model.generateMazeWithName(args[0],args[1], args[2], args[3],args[4]);
				} catch (Exception e) {
					System.out.println("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
					e.printStackTrace();
				}
				 
			}
		});
		
		viewCommandMap.put("display",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				//args[1] = name
				try {
					view.printMazeToUser(model.getMazeWithName(args[0]),args[0]);
				} catch (NullPointerException e) {
					System.out.println("Exception: there is no maze named "+args[0]);
				}
			}
		});
		
		viewCommandMap.put("display cross section by",new Command() 
		{
			@Override
			public void doCommand(String[] args) 
			{
				//args[1] = Axe,args[2] = index ,args[3] = Name
				try {
					int[][] crossedArr = model.getCrossSectionByAxe(args[0],args[1],args[3]);
					view.printToUserCrossedArray(crossedArr,args[0],args[1],args[3]);
				} catch (Exception e) {
					System.out.println("Exception: problem with args");
				}
			}
		});
		
		viewCommandMap.put("save maze",new Command() 
		{
			@Override
			//args[1] = name, args[2] = filename
			public void doCommand(String[] args) {
				
				try {
					if(!(args[0].isEmpty()||args[1].isEmpty()))
					{
						model.saveCompressedMazeToFile(args[0],args[1]);
						view.tellTheUserTheMazeIsSaved(args[0],args[1]);
					}
					else
					{
						errorNoticeToViewr("problem with args");	
					}
				} catch (Exception e) {
					System.out.println("Exception: problem with args");
				}
			}
		});
		
		viewCommandMap.put("load maze",new Command() 
		{
			@Override
			//args[1] = fileName, args[2] = name
			public void doCommand(String[] args) {
				
				try {
					model.loadAndDeCompressedMazeToFile(args[0],args[1]);
					
					if(model.isLoaded(args[1]))
						view.tellTheUserTheMazeIsLoaded(args[0],args[1]);
				} catch (Exception e) {
					System.out.println("Exception: problem with args");
				}
			}
		});
		
		viewCommandMap.put("maze size",new Command() 
		{
			@Override
			//args[1] = mazeName
			public void doCommand(String[] args) {
				try {
					double sizeOfMazeInRam = model.getSizeOfMazeInRam(args[0]);
					if (sizeOfMazeInRam>0)
						view.tellTheUsersizeOfMazeInRam(args[0],sizeOfMazeInRam);
				}catch (Exception e) {
					System.out.println("Exception: problem with args");
				}
			}
		});
		
		viewCommandMap.put("file size",new Command() 
		{
			@Override
			//args[1] = Filename
			public void doCommand(String[] args) {
				try {
					double sizeOfFile = model.getSizeOfMazeInFile(args[0]);
					if (sizeOfFile>0)
						view.tellTheUsersizeOfMazeInFile(args[0],sizeOfFile);
				} catch (Exception e) {
					System.out.println("Exception: problem with args");
				}
					
			}
		});
		
		viewCommandMap.put("solve",new Command() 
		{
			@Override
			//args[1] = mazeName,args[2] = Algorithm
			public void doCommand(String[] args) {
					try {
						model.solveMaze(args[0],args[1]);
					} catch (Exception e) {
						System.out.println("Exception: problem with args");
					}
					
					
			}
		});
		
		viewCommandMap.put("display solution",new Command() 
		{
			@Override
			//args[1] = mazeName
			public void doCommand(String[] args) {
					try {
						
						Solution<Position> solution = model.getSolutionOfMaze(args[0]);
						if(solution!=null)
							view.printSolutionToUser(args[0],solution);
					} catch (NullPointerException e) {
						System.out.println("Exception: unexisted solution");
					}
					
					
			}
		});
		
		viewCommandMap.put("exit",new Command() 
		{
			@Override
			public void doCommand(String[] args) {model.exit();}
		});
		

		String cliMenu=new String();
		cliMenu+= "1:	dir <path>\n";
		cliMenu+= "2:	generate 3d maze <name> <generator> <other params>\n";
		cliMenu+= "3:	display <name>\n";
		cliMenu+= "4:	display cross section by {X,Y,Z} <index> for <name>\n";
		cliMenu+= "5:	save maze <name> <file name>\n";
		cliMenu+= "6:	load maze <file name> <name>\n";
		cliMenu+= "7:	maze size <name>\n";
		cliMenu+= "8:	file size <name>\n";
		cliMenu+= "9:	solve <name> <algorithm>\n";
		cliMenu+= "10:	display solution <name>\n";
		cliMenu+= "11:	exit\n";
		
		view.setCommands(viewCommandMap);
		view.setCommandsMenu(cliMenu);
	}
	
	public void setView(View view){this.view = view;}
	public void setModel(Model model){this.model = model;}

	@Override
	public void mazeIsReady(String name) {view.tellTheUserMazeIsReady(name);}

	@Override
	public void solutionIsReady(String mazeName) {view.tellTheUserSolutionIsReady(mazeName);}

	@Override
	public void errorNoticeToViewr(String s) {
		view.errorNoticeToUser(s);
		
	}

	

	
	
	

}
