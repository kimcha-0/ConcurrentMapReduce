package comp533.factory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class JoinerImpl extends AMapReduceTracer implements Joiner {
	private int numThreads;
	private int count;
	
	public JoinerImpl(int numThreads) {
		this.numThreads = numThreads;
		this.count = 0;
		traceJoinerCreated(this, numThreads);
	}

	public synchronized void finished() {
		count++;
		if (count == numThreads) {
			notify();
		}
		traceJoinerFinishedTask(this, numThreads, count);
	}
	
	public synchronized void join() throws InterruptedException {
		if (count < numThreads) {
			traceJoinerWaitStart(this, numThreads, count);
			while (count < numThreads) wait();
		}
		traceJoinerWaitEnd(this, numThreads, count);
		count = 0;
	}
	
	public String toString() {
		return JOINER;
	}

}
