package comp533.mvc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import comp533.KeyValue;
import comp533.Slave;
import comp533.factory.Barrier;
import comp533.factory.Joiner;

import java.beans.PropertyChangeListener;

public interface Model {
	String getInputString();
	void setInputString(String newVal);
	Map<String, Integer> getMap();
	void addPropertyChangeListener(PropertyChangeListener listener);
	int getNumThreads();
	void setNumThreads(int numThreads);
	List<Slave> getThreads();
	List<List<KeyValue<String, Integer>>> getReductionQueueList();
	BlockingQueue<KeyValue<String, Integer>> getKeyValueQueue();
	Joiner getJoiner();
	Barrier getBarrier();
	void terminate();

	
}