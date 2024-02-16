package comp533.factory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import comp533.KeyValue;

public class TokenCountingReducer extends AMapReduceTracer implements Reducer<String, Integer> {

	public TokenCountingReducer() {}
	
	@Override
	public Map<String, Integer> reduce(List<KeyValue<String, Integer>> keyValues) {
		Map<String, Integer> map = new HashMap<>();
		Iterator<KeyValue<String, Integer>> iterator = keyValues.iterator();
		String key;
		Integer val;

		while (iterator.hasNext()) {
			KeyValue<String, Integer> pair = iterator.next();
			key = pair.getKey();
			val = pair.getValue();
			if (map.containsKey(key)) {
				map.put(key, map.get(key) + val);
			} else map.put(key, val);
			
		}
		traceReduce(keyValues, map);
		return map;
	}
	
	@Override
	public String toString() {
		return REDUCER;
	}
}
