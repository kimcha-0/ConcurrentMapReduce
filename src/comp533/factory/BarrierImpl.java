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
	
	public synchronized void barrier() throws InterruptedException {
		barrierCount--;
		if (barrierCount > 0) {
    		traceBarrierWaitStart(this, numThreads, barrierCount);
    		wait();
    		traceBarrierWaitEnd(this, numThreads, barrierCount);
			
		} else {
    		traceBarrierReleaseAll(this, numThreads, barrierCount);
    		notifyAll();
    		barrierCount = numThreads;
		}
	}
	
	public String toString() {
		return BARRIER;
	}
}
