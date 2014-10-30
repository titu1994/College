package college.sem5.cn;

import java.io.IOException;
import java.util.Scanner;

public class DistanceVectorRouting {

	public static void main(String args[]) throws IOException, NumberFormatException {
		int nodes, count = 0;

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the no. of nodes: ");
		nodes = sc.nextInt();
		
		Node node[] = new Node[nodes];
		for(int i = 0; i < nodes; i++) {
			node[i] = new Node(nodes);
		}
		
		int costmat[][] = new int[nodes][nodes];
		
		System.out.println("Enter the cost matrix");

		for(int i = 0; i < nodes; i++) {
			for(int j = 0; j < nodes; j++) {
				costmat[i][j] = sc.nextInt();
				costmat[i][i] = 0;
				node[i].distance[j] = costmat[i][j];
				node[i].from[j] = j;
			}
		}

		do {
			count = 0;
			for(int i = 0; i < nodes; i++) {
				for(int j = 0; j < nodes; j++) {
					for(int k = 0; k < nodes; k++) {
						if(node[i].distance[j] > costmat[i][k] + node[k].distance[j]) {
							node[i].distance[j] = node[i].distance[k]+node[k].distance[j];
							node[i].from[j] = k;
							count++;
						}
					}
				}
			}

		} while(count != 0);

		for(int i = 0;i < nodes; i++) {
			System.out.println("For router " + (i+1));
			for(int j = 0; j < nodes; j++) {
				System.out.println("\tNode " + (j+1) +" via Node " + (node[i].from[j]+1) + " Distance " + node[i].distance[j]);
			}
		}
	}
}

class Node {
	public int distance[];
	public int from[];
	
	public Node(int n) {
		distance = new int[n];
		from = new int[n];
	}
}
