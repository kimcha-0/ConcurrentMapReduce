package comp533;

public class KeyValueImpl implements KeyValue<String, Integer> {
	private String key;
	private Integer val;
	
	public KeyValueImpl(String key, Integer val) {
		this.key = key;
		this.val = val;
	}
	@Override
	public String getKey() {
		return this.key;
	}
	
	@Override
	public Integer getValue() {
		return this.val;
	}
	
	@Override
	public void setValue(Integer newVal) {
		val = newVal;
	}
	
	@Override
	public String toString() {
		return "(" + key + ", " + val + ")";
	}
	

}
