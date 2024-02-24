package comp533;

import comp533.factory.Mapper;
import comp533.factory.MapperFactory;
import comp533.mvc.Controller;
import comp533.mvc.ControllerImpl;
import comp533.mvc.Model;
import comp533.mvc.ModelImpl;
import comp533.mvc.View;
import comp533.mvc.ViewImpl;
import comp533.sum.IntSummingMapper;

public class IntegerSummer {
	
public static void main(final String[] args) {
    final Mapper<String, Integer> mapper = new IntSummingMapper();
    MapperFactory.setMapper(mapper);
	ModelImpl model = new ModelImpl();
	ControllerImpl controller = new ControllerImpl(model);
	ViewImpl view = new ViewImpl();
	model.addPropertyChangeListener(view);
	controller.initSlaveThreads();
	controller.getUserInput();
	}
}
