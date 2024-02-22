package comp533.count;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import comp533.KeyValue;
import comp533.KeyValueImpl;
import comp533.factory.Mapper;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class TokenCountingMapper extends AMapReduceTracer implements Mapper<String, Integer> {
	private static TokenCountingMapper instance;
	
	public TokenCountingMapper() {}
	
	public static Mapper<String, Integer> getInstance() {
		if (instance == null) {
			instance = new TokenCountingMapper();
		}
		return instance;
	}
	
	@Override
	public List<KeyValue<String, Integer>> map(List<String> input) {
		List<KeyValue<String, Integer>> keys = new ArrayList<>();
		Iterator<String> iterator = input.iterator();

		while (iterator.hasNext()) {
			keys.add(new KeyValueImpl(iterator.next(), 1));
		}
		traceMap(input, keys);
		return keys;
	}
	
	@Override
	public String toString() {
		return TOKEN_COUNTING_MAPPER;
	}
}
