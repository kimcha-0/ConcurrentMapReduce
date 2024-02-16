package comp533.factory;

import java.util.Map;

import comp533.KeyValue;

import java.util.List;

public interface Reducer<K, V> {
	Map<K, V> reduce(List<KeyValue<K, V>> keyValues); 
}