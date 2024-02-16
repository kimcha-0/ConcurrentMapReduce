package comp533;

import comp533.mvc.ControllerImpl;
import comp533.mvc.ModelImpl;
import comp533.mvc.ViewImpl;

public class TokenCounter {
	public static void main(String[] args) {
		ModelImpl model = new ModelImpl();
		ControllerImpl controller = new ControllerImpl(model);
		ViewImpl view = new ViewImpl();
		model.addPropertyChangeListener(view);
		controller.initSlaveThreads();
		controller.getUserInput();
		// connect view as an observable of the model by calling addPropertyChangeListener() in the model, passing it to the view.
		// instantiate the controller, passing a reference of the model to it
	}

}

