package comp533;

public interface KeyValue<K, V> {
	K getKey();
	V getValue();
	void setValue(V newVal);
}
