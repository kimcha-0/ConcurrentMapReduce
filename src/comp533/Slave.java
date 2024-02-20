package comp533;

public interface Slave extends Runnable {
	void run();
	void notifySlave();
}
