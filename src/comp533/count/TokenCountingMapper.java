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
	public KeyValue<String, Integer> map(String input) {
		KeyValue<String, Integer> kv = new KeyValueImpl(input, 1);
		traceMap(input, kv);
		return kv;
	}
	
	@Override
	public String toString() {
		return TOKEN_COUNTING_MAPPER;
	}
}
