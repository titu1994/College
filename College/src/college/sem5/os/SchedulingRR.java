package college.sem5.os;

import java.io.*;

public class SchedulingRR {
	public static void main(String args[]) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int i, j, k, timeQuantum, sum = 0;
		System.out.println("Enter number of process:");
		int n = Integer.parseInt(in.readLine());
		int bt[] = new int[n];
		int wt[] = new int[n];
		int tat[] = new int[n];
		int a[] = new int[n];
		System.out.println("Enter burst Time:");
		for (i = 0; i < n; i++) {
			System.out.println("Enter burst Time for   " + (i + 1));
			bt[i] = Integer.parseInt(in.readLine());
		}
		System.out.println("Enter Time slice:");
		timeQuantum = Integer.parseInt(in.readLine());
		for (i = 0; i < n; i++)
			a[i] = bt[i];
		for (i = 0; i < n; i++)
			wt[i] = 0;
		do {
			for (i = 0; i < n; i++) {
				if (bt[i] > timeQuantum) {
					bt[i] -= timeQuantum;
					for (j = 0; j < n; j++) {
						if ((j != i) && (bt[j] != 0))
							wt[j] += timeQuantum;
					}
				} else {
					for (j = 0; j < n; j++) {
						if ((j != i) && (bt[j] != 0))
							wt[j] += bt[i];
					}
					bt[i] = 0;
				}
			}
			sum = 0;
			for (k = 0; k < n; k++)
				sum = sum + bt[k];
		} while (sum != 0);
		for (i = 0; i < n; i++)
			tat[i] = wt[i] + a[i];
		System.out.println("process\t\tBT\tWT\tTAT");
		for (i = 0; i < n; i++) {
			System.out.println("process" + (i + 1) + "\t" + a[i] + "\t" + wt[i]
					+ "\t" + tat[i]);
		}
		float avg_wt = 0;
		float avg_tat = 0;
		for (j = 0; j < n; j++) {
			avg_wt += wt[j];
		}
		for (j = 0; j < n; j++) {
			avg_tat += tat[j];
		}
		System.out.println("average wating time " + (avg_wt / n)
				+ "\nAverage turn around time" + (avg_tat / n));
	}
}
