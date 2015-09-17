package boot;

import Controller.MyController;
import Model.MyModel;
import View.MyView;

public class Run {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		MyView view = new MyView();
		MyModel model = new MyModel();
		MyController controller = new MyController(view, model);
		model.setController(controller);
		view.setController(controller);
		view.start();

	}

}
