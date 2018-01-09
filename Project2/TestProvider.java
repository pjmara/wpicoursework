import java.util.HashMap;

public class TestProvider<T,U> implements DataProvider<T, U> {
	private HashMap<T,U> storage;
	private int calledTime;
	
	public TestProvider () {
		storage = new HashMap<T,U>();
		calledTime = 0; // An accumulator to count the time get method is called for testing purpose.
	}
	
	/**
	 * Put the key and the associated value into storage
	 * @param key the key
	 * @param value the value associated with the key
	 */
	public void put (T key, U value) {
		storage.put(key, value);
	}
	
	@Override
	public U get (T key) {
		calledTime ++;
		return storage.get(key);
	}
	
	public int getCalledTime() {
		return calledTime;
	}

}
