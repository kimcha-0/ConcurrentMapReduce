package comp533.mvc;

import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeListener;

public interface Model {
	String getInputString();
	void setInputString(String newVal);
	Map<String, Integer> getMap();
	void addPropertyChangeListener(PropertyChangeListener listener);
	int getNumThreads();
	void setNumThreads(int numThreads);
	List<Thread> getThreads();
}