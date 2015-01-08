package college.sem5.os;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ShortestRemainingTimeFirst {
	public static void main(String args[]) {
		int table1[][] = new int[5][6];
		int table2[][] = new int[5][6];
		int totalTime = 0;
		boolean con[] = new boolean[5];
		boolean fin[] = new boolean[5];
		boolean lowest[] = new boolean[5];
		Scanner sc = new Scanner(System.in);
		for (int a = 0; a < 5; a++) {

			System.out.print("Input ARRIVAL TIME and BURST TIME	for process #"
					+ (a + 1) + " (separated by space): ");
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			int b = 1;
			while (st.hasMoreTokens()) {
				table1[a][b] = Integer.parseInt("" + st.nextToken());
				table2[a][b] = table1[a][b];
				b++;
			}
			table1[a][0] = (a + 1);
			table2[a][0] = table1[a][0];
			totalTime += table1[a][2];
			con[a] = false;
			fin[a] = false;
			lowest[a] = false;
		}
		String str[] = new String[totalTime];
		for (int time = 0; time < totalTime; time++) {
			for (int d = 0; d < 5; d++) {
				if (table1[d][1] <= time && !fin[d]) {
					con[d] = true;
				}
			}
			int low = 0;
			for (int j = 0; j < 5; j++) {
				if (con[j]) {
					low = table1[j][2];
					break;
				}
			}
			for (int k = 0; k < 5; k++) {
				if (table1[k][2] < low && con[k]) {
					low = table1[k][2];
				}
			}
			for (int l = 0; l < 5; l++) {
				if (table1[l][2] == low) {
					lowest[l] = true;
					break;
				}
			}
			for (int f = 0; f < 5; f++) {
				if (lowest[f]) {
					table1[f][2] -= 1;
					if (table1[f][2] == 0) {
						fin[f] = true;
						str[time] = "" + (f + 1);
						break;
					}
				}
			}
			if (str[time] == null)
				str[time] = "0";
			for (int p = 0; p < 5; p++) {
				lowest[p] = false;
				con[p] = false;
			}
		}
		for (int n = 0; n < totalTime; n++) {
			if (str[n] != "0") {
				table2[Integer.parseInt(str[n]) - 1][5] = n + 1;
			}
		}
		for (int t = 0; t < 5; t++) {
			table2[t][4] = table2[t][5] - table2[t][1];
			table2[t][3] = table2[t][4] - table2[t][2];
		}
		double AWT = 0, ATT = 0;

		System.out.println("\nP\tAT\tBT\tWT\tTT\tET");
		for (int x = 0; x < 5; x++) {
			System.out.println("" + table2[x][0] + "\t" + table2[x][1] + "\t"
					+ table2[x][2] + "\t" + table2[x][3] + "\t" + table2[x][4]
					+ "\t" + table2[x][5] + "");
			AWT = AWT + table2[x][2];
			ATT = ATT + table2[x][3];
		}
		System.out.println("Average waiting time:" + AWT / 5);
		System.out.println("Average turnaround time:" + ATT / 5);
	}
}
