package college.sem5.cn;

import java.io.IOException;
import java.util.Scanner;

public class DistanceVectorRouting {

	public static void main(String args[]) throws IOException, NumberFormatException {
		Node rt[] = new Node[10];

		for(int i = 0; i < 10; i++) {
			rt[i] = new Node();
		}

		int costmat[][] = new int[20][20];
		int nodes, count = 0;

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the no. of nodes: ");
		nodes = sc.nextInt();
		System.out.println("Enter the cost matrix");

		for(int i = 0; i < nodes; i++) {
			for(int j = 0; j < nodes; j++) {
				costmat[i][j] = sc.nextInt();
				costmat[i][i] = 0;
				rt[i].distance[j] = costmat[i][j];
				rt[i].from[j] = j;
			}
		}

		do {
			count = 0;
			for(int i = 0; i < nodes; i++) {
				for(int j = 0; j < nodes; j++) {
					for(int k = 0; k < nodes; k++) {
						if(rt[i].distance[j] > costmat[i][k] + rt[k].distance[j]) {
							rt[i].distance[j] = rt[i].distance[k]+rt[k].distance[j];
							rt[i].from[j] = k;
							count++;
						}
					}
				}
			}

		} while(count != 0);

		for(int i = 0;i < nodes; i++) {
			System.out.println("For router " + (i+1));
			for(int j = 0; j < nodes; j++) {
				System.out.println("\tNode " + (j+1) +" via " + rt[i].from[j]+1 + " Distance " + rt[i].distance[j]);
			}
		}
	}
}

class Node {
	public int distance[] = new int[20];
	public int from[] = new int[20];
}
