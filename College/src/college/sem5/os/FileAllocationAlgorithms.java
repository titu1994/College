package college.sem5.os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import college.sem5.os.Allocation.FileAllocationTable;

public class FileAllocationAlgorithms {
	private static final Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.print("Enter no of files : ");
		int n = sc.nextInt();
		
		FileAllocationTable fat = new FileAllocationTable(n);
		System.out.println("Enter the file data in the format : ID, StartAddress and Length");
		for(int i= 0; i < n; i++) {
			System.out.println("File : " + (i+1));
			fat.addFile(sc.nextInt(), sc.nextInt(), sc.nextInt());
		}
		int ch;
		do {
			System.out.println("Enter choice : 1 - Continious, 2 - Linked or anyother to exit");
			ch = sc.nextInt();
			switch(ch) {
			case 1: {
				ContigiousAllocation alloc = new ContigiousAllocation(fat);
				alloc.allocate();
				System.out.println(alloc);
				break;
			}
			case 2: {
				LinkedAllocation alloc = new LinkedAllocation(fat);
				alloc.allocate();
				System.out.println(alloc);
				break;
			}
			default : {
				ch = -1;
			}
			}
		} while (ch != -1);


	}
}

abstract class Allocation {
	protected int memory[];
	protected int pointer;
	protected FileAllocationTable fat;

	public Allocation(FileAllocationTable fat) {
		this.fat = fat;
		memory = new int[50];

		for(int i = 0; i < memory.length; i++) {
			memory[i] = -1;
		}
	}

	public abstract void allocate();

	public static class FileAllocationTable {
		public int files[];
		public int startBlock[], length[];
		protected int index = -1;
		protected int size;

		public FileAllocationTable(int size) {
			files = new int[size];
			startBlock = new int[size];
			length = new int[size];
		}

		public void addFile(int name, int startBlock, int length) {
			if(index++ < files.length) {
				this.files[index] = name;
				this.startBlock[index] = startBlock;
				this.length[index] = length;
				System.out.println("File " + name + " added.");
				size++;
			}
			else {
				System.out.println("FAT is full");
			}
		}
	}

	@Override
	public String toString() { 
		return Arrays.toString(memory);
	}
}

class ContigiousAllocation extends Allocation {

	public ContigiousAllocation(FileAllocationTable fat) {
		super(fat);
	}

	@Override
	public void allocate() {
		int tempID, tempLength, tempStart;
		for(int i = 0; i < fat.size; i++) {
			System.out.println("Entered");
			tempID = fat.files[i];
			tempStart = fat.startBlock[i];
			tempLength = fat.length[i];

			boolean avail = true;
			for(int j = tempStart; j < (tempStart + tempLength); j++) {
				if(j < memory.length) {
					if(memory[j] != -1) {
						System.out.println("Failed at j = " + j);
						avail = false;
						break;
					}
				}
				else {
					avail = false;
					break;
				}
			}

			if(avail) {
				for(int j = tempStart; j < (tempStart + tempLength); j++) {
					memory[j] = tempID;
				}
			}
			else {
				System.out.println("Cannot allocate file " + tempID);
				System.out.println("Instead allocating it to location : " + pointer);

				for(int j = pointer; j < (pointer + tempLength); j++) {
					memory[j] = tempID;
				}
				fat.startBlock[i] = pointer;
				pointer += tempLength;
			}
		}
	}

}

class LinkedAllocation extends Allocation {

	public LinkedAllocation(FileAllocationTable fat) {
		super(fat);
	}

	@Override
	public void allocate() {
		int tempLength, tempStart, end;
		Random r = new Random();
		int pos = -1, previous = -1;

		for(int i = 0; i < fat.size; i++) {
			tempStart = fat.startBlock[i];
			tempLength = fat.length[i];
			end = tempLength + tempStart;
			memory[tempStart] = r.nextInt((end - tempStart) + 1) + tempStart;
			previous = memory[tempStart];
			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(previous);

			for(int j = 1; j < tempLength; j++) {
				pos = r.nextInt((end - tempStart) + 1) + tempStart;
				if(memory[pos] == -1 && !ids.contains(pos)) {
					ids.add(pos);
					memory[pos] = previous;
					previous = pos;
				}
				else {
					j--;
				}
			}
		}
	}

}