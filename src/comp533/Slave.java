package comp533;

import comp533.mvc.Model;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class Slave extends AMapReduceTracer implements SlaveInterface {
	private int slaveNum;
	private Model model;

	public Slave(int slaveNumber, Model model) {
		this.slaveNum = slaveNumber;
		this.model = model;
		
	}
	public void run() {
		
	}
	
	public synchronized void notifySlave() {
		traceNotify();
		this.notify();
	}

	public String toString() {
		return SLAVE;
	}
	
	
}
