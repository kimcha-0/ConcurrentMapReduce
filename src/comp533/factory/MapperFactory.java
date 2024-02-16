package comp533.factory;

import comp533.count.TokenCountingMapper;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class MapperFactory extends AMapReduceTracer {
	private static Mapper<String, Integer> mapper = TokenCountingMapper.getInstance();
	
	public MapperFactory() {}
	
	public static Mapper<String, Integer> getMapper() {
		return mapper;
	}
	
	public static void setMapper(Mapper<String, Integer> newMapper) {
		mapper = newMapper;
		traceSingletonChange(MapperFactory.class, newMapper);
	}
}
