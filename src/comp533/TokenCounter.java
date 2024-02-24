package comp533;

import comp533.mvc.ControllerImpl;
import comp533.mvc.ModelImpl;
import comp533.mvc.ViewImpl;
import comp533.mvc.Controller;
import comp533.mvc.Model;
import comp533.mvc.View;

public class TokenCounter {
	public static void main(String[] args) {
		Model model = new ModelImpl();
		Controller controller = new ControllerImpl(model);
		View view = new ViewImpl();
		model.addPropertyChangeListener(view);
		controller.initSlaveThreads();
		controller.getUserInput();
		// connect view as an observable of the model by calling addPropertyChangeListener() in the model, passing it to the view.
		// instantiate the controller, passing a reference of the model to it
	}

}

