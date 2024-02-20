package comp533.factory;

import java.util.List;

import comp533.KeyValue;

public interface Mapper<K, V> {
	
	KeyValue<K, V> map(String string);
}
