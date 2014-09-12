package college.sem5.os;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ReaderWriterProblem {
	public static AtomicInteger semaphoreFile;
	public static AtomicInteger semaphoreMutex;
	
	public static void main(String[] args) {
		final Writer w = new Writer();
		final Reader r = new Reader();
		
		Thread writer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				w.write();
			}
		});
		
		Thread reader = new Thread(new Runnable() {
			
			@Override
			public void run() {
				r.read();
			}
		});
		
		writer.start();
		reader.start();
		
	}

	public static void semaphoreInitialization() {
		semaphoreFile = new AtomicInteger(1);
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

class Writer {
	private Random random;
	public Writer() {
		random = new Random();
		ReaderWriterProblem.semaphoreInitialization();	
	}
	
	public void write() {
		for(int i = 0; i < 100; i++) {
			ReaderWriterProblem.semaphoreWait(ReaderWriterProblem.semaphoreFile);
			System.out.println("Writer: Writing " + random.nextInt(10000) + " bytes");
			ReaderWriterProblem.semaphoreNotify(ReaderWriterProblem.semaphoreFile);
		}
	}
}

class Reader {
	private Random random = new Random();
	private int readCount = 0;
	
	public void read() {
		for(int i = 0; i < 100; i++) {
			ReaderWriterProblem.semaphoreWait(ReaderWriterProblem.semaphoreMutex);
			readCount++;
			
			if(readCount == 1)
				ReaderWriterProblem.semaphoreWait(ReaderWriterProblem.semaphoreFile);
			ReaderWriterProblem.semaphoreNotify(ReaderWriterProblem.semaphoreMutex);
			
			ReaderWriterProblem.semaphoreWait(ReaderWriterProblem.semaphoreMutex);
			readCount--;
			
			if(readCount == 0)
				ReaderWriterProblem.semaphoreNotify(ReaderWriterProblem.semaphoreFile);
			ReaderWriterProblem.semaphoreNotify(ReaderWriterProblem.semaphoreMutex);
			
			System.out.println("Reader read " + random.nextInt(10000) + " bytes.");
		}
	}
}
