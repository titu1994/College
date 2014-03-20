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
	
	public class LRUScheduling {
  
  public static void main(String[] args) throws NumberFormatException, IOException {
    BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter max LRU Size");
    int size = Integer.parseInt(bb.readLine());
    
    LRUCache<Integer, String> queue = new LRUCache<Integer, String>(size);
    System.out.println("Enter a string sequence of data");
    String data[] = bb.readLine().split(" ");
    
    for(int i = 0; i < data.length; i++){
      queue.insert(i, data[i]);
      System.out.println(queue);
    }
    System.out.println("Final : " + queue);
  }
  
  public class LRUCache <K, V> extends LinkedHashMap < K, V > {
    
    private int capacity;
    
    public LRUCache(int capacity) { 
      super(capacity+1, 1.0f, true);
      this.capacity = capacity;
    }
    
    protected boolean removeEldestEntry(Entry entry) {
      return (size() > this.capacity);
    } 
  }
  
}
}
