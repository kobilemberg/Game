package Controller;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyController </h1>
 * MyController class implements Controller interface, 
 * class goal is to act as MVC controller
 */
import java.util.HashMap;

import Model.Model;
import View.View;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyController implements Controller {
	
	View view;
	Model model;

//Constructors
/**
* Instantiates a new  my own controller with given view and model.
* @param view View represent the view layer
* @param model Model represent the model layer
* @return new MyController as instance
* */
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
		cliMenu+= "2:	generate 3d maze <Maze name> <MyMaze3dGenerator\\SimpleMaze3dGenerator> <X> <Y> <Z>\n";
		cliMenu+= "3:	display <Maze name>\n";
		cliMenu+= "4:	display cross section by {X,Y,Z} <index> for <Maze name>\n";
		cliMenu+= "5:	save maze <Maze name> <File name>\n";
		cliMenu+= "6:	load maze <File name> <Maze name>\n";
		cliMenu+= "7:	maze size <Maze name>\n";
		cliMenu+= "8:	file size <File name>\n";
		cliMenu+= "9:	solve <Maze name> <A*\\BFS>\n";
		cliMenu+= "10:	display solution <Maze name>\n";
		cliMenu+= "11:	exit\n";
		
		view.setCommands(viewCommandMap);
		view.setCommandsMenu(cliMenu);
	}

//Getters and setters
	
	/**
	 * This method will set controller view layer
	 * @param view View represent the view layer
	 */
	public void setView(View view){this.view = view;}
	/**
	* This method will set controller model layer
	* @param model Model represent the model layer
	*/
	public void setModel(Model model){this.model = model;}
	/**
	* This method will return the controller view layer
	* @return View instance represent the view layer of the controller
	*/
	public View getView(){return view;}
	/**
	* This method will return the controller model layer
	* @return Model instance represent the Model layer of the controller
	*/
	public Model getModel(){return model;}
	
//Overrides and functionality
	@Override
	public void mazeIsReady(String name) {view.tellTheUserMazeIsReady(name);}

	@Override
	public void solutionIsReady(String mazeName) {view.tellTheUserSolutionIsReady(mazeName);}

	@Override
	public void errorNoticeToViewr(String s) {view.errorNoticeToUser(s);}

	

	
	
	

}
