package college.sem5.sooad;

import java.util.*;
import java.util.concurrent.*;;

public class OOPrinciples {
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	public static void main(String args[]) throws Exception {
		for(int i = 0; i < 3; i++) {
			executor.execute((new ShapeRunnable()));
		}
		executor.shutdown();
	}
}
abstract class Shape {
	protected double length, breadth, height;
	protected double area, volume;
	public abstract void calculateArea();
	public abstract void calculateVolume();
	public Shape(double length , double breadth, double height) {
		this.length = length;
		this.breadth = breadth;
		this.height = height;
	}
	public double getArea() {
		return area;
	}
	public double getVolume() {
		return volume;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Length = " + length + "\n");
		sb.append("Breadth = " + breadth + "\n");
		sb.append("Height = " + height + "\n");
		sb.append("Area : " + area + "\n");
		sb.append("Volume : " + volume + "\n\n");

		return sb.toString();
	}
}
class Rectangle extends Shape {
	public Rectangle( double length , double breadth) {
		super(length , breadth, 0 ); 
	}
	public void calculateArea() {
		area = length * breadth;
	}
	public void calculateVolume() {
		volume = 0;
	}  
}
class Cube extends Shape{
	public Cube( double length , double breadth, double height) {
		super(length , breadth, height ); 
	}
	public void calculateArea() {
		area = length * breadth;
	}
	public void calculateVolume() {
		volume = length * breadth * height;
	}  
}
class ShapeRunnable implements Runnable{
	private static final Scanner sc = new Scanner(System.in);
	public static int threadCount = 0;
	private static double length, breadth, height;
	
	public synchronized void run() {
		Shape shape = null;
		System.out.println("Enter length and breadth");
		length = sc.nextDouble();
		breadth = sc.nextDouble();
		if(ShapeRunnable.threadCount++%2 == 0) {
			shape = new Rectangle(length, breadth);
			shape.calculateArea();
			System.out.println(shape);
		}
		else {
			System.out.println("Enter the height as well : ");
			height = sc.nextDouble();
			shape = new Cube( length , breadth, height );
			shape.calculateArea();
			shape.calculateVolume();
			System.out.println(shape);
		} 
	}
}


