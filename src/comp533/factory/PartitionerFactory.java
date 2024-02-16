package comp533.factory;

public class PartitionerFactory {
	private static Partitioner<String, Integer> partitioner = PartitionerImpl.getInstance();
	
	public static Partitioner<String, Integer> getPartitioner() {
		return partitioner;
	}
	
	public static void setPartitioner(Partitioner<String, Integer> newPartitioner) {
		partitioner = newPartitioner;
	}
}
