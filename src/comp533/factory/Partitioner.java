package comp533.factory;

public interface Partitioner<K, V> {
	public int getPartition(K key, V value, int numberOfPartitions);
}
