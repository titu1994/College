package college.sem7.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HillClimbing {
	
	static int leftGoal[];
	static int rightGoal[];
	static int leftGoalH[], rightGoalH[];
	

	public static void main(String[] args) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Goal seperated with space and commas");
		String ip = bb.readLine();
		
		String final1[] = ip.split(",")[0].split(" ");
		String final2[] = ip.split(",")[1].split(" ");
		
		leftGoal = new int[final1.length];
		rightGoal = new int[final2.length];
		leftGoalH = new int[leftGoal.length];
		rightGoalH = new int[rightGoal.length];
		
		
		for(int i = 0; i < leftGoal.length; i++) {
			leftGoal[i] = Integer.parseInt(final1[i].trim());
			if(i == 0)
				leftGoalH[0] = 1;
			else
				leftGoalH[i] = leftGoalH[i-1] + 1;
		}
		
		for(int i = 0; i < rightGoal.length; i++) {
			rightGoal[i] = Integer.parseInt(final2[i].trim());
			if(i == 0)
				rightGoalH[0] = 1;
			else
				rightGoalH[i] = rightGoalH[i-1] + 1;
		}
		
		
		System.out.println("Enter start");
		ip = bb.readLine();
		
		final1 = ip.split(",")[0].split(" ");
		final2 = ip.split(",")[1].split(" ");
		
		int leftStart[] = new int[final1.length];
		int rightStart[] = new int[final2.length];
		
		for(int i = 0; i < leftGoal.length; i++)
			leftStart[i] = Integer.parseInt(final1[i].trim());
		
		for(int i = 0; i < rightGoal.length; i++)
			rightStart[i] = Integer.parseInt(final2[i].trim());
		
		
		

	}
	
	private static int[] computeHeuristics(int[] data, boolean left) {
		int[] x = new int[data.length];
		if(left) {
			
		}
		
		return x;
	}

}
