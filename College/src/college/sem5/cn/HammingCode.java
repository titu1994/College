package college.sem5.cn;

import java.util.*;

public class HammingCode {
	public static void main(String args[]) {
		Scanner read = new Scanner(System.in);

		int i, value;
		int a[] = new int[12];
		int r[] = new int[12];
		int v[] = new int[4];

		a[1] = a[2] = a[4] = a[8] = -1;

		value = 0;

		System.out.println("Enter 7 digits in binary");
		for (i = 11; i >= 1; i--)
			if (i != 8 && i != 4 && i != 2 && i != 1)
				a[i] = read.nextInt();

		int c = 0;

		for (int k = 3; k < 12; k += 2) {
			if (a[k] == 1)
				c++;
		}

		if (c % 2 == 0)
			a[1] = 0;
		else
			a[1] = 1;

		c = 0;

		if (a[3] == 1)
			c++;
		if (a[6] == 1)
			c++;
		if (a[7] == 1)
			c++;
		if (a[10] == 1)
			c++;
		if (a[11] == 1)
			c++;

		if (c % 2 == 0)
			a[2] = 0;
		else
			a[2] = 1;

		c = 0;

		if (a[5] == 1)
			c++;
		if (a[6] == 1)
			c++;
		if (a[7] == 1)
			c++;

		if (c % 2 == 0)
			a[4] = 0;
		else
			a[4] = 1;

		c = 0;

		if (a[9] == 1)
			c++;
		if (a[10] == 1)
			c++;
		if (a[11] == 1)
			c++;

		if (c % 2 == 0)
			a[8] = 0;
		else
			a[8] = 1;

		System.out.println("The Encoded Message is ");

		for (i = 11; i >= 1; i--)
			System.out.print(a[i]);
		System.out.println();

		System.out.println("Enter the 11 bit message to decode:");
		for (i = 11; i >= 1; i--)
			r[i] = read.nextInt();

		c = 0;

		for (int k = 1; k < 12; k += 2) {
			if (r[k] == 1)
				c++;
		}

		if (c % 2 == 0)
			v[0] = 0;
		else
			v[0] = 1;

		c = 0;

		if (r[2] == 1)
			c++;
		if (r[3] == 1)
			c++;
		if (r[6] == 1)
			c++;
		if (r[7] == 1)
			c++;
		if (r[10] == 1)
			c++;
		if (r[11] == 1)
			c++;

		if (c % 2 == 0)
			v[1] = 0;
		else
			v[1] = 1;

		c = 0;

		if (r[4] == 1)
			c++;
		if (r[5] == 1)
			c++;
		if (r[6] == 1)
			c++;
		if (r[7] == 1)
			c++;

		if (c % 2 == 0)
			v[2] = 0;
		else
			v[2] = 1;

		c = 0;

		if (r[8] == 1)
			c++;
		if (r[9] == 1)
			c++;
		if (r[10] == 1)
			c++;
		if (r[11] == 1)
			c++;

		if (c % 2 == 0)
			v[3] = 0;
		else
			v[3] = 1;

		value = v[0] + v[1] * 2 + v[2] * 4 + v[3] * 8;

		if (r[value] == 0)
			r[value] = 1;
		else
			r[value] = 0;

		System.out.println("The Decoded Message is ");
		for (i = 11; i >= 1; i--)
			System.out.print(r[i]);
	}
}