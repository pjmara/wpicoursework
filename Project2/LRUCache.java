import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	
	private static class Node<T,U> {
		private T key;
		private U value;
		private Node<T,U> next,prev;
		
	}
	
	private int capacity;
	private int missNum;
	private DataProvider<T,U> provider;
	private Map<T,Node<T,U>> keyNodeMap;
	private Node<T,U> head;
	private Node<T,U> tail;
	private int numElements;
	
	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		this.capacity = capacity;
		this.provider = provider;
		keyNodeMap =  new HashMap<T, Node<T,U>>(capacity);
		missNum = 0;
		numElements = 0;
	}

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		if (keyNodeMap.containsKey(key)) {
			moveToEnd(key);
			return keyNodeMap.get(key).value;
		} else {
			missNum ++;
			final U newValue = provider.get(key);
			addToCache(key, newValue);
			return newValue;
		}
	}
	
	/**
	 * Makes sure the value just called from cache is updated to the most recently used spot
	 * @param key key to a certain value
	 */
	private void moveToEnd (T key) {
		final Node<T,U> currentNode = keyNodeMap.get(key);
		if (!tail.equals(currentNode) && !head.equals(currentNode)) {
			currentNode.prev.next = currentNode.next;
			tail.next = currentNode;
			currentNode.prev = tail;
			tail = currentNode;
		} else if (head.equals(currentNode)) {
			head = currentNode.next;
			currentNode.next.prev = null;
			currentNode.prev = tail;
			tail.next = currentNode;
			tail = currentNode;
		}
	}
	
	/**
	 * Adds the given value to the LRUCache to the end
	 * @param key the key
	 * @param value the value associated with the key
	 */
	private void addToCache (T key, U value) {
		final Node<T,U> newNode = new Node<T,U>();
		newNode.key = key;
		newNode.value = value;
		newNode.prev = null;
		newNode.next = null;
		// When the cache is empty, both head and tail will be the Node passed in
		if (keyNodeMap.isEmpty()) {         
			keyNodeMap.put(key, newNode);
			head = newNode;
			tail = newNode;
			numElements ++;
			// When the cache is neither empty nor full, set up the next and tail
		} else if(numElements < capacity) {
			tail.next = newNode;
			newNode.prev = tail;
			keyNodeMap.put(key, newNode);
			tail = newNode;
			numElements ++;
			// When the cache is full, remove the head and set the head, tail, prev and next accordingly
		} else {
			head.next.prev = null;
			tail.next = newNode;
			newNode.prev = tail;
			keyNodeMap.remove(head.key);
			keyNodeMap.put(key, newNode);
			head = head.next;
			tail = tail.next;
		}
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return missNum;
	}
}
