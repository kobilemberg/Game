package boot; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Controller.MyController;
import Model.MyModel;
import View.MyView;

public class Run {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		//MyView view = new MyView(System.in, System.out);
		MyModel model = new MyModel();
		MyController controller = new MyController(view, model);
		model.setController(controller);
		view.setController(controller);
		view.start();

	}

}
