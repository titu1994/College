package college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUScheduling {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter max LRU Size");
		int size = Integer.parseInt(bb.readLine());
		
		LRUCache<Integer, String> queue = new LRUCache<Integer, String>(size);
		System.out.println("Enter a string sequence of data");
		String data[] = bb.readLine().split(" ");
		
		for(int i = 0; i < data.length; i++){
			if(!queue.equals(i))
				queue.insert(i, data[i]);
			System.out.println(queue);
		}
		System.out.println("Final : " + queue);
	}
	
	private static class LRUCache<Key, Value>{
		private int maxSize;
		private ConcurrentHashMap<Key, Value> map;
		private ConcurrentLinkedQueue<Key> queue;
		
		private Key oldestKey = null;
		
		public LRUCache(int maxSize) {
			this.maxSize = maxSize;
			map = new ConcurrentHashMap<Key, Value>(maxSize);
			queue = new ConcurrentLinkedQueue<Key>();
		}
		
		public void insert(Key key, Value value) {
			if(map.containsKey(key)){
				queue.remove(key);
			}
			
			while(queue.size() >= maxSize) {
				oldestKey = queue.poll();
				if(oldestKey != null)
					map.remove(oldestKey);
			}
			
			queue.add(key);
			map.put(key, value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(map.containsKey(obj))
				return true;
			return false;
		}

		public Value retrive(Key key) {
			return map.get(key);
		}

		@Override
		public String toString() {
			return map.values().toString();
		}
	}

}
