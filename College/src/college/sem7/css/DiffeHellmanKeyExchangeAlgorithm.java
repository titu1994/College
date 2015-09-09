package college.sem7.css;

import java.util.Scanner;

public class DiffeHellmanKeyExchangeAlgorithm {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a prime for \"p\" : ");
		int p = sc.nextInt();
		
		if(!isPrime(p)) {
			System.out.println("p must be a prime");
			return;
		}
		
		int g = 5;
		System.out.println("Selected base \"g\" : " + g);
		
		//Secret Integer Key (A)
		double a = 6;
		double sendA = (Math.pow(g, a) % p);
		System.out.println("A Sends to B : " + sendA);
		
		// Secret Integer Key (B)
		double b = 15;
		double sendB = (Math.pow(g, b)) % p;
		System.out.println("B sends to A : " + sendB);
		
		// A Computes
		double aS = (Math.pow(sendB, a)) % p;
		
		// B Computes
		double bS = (Math.pow(sendA, b)) % p;
		
		
		System.out.println("Secret Key (A) : " + aS);
		System.out.println("Secret Key (B) : " + bS);
		
	}
	
	private static boolean isPrime(int pr) {
		int j = (int) Math.sqrt(pr);
		for (int i = 2; i <= j; i++) {
			if (pr % i == 0)
				return false;
		}
		return true;
	}

}
