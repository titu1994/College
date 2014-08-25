package college.sem5.os;
import java.io.*;

public class SchedulingFCFS {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int a[][] = new int[10][10];
		String ch = "0";
		int i, j, ct = 0, n = 0;
		double awt = 0.0, atat = 0.0;
		System.out.println("Enter process BT and AT: ");
		while (true) {
			ch = br.readLine();
			if (Integer.parseInt(ch) < 0)
				break;
			a[n][0] = Integer.parseInt(ch);
			ch = br.readLine();
			a[n][1] = Integer.parseInt(ch);
			n++;
		}
		for (i = 0; i < n; i++) {
			System.out.print(ct + "P" + (i + 1));
			ct += a[i][0];
			System.out.print(ct + " ");
		}
		ct = 0;
		System.out.println();
		for (j = 0; j < n; j++) {
			ct += a[j][0];
			if (j != n - 1)
				awt += ct - a[j + 1][1];
			atat += ct - a[j][1];
		}
		System.out.println("AWT: " + (awt / 3));
		System.out.println("ATAT: " + (atat / 3));
	}
}
