package college;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RestoringDivision {
	private static int msl, qsl;
	static String ms;
	static String qs;
	
	private static int M[], MinM[], Q[], A[], C;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter M and Q");
		int q = sc.nextInt();
		int m = sc.nextInt();
		
		initialize(m, q);
		restoringDivision();
		sc.close();
	}
	
	private static void initialize(int m,int q) {
		ms = Integer.toBinaryString(m);
		qs = Integer.toBinaryString(q);
		
		msl = ms.length();
		qsl = qs.length();
		
		M = new int[qsl+1];
		Q = new int[qsl];
		
		for(int i = 0; i < msl; i++)
			M[qsl-i] = ms.charAt(msl-1-i) == '0' ? 0 : 1;
		
		for(int i = 0; i < qsl; i++)
			Q[i] = qs.charAt(i)== '0' ? 0 : 1;
		
		System.out.println(Arrays.toString(M));
		System.out.println(Arrays.toString(Q));
		
		A = new int[qsl];
		twosComplement();
	}
	
	private static void twosComplement() {
		MinM = new int[M.length];
		
		for(int i = 0; i < M.length; i++){
			MinM[i] = (M[i] + 1)%2;
		}
		System.out.println("MIN : " + Arrays.toString(MinM));
		
		int oneArr[] = new int[MinM.length];
		oneArr[MinM.length-1] = 1;
		add(MinM, oneArr);
	}
	
	private static void add(int a[],int b[]){
		int cin = 0, s = 0, x, y;
		int aLen = a.length;
		int len = aLen;
		
		for(int i = 0; i < len-1 ; i++){
			x = a[len-1-i];
			y = b[len-1-i];
			
			s = x ^ y ^ cin;
			cin = (x & cin) | (y & cin) | (x & y);
			
			a[i] = s;
		}
		a[len-1] = cin ^ s;
		int t;
		for(int i = 0; i < a.length/2; i++){
			t = a[i];
			a[i] = a[a.length-1-i];
			a[a.length-1-i] = t;
		}
	}
	
	private static void shiftLeft(){
		for(int i = 0; i <A.length-1; i++)
			A[i] = A[i+1];

		A[A.length-1] = Q[0];
		
		for(int i = 0; i < Q.length-1; i++)
			Q[i] = Q[i+1];
		
		C = A[0];
		Q[Q.length-1] = (C+1)%2;
	}
	
	private static void subM(){
		add(A, MinM);
		
		if(C == 1)
			addM();
	}
	
	private static void addM(){
		add(A,M);
	}
	
	private static void restoringDivision(){
		int len = Q.length;
		
		for(int i = 0; i < len; i++){
			shiftLeft();
			subM();
		}
		
		System.out.print("Qoutient : ");
		for(int i = 0; i < A.length; i++)
			System.out.print(A[i]);
		
		System.out.println();
		
		System.out.print("Remainder : ");
		for(int i = 0; i < Q.length; i++)
			System.out.print(Q[i]);
		
		System.out.println();
		
		System.out.print("MINM : ");
		System.out.println(Arrays.toString(MinM));
	}
}
