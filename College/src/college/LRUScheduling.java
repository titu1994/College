package college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LRUScheduling {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter max LRU Size");
		int size = Integer.parseInt(bb.readLine());

		LRUCache<Integer, String> queue = new LRUCache<Integer, String>(size);
		System.out.println("Enter a string sequence of data");
		String data[] = bb.readLine().split(" ");

		for(int i = 0; i < data.length; i++){
			queue.put(i, data[i]);
			System.out.println(queue);
		}
		System.out.println("Final : " + queue);
	}

	public static class LRUCache <K, V> extends LinkedHashMap < K, V > {
		
		private static final long serialVersionUID = -8468306640924288252L;
		private int capacity;

		public LRUCache(int capacity) { 
			super(capacity+1, 1.0f, true);
			this.capacity = capacity;
		}
		
		protected boolean removeEldestEntry(Entry<K,V> entry) {
			return (size() > this.capacity);
		}

		@Override
		public V put(K key, V value) {
			if(!values().contains(value))
				return super.put(key, value);
			else{
				get(key);
				return null;
			}
		}

		@Override
		public String toString() {
			return values().toString();
		} 
	}

}

