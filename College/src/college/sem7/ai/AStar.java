package college.sem7.ai;

import java.util.ArrayList;
import java.util.Scanner;

public class AStar {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<Integer> open = new ArrayList<>(), close = new ArrayList<>();
		int graph[][] = new int[20][20];
		int dist = 0;
		int n = sc.nextInt();
		int heur[] = new int[n];

		for (int j = 0; j < n; j++)
			heur[j] = sc.nextInt();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				graph[i][j] = sc.nextInt();
			}
		}

		open.add(1);
		System.out.println("Open: " + open);
		System.out.println("Close: " + close);
		System.out.println("Chosen node: " + 1);
		int curr = 0, small = 0;
		while (!close.contains(n)) {
			if (small == 0) {
				open.remove(new Integer(small + 1));
				close.add(small + 1);
			}

			for (int j = 0; j < n; j++)
				if (graph[curr][j] > 0) {
					small = j;
					break;
				}

			for (int i = 0; i < n; i++) {
				if (graph[curr][i] > 0) {
					if (!open.contains(i + 1))
						open.add(i + 1);

					if ((graph[curr][i] + heur[i]) < (graph[curr][small] + heur[small]))
						small = i;
				}
			}

			curr = small;
			System.out.println("Open : " + open);
			System.out.println("Close: " + close);
			System.out.println("Chosen node: " + (small + 1));

			if (!close.contains(small + 1)) {
				open.remove(new Integer(small + 1));
				close.add(small + 1);
			}

		}
		System.out.println("Finish");
	}

}

//Input
/*
12
12 10 16 15 12 7 11 15 12 4 1 0
0 2 0 0 1 0 0 0 0 0 0 0
0 0 1 0 0 3 0 0 0 0 0 0
0 0 0 2 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 1 0 0 0 0
0 0 0 0 0 0 0 0 1 0 0 0
0 0 0 0 8 0 1 0 0 4 0 0
0 0 3 0 0 0 0 0 0 0 10 0
0 0 0 0 0 0 16 0 0 0 0 15
0 0 0 0 0 0 0 0 0 8 0 0
0 0 0 0 0 0 0 0 0 0 3 0
0 0 0 0 0 0 0 0 0 0 0 1
0 0 0 0 0 0 0 0 0 0 0 0
*/

//Output
/*
Open: [1]
Close: []
Chosen node: 1
Open : [2, 5]
Close: [1]
Chosen node: 2
Open : [5, 3, 6]
Close: [1, 2]
Chosen node: 6
Open : [5, 3, 7, 10]
Close: [1, 2, 6]
Chosen node: 10
Open : [5, 3, 7, 11]
Close: [1, 2, 6, 10]
Chosen node: 11
Open : [5, 3, 7, 12]
Close: [1, 2, 6, 10, 11]
Chosen node: 12
Finish
*/
