package comp533.mvc;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import java.util.Map;

import comp533.KeyValue;
import comp533.Slave;
import comp533.factory.Mapper;
import comp533.factory.MapperFactory;
import comp533.factory.Reducer;
import comp533.factory.ReducerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModelImpl extends AMapReduceTracer implements Model {
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	// editable
	private String inputBuffer;
	// readonly
	private Map<String, Integer> _map;
	private Mapper<String, Integer> mapper;
	private Reducer<String, Integer> reducer;
	private int numThreads;
	private List<Thread> threads;
	
    public ModelImpl() {
        this._map = new HashMap<String, Integer>();
        this.mapper = MapperFactory.getMapper();
        this.reducer = ReducerFactory.getReducer();
        this.threads = new ArrayList<Thread>();
    }

    @Override
	public String getInputString() {
		return inputBuffer;
	}
	
    @Override
	public void setInputString(String newVal) {
		// fire PropertyChangeEvent
		String oldVal = inputBuffer;
		inputBuffer = newVal;
		propertyChangeSupport.firePropertyChange("InputString", oldVal, newVal);
		countTokens();
		propertyChangeSupport.firePropertyChange("Result", null, _map);
	}

    private void countTokens() {
    	_map.clear();
        String[] strings = this.inputBuffer.split(" ");
        List<String> tokens = Arrays.asList(strings);
        List<KeyValue<String, Integer>> keyValuePairs = mapper.map(tokens);
        this._map = reducer.reduce(keyValuePairs);
    }
    
    @Override
    public List<Thread> getThreads() {
    	return this.threads;
    }
    
    @Override
    public int getNumThreads() {
    	return this.numThreads;
    }
    
    @Override
    public void setNumThreads(int num) {
    	int oldNum = this.numThreads;
    	propertyChangeSupport.firePropertyChange("NumThreads", oldNum, num);
    	this.numThreads = num;

    	for (int i = 0; i < num; i++) {
    		Thread slave = new Thread(new Slave(i, this));
    		propertyChangeSupport.firePropertyChange("Threads", null, slave);
    		this.threads.add(slave);
    	}
    }

    @Override
	public Map<String, Integer> getMap() {
		return _map;
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
    public String toString() {
        return MODEL;
    }
}

