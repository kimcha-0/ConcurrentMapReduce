package comp533.factory;

import java.util.List;

import comp533.KeyValue;

public interface Mapper<K, V> {
	
	List<KeyValue<K, V>> map(List<String> tokens);
}
