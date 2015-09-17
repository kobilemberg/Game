package Controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;

public interface Controller {
	HashMap<String, Maze3d> maze3dMap = new HashMap<String, Maze3d>();

	void mazeIsReady(String name);

	void solutionIsReady(String mazeName);

	

	
	
	//public Controller(Model model,View view);

}
