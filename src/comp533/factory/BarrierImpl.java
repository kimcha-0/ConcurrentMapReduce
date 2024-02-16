package comp533.factory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class BarrierImpl extends AMapReduceTracer implements Barrier {
	private int barrierCount;
	private int numThreads;
	
	public BarrierImpl(int barrierCount) {
		this.barrierCount = barrierCount;		
		this.numThreads = barrierCount;
		traceBarrierCreated(this, numThreads);
	}
	
	public synchronized void barrier() {
		if (--barrierCount == 0) {
			this.notifyAll();
			barrierCount = numThreads;
			traceBarrierReleaseAll(this, numThreads, barrierCount);
		}
		try {
			traceBarrierWaitStart(this, numThreads, barrierCount);
			wait();
			traceBarrierWaitEnd(this, numThreads, barrierCount);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Thread Interrupted");
		}
	}
	
	public String toString() {
		return BARRIER;
	}
}
