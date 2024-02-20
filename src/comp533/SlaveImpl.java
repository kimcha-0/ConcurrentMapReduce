package comp533;

import java.util.ArrayList;
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
	public void run() {
		try {
			while (true) {
				KeyValue<String, Integer> kv = this.model.getKeyValueQueue().take();
				if (kv.getKey() == null) {
					Map<String, Integer> partialMap = reducer.reduce(slaveList);
					partitionMap(partialMap);
				} else {
					this.slaveList.add(kv);
				}
			}
		} catch (InterruptedException e) {
		}
	}
	
	private void partitionMap(Map<String, Integer> partialMap) {
		Partitioner<String, Integer> partitioner = PartitionerFactory.getPartitioner();
		for (var key : partialMap.entrySet()) {
			int partitionId = partitioner.getPartition(key.getKey(), key.getValue(), model.getNumThreads());
			synchronized(model.getReductionQueueList().get(partitionId)) {
				model.getReductionQueueList().get(partitionId).add(new KeyValueImpl(key.getKey(), key.getValue()));
			}
		}
		try {
			model.getBarrier().barrier();
			traceSplitAfterBarrier(this.slaveNum, model.getReductionQueueList().get(slaveNum));
			reduceThisPartition();
		} catch (InterruptedException e) {
		}
	}
	
	private synchronized void reduceThisPartition() {
		List<KeyValue<String, Integer>> reductionQueue = model.getReductionQueueList().get(slaveNum);
		Map<String, Integer> reducedPartition = reducer.reduce(reductionQueue);
		reductionQueue.clear();
		for (var kv : reducedPartition.entrySet()) {
			reductionQueue.add(new KeyValueImpl(kv.getKey(), kv.getValue()));
		}
	}
	
	public synchronized void notifySlave() {
		traceNotify();
		this.notify();
	}

	public String toString() {
		return SLAVE;
	}
	
	
}
