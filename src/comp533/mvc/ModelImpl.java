package comp533.mvc;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import comp533.KeyValue;
import comp533.KeyValueImpl;
import comp533.Slave;
import comp533.SlaveImpl;
import comp533.factory.Barrier;
import comp533.factory.BarrierImpl;
import comp533.factory.Joiner;
import comp533.factory.JoinerImpl;
import comp533.factory.Mapper;
import comp533.factory.MapperFactory;
import comp533.factory.Reducer;
import comp533.factory.ReducerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ModelImpl extends AMapReduceTracer implements Model {
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	// editable
	private String inputBuffer;
	// readonly
	private Map<String, Integer> result;
	private Map<String, Integer> _map;
	private Mapper<String, Integer> mapper;
	private Reducer<String, Integer> reducer;
	private int numThreads;
	private List<Slave> slaves;
	private List<Thread> threads;
	private BlockingQueue<KeyValue<String, Integer>> keyValueQueue;
	private List<LinkedList<KeyValue<String, Integer>>> reductionQueueList;
	private Joiner joiner;
	private Barrier barrier;
	
    public ModelImpl() {
        this._map = new HashMap<String, Integer>();
        this.mapper = MapperFactory.getMapper();
        this.reducer = ReducerFactory.getReducer();
        this.slaves = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.keyValueQueue = new ArrayBlockingQueue<>(BUFFER_SIZE);
        this.reductionQueueList = new ArrayList<>();
        this.result = new HashMap<>();
        
    }

    @Override
    public BlockingQueue<KeyValue<String, Integer>> getKeyValueQueue() {
    	return this.keyValueQueue;
    }
    
    @Override
    public List<LinkedList<KeyValue<String, Integer>>> getReductionQueueList() {
    	return this.reductionQueueList;
    }
    
    @Override
    public Joiner getJoiner() {
    	return this.joiner;
    }
    
    @Override
    public Barrier getBarrier() {
    	return this.barrier;
    }
    
    @Override
	public String getInputString() {
		return inputBuffer;
	}
    
	
    @Override
	public synchronized void setInputString(String newVal) {
    	// clear and initialize data structures
		// fire PropertyChangeEvent
    	this.barrier = new BarrierImpl(numThreads);
    	this.joiner = new JoinerImpl(numThreads);
    	for (LinkedList<KeyValue<String, Integer>> q : this.reductionQueueList) {
    		q.clear();
    	}
    	// Possible unblocking of slave threads
    	for (Slave t : this.slaves) {
    		t.notifySlave();
    	}
		String oldVal = inputBuffer;
		inputBuffer = newVal;
		Map<String, Integer> oldResult = result;
		propertyChangeSupport.firePropertyChange("InputString", oldVal, newVal);
		tokenize();
		propertyChangeSupport.firePropertyChange("Result", null, result);
	}

    private void tokenize() {
    	_map.clear();
        String[] strings = this.inputBuffer.split(" ");
        List<String> tokens = Arrays.asList(strings);
        Iterator<String> iterator = tokens.iterator();
        while (iterator.hasNext()) {
        	try {
        		loadToken(mapper.map(iterator.next()));
        	} catch (InterruptedException e) {
        		
        	}
        }
        for (int i = 0; i < this.numThreads; i++) {
        	KeyValue<String, Integer> endToken = new KeyValueImpl(null, 0);
                try {
                	traceEnqueueRequest(endToken);
                	this.keyValueQueue.put(endToken);
                	traceEnqueue(this.keyValueQueue);
                } catch (InterruptedException e) {
                	
                }
        }
        try {
        	this.joiner.join();
        	merge();
        } catch (InterruptedException e) {
        	
        }
        
    }
    
    private void merge() {
    	Map<String, Integer> temp = result;
    	for (List<KeyValue<String, Integer>> queue : reductionQueueList) {
    		for (KeyValue<String, Integer> kv : queue) {
    			result.put(kv.getKey(), kv.getValue());
    		}
    		traceAddedToMap(temp, queue);
    	}
    }
    
    private void loadToken(KeyValue<String, Integer> token) throws InterruptedException {
    	traceEnqueueRequest(token);
    	this.keyValueQueue.put(token);
    	traceEnqueue(this.keyValueQueue);
    }
    
    @Override
    public void terminate() {
    	for (Thread t : threads) {
    		t.interrupt();
    	}
    }
    
    @Override
    public List<Slave> getThreads() {
    	return this.slaves;
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
    		Slave slave = new SlaveImpl(i, this);
    		Thread thread = new Thread(slave, slave.toString() + i);
    		thread.start();
    		threads.add(thread);
    		this.slaves.add(slave);
    		LinkedList<KeyValue<String, Integer>> reductionQueue = new LinkedList<>();
    		this.reductionQueueList.add(reductionQueue);
    	}
   		propertyChangeSupport.firePropertyChange("Threads", null, this.threads);
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

