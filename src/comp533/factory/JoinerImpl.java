package comp533.factory;

public class JoinerImpl implements Joiner {
	private int numThreads;
	private int count;
	
	public JoinerImpl(int numThreads) {
		this.numThreads = numThreads;
		this.count = 0;
	}
	
	public synchronized void finished() {
		count++;
	}
	
	public synchronized void join() {
		while (count < numThreads) {
			try {
				wait();
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("Thread Interrupted");
			}
		}
	}

}
