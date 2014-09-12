package college.sem5.os;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class DiningPhilosopherProblem {

	public static void main(String args[]) throws IOException, NumberFormatException {
		Scanner sc = new Scanner(System.in);
		final Philosopher pholosoper = new Philosopher();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				pholosoper.eat(0);
				pholosoper.eat(1);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				pholosoper.eat(2);
				pholosoper.eat(3);
				pholosoper.eat(4);
			}
		});
		
		
		
		t1.start();
		t2.start();
	}

	public static void semaphoreWait(AtomicInteger semaphore) {
		while(semaphore.intValue() <= 0);

		semaphore.set(semaphore.get() - 1);
	}

	public static void semaphoreNotify(AtomicInteger semaphore) {
		semaphore.set(semaphore.get() + 1);
	}
}

class Philosopher {
	public static AtomicInteger[] forks = new AtomicInteger[5];

	public Philosopher() {
		for(int i = 0; i < 5; i++) {
			forks[i] = new AtomicInteger(1);
		}
	}

	public void eat(int philosopher) {
		for(int i = 0; i < 20; i++) {
			

			DiningPhilosopherProblem.semaphoreWait(forks[i % 5]);
			DiningPhilosopherProblem.semaphoreWait(forks[(i + 1) % 5]);
			System.out.println("Philosopher " + i + " is thinking.");
			System.out.println("Philosopher " + i + " is eating.");

			DiningPhilosopherProblem.semaphoreNotify(forks[(i + 1) % 5]);
			DiningPhilosopherProblem.semaphoreNotify(forks[i % 5]);
		}
	}
}
