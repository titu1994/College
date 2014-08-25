package college.sem5.os;

import java.util.*;

public class Priority {
	public int bt;
	public int at;
	public int pt;
	public int pr;

	Priority() {
		bt = 0;
		at = 0;
		pt = 0;
	}

	Priority(int x, int y, int z) {
		bt = x;
		at = y;
		pt = z;
	}

	void t(Priority p2) {
		pr = p2.at - at;
	}
}

class PEP {
	static int sum(Priority p[], int n) {
		int s = 0;
		for (int i = 0; i < n; ++i)
			s += p[i].bt;
		if (s == 0)
			return 1;
		else
			return 0;
	}

	public static void main(String args[]) {
		Priority p[];
		Scanner scr = new Scanner(System.in);
		System.out.println("Enter the number of processes");
		int n = scr.nextInt();
		p = new Priority[n];
		int i = 0, j = 0;
		int bt, at, pt;
		for (i = 0; i < n; ++i) {
			System.out
					.println("Enter the burst time and arrival time & priority of a process");
			bt = scr.nextInt();
			at = scr.nextInt();
			pt = scr.nextInt();
			p[i] = new Priority(bt, at, pt);
		}
		Priority temp;
		// Sorting processes in increasing order of arrival time
		for (i = 0; i <= n - 2; ++i) {
			for (j = i; j <= n - 1; ++j) {
				if (p[i].at > p[j].at) {
					temp = p[i];
					p[i] = p[j];
					p[j] = temp;
				}
			}
		}
		int tt[] = new int[n];
		int wt[] = new int[n];
		int b[] = new int[n];
		for (i = 0; i < n; ++i)
			b[i] = p[i].bt;
		for (i = 0; i < n; ++i) {
			System.out.println("Process " + (i + 1) + " Burst time= " + p[i].bt
					+ " priority= " + p[i].pt + " Arrival time= " + p[i].at);
		}
		int time = 0;
		int av;
		for (int k = 0; k < n - 1; ++k) {
			p[k].t(p[k + 1]);
		}
		System.out.println();
		int t1;
		do {
			t1 = 0;
			av = 0;
			for (i = 0; i < n; ++i)
				if (p[i].at <= time)
					++av;
			int hp;
			int max = 0;
			if (p[0].bt == 0) {
				for (i = 1; i < n; ++i)
					if (p[i].bt != 0) {
						max = i;
						break;
					}
			}
			for (i = 0; i < av; ++i) {
				if ((p[max].pt > p[i].pt) && p[i].bt > 0)
					max = i;
			}
			hp = max;
			p[hp].bt -= 1;
			++time;
			if (p[hp].bt == 0)
				tt[hp] = time;
			++t1;
		} while (sum(p, n) != 1);
		double att = 0, awt = 0;
		double s1 = 0, s2 = 0;
		for (i = 0; i < n; ++i)
			tt[i] = tt[i] - p[i].at;
		System.out.println();
		for (int h = 0; h < n; ++h)
			System.out.println("Turn around time of process " + (h + 1) + " = "
					+ tt[h] + "  wait time = " + (tt[h] - b[h]));

		for (i = 0; i < n; ++i)
			wt[i] = tt[i] - b[i];
		for (i = 0; i < n; ++i) {
			s1 += tt[i];
			s2 += wt[i];
		}
		att = s1 / n;
		awt = s2 / n;
		System.out.println("Average Turn around time= " + att
				+ " Average wait time= " + awt);
	}
}

