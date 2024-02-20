package comp533.factory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class PartitionerImpl extends AMapReduceTracer implements Partitioner<String, Integer> {
	private static PartitionerImpl instance;

	public PartitionerImpl() {}
	
	public static PartitionerImpl getInstance() {
		if (instance == null) {
			instance = new PartitionerImpl();
		}
		return instance;
	}
	
	@Override
	public int getPartition(String key, Integer value, int numberOfPartitions) {
		// determines which partition list the keyvalue pair belong
		int partition;
		char firstChar = key.length() > 0 ? key.charAt(0) : null;
		if (!Character.isLetter(firstChar)) partition = 0; 
		else {
			firstChar = Character.toLowerCase(firstChar);
			int maxSize = (int)Math.ceil(('z' - 'a' + 1) / (double) numberOfPartitions); 
			partition = (int)Math.floor((firstChar - 'a' + 1) / ((double) maxSize));
		}
		tracePartitionAssigned(key, value, partition, numberOfPartitions);
		return partition;
	}
	
	public String toString() {
		return PARTITIONER;
	}
}
