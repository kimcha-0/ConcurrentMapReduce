package comp533.sum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comp533.KeyValue;
import comp533.KeyValueImpl;
import comp533.factory.Mapper;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class IntSummingMapper extends AMapReduceTracer implements Mapper<String, Integer> {
	
	private static IntSummingMapper instance;
	
	public IntSummingMapper() {}
	
	public static IntSummingMapper getInstance() {
		if (instance == null) {
			instance = new IntSummingMapper();
		}
		return instance;
	}
	
	@Override
	public List<KeyValue<String, Integer>> map(List<String> strings) {
		List<KeyValue<String, Integer>> keys = new ArrayList<>();
		Iterator<String> iterator = strings.iterator();

		while (iterator.hasNext()) {
			KeyValue<String, Integer> pair = new KeyValueImpl("ResultKey", Integer.parseInt(iterator.next()));
//			System.out.println(pair);
			keys.add(pair);
		}
		traceMap(strings, keys);
		return keys;
	}
	
	@Override
	public String toString() {
		return INT_SUMMING_MAPPER;
	}

}
