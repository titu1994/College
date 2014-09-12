package college.sem5.os;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerProblem {
	public static AtomicInteger semaphoreFull;
	public static AtomicInteger semaphoreEmpty; 
	public static AtomicInteger semaphoreMutex;
	
	public static void main(String args[]) throws IOException, NumberFormatException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter buffer size : ");
		final int buffSize = sc.nextInt();
		
		Thread producer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Producer pr = new Producer(buffSize);
				pr.produce();
			}
		});
		
		Thread consumer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Consumer cn = new Consumer();
				cn.consume();
			}
		});
		
		producer.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//Used to allow the producer to atleast initialize the semaphores in its constructor
		}
		
		consumer.start();
	}
	
	public static void semaphoreInitialization() {
		semaphoreEmpty = new AtomicInteger(Producer.bufferSize);
		semaphoreFull = new AtomicInteger(0);
		semaphoreMutex = new AtomicInteger(1);
	}
	
	public static void semaphoreWait(AtomicInteger semaphore) {
		while(semaphore.intValue() <= 0);
		
		semaphore.set(semaphore.get() - 1);
	}
	
	public static void semaphoreNotify(AtomicInteger semaphore) {
		semaphore.set(semaphore.get() + 1);
	}
	
}

class Producer {
	public static int bufferSize = 5;
	public static ArrayBlockingQueue<Integer> queue;
	
	public Producer(int bufferSize) {
		Producer.bufferSize = bufferSize;
		
		if(queue == null)
			queue = new ArrayBlockingQueue<>(bufferSize);
		
		ProducerConsumerProblem.semaphoreInitialization();
	}
	
	public void produce() {
		int calculate = 0;
		for(int i = 0; i < 100; i++) { // Value of i is the produced item.
			ProducerConsumerProblem.semaphoreWait(ProducerConsumerProblem.semaphoreEmpty);
			ProducerConsumerProblem.semaphoreWait(ProducerConsumerProblem.semaphoreMutex);
			
			//Entering item into queue
			try {
				calculate = (i % bufferSize) + 1;
				queue.put(calculate);
				System.out.println("Producer: Produced an item : " + calculate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ProducerConsumerProblem.semaphoreNotify(ProducerConsumerProblem.semaphoreMutex);
			ProducerConsumerProblem.semaphoreNotify(ProducerConsumerProblem.semaphoreFull);
		}
	}
}

class Consumer {
	
	public void consume() {
		for(int i = 0; i < 100; i++) {
			ProducerConsumerProblem.semaphoreWait(ProducerConsumerProblem.semaphoreFull);
			ProducerConsumerProblem.semaphoreWait(ProducerConsumerProblem.semaphoreMutex);
			
			int item = Producer.queue.remove();
			System.out.println("Consumer: Consumed an item : "+item);
		
			ProducerConsumerProblem.semaphoreNotify(ProducerConsumerProblem.semaphoreMutex);
			ProducerConsumerProblem.semaphoreNotify(ProducerConsumerProblem.semaphoreEmpty);
		}
	}
}
