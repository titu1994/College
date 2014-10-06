package college.sem5.os;

import java.io.IOException;
import java.util.Scanner;

public class BankersAlgorithm {

	public static void main(String args[]) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number of processes :");
		int n = sc.nextInt();

		System.out.print("Enter the number of resources : ");
		int m = sc.nextInt();

		int alloc[][] = new int[n][m];
		int max[][] = new int[n][m];
		int need[][] = new int[n][m];
		int avail[] = new int[m];

		System.out.println("Enter the Allocated Matrix elements");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				alloc[i][j] = sc.nextInt();
			}
		}

		System.out.println("Enter the Max Matrix elements");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				max[i][j] = sc.nextInt();
			}
		}

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				need[i][j] = max[i][j] - alloc[i][j];
			}
		}
		System.out.println("Need Matrix : ");

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				System.out.print(need[i][j] + "\t");
			}
			System.out.println();
		}

		System.out.println("Enter the available resources");
		for(int i = 0; i < m; i++) {
			avail[i] = sc.nextInt();
		}

		int work[] = new int[m];
		System.arraycopy(avail, 0, work, 0, m);

		boolean finish[] = new boolean[n];
		int safeSequence[] = new int[n];
		int index = -1;
		int t = 0;

		while((index = isDeadlocked(work, need, finish)) > -1) {
			safeSequence[t++] = index;
			finish[index] = true;
			for(int i = 0; i < m; i++) {
				work[i] += alloc[index][i];
			}
		}

		boolean notInDeadlock = true;
		for(int i = 0; i < n; i++) {
			notInDeadlock = notInDeadlock & finish[i];
		}

		if(notInDeadlock) {
			for(int i = 0; i < n; i++) {
				System.out.print(safeSequence[i] + "\t");
			}
			System.out.println();
		}
		else {
			System.out.println("System is in deadock.");
		}

	}

	private static int isDeadlocked(int work[], int need[][], boolean finish[]) {
		boolean flag = true;
		for(int i = 0; i < need.length; i++) {
			for(int j = 0; j < need[i].length; j++) {
				if(need[i][j] <= work[j] && !finish[i]) {
					flag = flag & true;
				}
				else 
					flag = false;
			}
			if(flag)
				return i;
			else {
				flag = true;
			}
		}
		return -1;
	}

}
