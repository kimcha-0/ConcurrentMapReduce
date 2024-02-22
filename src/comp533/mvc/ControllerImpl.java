package comp533.mvc;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import java.util.Scanner;


public class ControllerImpl extends AMapReduceTracer implements Controller {
    private Scanner in;
    private String currentString;
    private Model model;

    public ControllerImpl(Model model) {
        this.in = new Scanner(System.in);
        this.model = model;
    }
    
    

    @Override
    public void getUserInput() {
		while (true) {
			traceNumbersPrompt();
			currentString = in.nextLine();
			if (currentString.equals("quit")) {
				model.terminate();
				break;
			}
			model.setInputString(currentString);
		}
		this.in.close();
		traceQuit();
    }
    
    @Override
    public void initSlaveThreads() {
    	traceThreadPrompt();
    	int numThreads = in.nextInt();
    	in.nextLine();
    	model.setNumThreads(numThreads);
    }
    
    @Override
    public void setUserInput(String val) {
    	this.currentString = val;
    }
    
    @Override
    public String toString() {
    	return CONTROLLER;
    }
}
