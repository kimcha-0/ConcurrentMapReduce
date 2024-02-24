package comp533;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import comp533.factory.Partitioner;
import comp533.factory.PartitionerFactory;
import comp533.factory.Reducer;
import comp533.factory.ReducerFactory;
import comp533.mvc.Model;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class SlaveImpl extends AMapReduceTracer implements Slave {
	private int slaveNum;
	private Model model;
	private List<KeyValue<String, Integer>> slaveList;
	private Reducer<String, Integer> reducer;

	public SlaveImpl(int slaveNumber, Model model) {
		this.slaveNum = slaveNumber;
		this.model = model;
		this.slaveList = new ArrayList<>();
		this.reducer = ReducerFactory.getReducer();
		
	}

	@Override
	public void run() {
		try {
			while (true) {
				traceDequeueRequest(this.model.getKeyValueQueue());
				KeyValue<String, Integer> keyValue = this.model.getKeyValueQueue().take();
				traceDequeue(keyValue);
				if (keyValue.getKey() == null) {
					Map<String, Integer> partialMap = reducer.reduce(slaveList);
					slaveList.clear();
					partitionMap(partialMap);
					slaveWait();
				} else {
					this.slaveList.add(keyValue);
				}
			}
		} catch (InterruptedException e) {
		}
	}
	
	private synchronized void slaveWait() {
		try {
			traceWait();
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void partitionMap(Map<String, Integer> partialMap) {
		Partitioner<String, Integer> partitioner = PartitionerFactory.getPartitioner();
		List<List<KeyValue<String, Integer>>> queueList = model.getReductionQueueList();
		for (var key : partialMap.entrySet()) {
			int partitionId = partitioner.getPartition(key.getKey(), key.getValue(), model.getNumThreads());
			List<KeyValue<String, Integer>> currentPartition = queueList.get(partitionId);
			synchronized (currentPartition) {
				currentPartition.add(new KeyValueImpl(key.getKey(), key.getValue()));
			}
		}
		try {
			model.getBarrier().barrier();
			traceSplitAfterBarrier(this.slaveNum, queueList.get(slaveNum));
			reduceThisPartition();
		} catch (InterruptedException e) {
		}
	}
	
	private void reduceThisPartition() {
		List<KeyValue<String, Integer>> reductionQueue = model.getReductionQueueList().get(slaveNum);
		Map<String, Integer> reducedPartition;
		synchronized (reductionQueue) {
			reducedPartition = reducer.reduce(reductionQueue);
			reductionQueue.clear();
			for (var keyValue : reducedPartition.entrySet()) {
				reductionQueue.add(new KeyValueImpl(keyValue.getKey(), keyValue.getValue()));
			}
			this.model.getJoiner().finished();
		}
	}
	
	@Override
	public synchronized void notifySlave() {
		traceNotify();
		this.notify();
	}

	@Override
	public String toString() {
		return SLAVE;
	}
	
	
}
