package comp533.factory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class ReducerFactory extends AMapReduceTracer {
	private static Reducer<String, Integer> reducer = new TokenCountingReducer();
	
	public ReducerFactory() {}
	
	public static Reducer<String, Integer> getReducer() {
		return reducer;
	}
	
	public static void setReducer(Reducer newReducer) {
		reducer = newReducer;
		traceSingletonChange(TokenCountingReducer.class, newReducer);
	}

}
