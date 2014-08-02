package college.sem4;

import java.util.*;
import java.io.*;

public class FIFOScheduling {
	public static void main(String args[]) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		LinkedList<String> list = new LinkedList<String>();

		System.out.println("Input string of integers");
		String a = bb.readLine();
		String data[] = a.split(" ");

		int pageSize = 3;
		int pos = 0;

		for(int i = 0; i < data.length; i++){
			if(list.size() < pageSize && !list.contains(data[i])){
				list.add(data[i]);
			}
			else {
				if(!list.contains(data[i])) {
					list.remove(pos);
					list.add(pos, data[i]);
					pos = (pos+1)%3;
				}
			} 
			System.out.println(Arrays.toString(list.toArray()));
		}
		System.out.println("Final : "+Arrays.toString(list.toArray()));
	}
}


