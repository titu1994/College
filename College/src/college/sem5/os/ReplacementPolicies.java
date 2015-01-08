package college.sem5.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

public class ReplacementPolicies {
	public static void main(String args[]) throws IOException,NumberFormatException {
		int choice = -1;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter choice: 1 for FIFO, 2 for LRu, 3 for Optimal");
			choice = sc.nextInt();
			switch(choice) {
			case 1: {
				FIFOReplacement.replace();
				break;
			}
			case 2: {
				LRUReplacement.replace();
				break;
			}
			case 3: {
				OptimalReplacement.replace();
				break;
			}
			default: {
				choice = -1;
			}
			}

		} while(choice != -1);

	}	
}


class FIFOReplacement {
	public static void replace() throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		LinkedList<String> list = new LinkedList<String>();

		System.out.println("Enter Page Size");
		int pageSize = Integer.parseInt(bb.readLine());

		System.out.println("Input string of integers");
		String a = bb.readLine();
		String data[] = a.split(" ");

		int pos = 0;

		for(int i = 0; i < data.length; i++){
			if(list.size() < pageSize && !list.contains(data[i])){
				list.add(data[i]);
			}
			else {
				if(!list.contains(data[i])) {
					System.out.println("Page Fault : " + Arrays.toString(list.toArray()));
					list.remove(pos);
					list.add(pos, data[i]);
					pos = (pos+1)%pageSize;
				}
				else {
					System.out.println("Hit : " + Arrays.toString(list.toArray()));
				}
			} 
			System.out.println(Arrays.toString(list.toArray()));
		}
		System.out.println("Final : "+Arrays.toString(list.toArray()));
	}
}

class LRUReplacement  {

	public static void replace() throws NumberFormatException, IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter max LRU Size");
		int size = Integer.parseInt(bb.readLine());

		LRUCache<Integer, String> queue = new LRUCache<Integer, String>(size);
		System.out.println("Enter a string sequence of data");
		String data[] = bb.readLine().split(" ");

		for(int i = 0; i < data.length; i++){
			if(queue.containsKey(Integer.parseInt(data[i]))) {
				System.out.println("Hit : " + queue);
			}
			else {
				if(queue.size() >= size)
					System.out.println("Page Fault : " + queue);
				else 
					System.out.println(queue);
				queue.put(Integer.parseInt(data[i]), data[i]);
			}
		}
		System.out.println("Final : " + queue);
	}

	public static class LRUCache <K, V> extends LinkedHashMap < K, V > {
		
		private static final long serialVersionUID = 4764830195495351779L;
		private int capacity;

		public LRUCache(int capacity) { 
			super(capacity, .75f, true);
			this.capacity = capacity;
		}

		protected boolean removeEldestEntry(Entry<K,V> entry) {
			return (size() > this.capacity);
		}

		@Override
		public String toString() {
			return values().toString();
		} 
	}
}

class OptimalReplacement  {
	private static LinkedList<String> list ;
	private static String data[];
	private static int pageSize;

	public static void replace() throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		list = new LinkedList<String>();

		System.out.println("Enter Page Size");
		pageSize = Integer.parseInt(bb.readLine());
		int pos = 0;

		System.out.println("Input string of integers");
		String a = bb.readLine();
		data = a.split(" ");

		for(int i = 0; i < data.length; i++){
			if(list.size() < pageSize && !list.contains(data[i])){
				list.add(data[i]);
			}
			else {
				if(!list.contains(data[i])) {
					System.out.println("Page Fault : " + Arrays.toString(list.toArray()));
					pos = getPos(i%pageSize);
					list.remove(pos);
					list.add(pos, data[i]);
				}
				else {
					System.out.println("Hit : " + Arrays.toString(list.toArray()));
				}
			} 
			System.out.println(Arrays.toString(list.toArray()));
		}
		System.out.println("Final : "+Arrays.toString(list.toArray()));
	}

	public static int getPos(int currPos){
		int indices[] = new int[pageSize];

		if(currPos > pageSize) {
			for(int i = 0; i < pageSize; i++) {
				indices[i] = list.lastIndexOf(data[currPos - pageSize + i + 1]);
				if(indices[i] == -1) {
					return currPos - pageSize + i + 1;
				}
			}
			int lastIndex = -1;
			for(int i = 0; i < pageSize; i++) {
				if(lastIndex < indices[i]) 
					lastIndex = indices[i];
			}
			if(lastIndex != -1)
				return lastIndex;
		}
		else {
			return currPos;
		}
		return currPos;

	}	

}
