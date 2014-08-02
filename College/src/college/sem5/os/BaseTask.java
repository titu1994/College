package college.sem5.os;

public class BaseTask implements Comparable<BaseTask>{

	private static int _id = 1;

	private int burstTime;
	private String processorID;
	private int arrivalTime;
	private int priority;
	private int lastExecutedTime;

	public BaseTask(int burstTime) {
		this(burstTime, 0, 0);
	}

	public BaseTask(int burstTime, int arrivalTime) {
		this(burstTime, arrivalTime, 0);
	}

	public BaseTask(int burstTime, int arrivalTime, int priority) {
		this.burstTime = burstTime;
		this.priority = priority;
		this.arrivalTime = arrivalTime;

		this.processorID = "Processor " + _id++;
	}
	
	
	
	public int getLastExecutedTime() {
		return lastExecutedTime;
	}

	public void setLastExecutedTime(int lastExecutedTime) {
		this.lastExecutedTime = lastExecutedTime;
	}

	public int getBurstTime() {
		return burstTime;
	}
	
	public void decrementBurstTime() {
		if(this.burstTime - 1 >= 0) 
			this.burstTime--;
		else 
			this.burstTime = 0;
	}
	
	public void decrementBurstTime(int decrement) {
		if(this.burstTime - decrement >= 0) 
			this.burstTime -= decrement;
		else 
			this.burstTime = 0;
	}

	public String getProcessorID() {
		return processorID;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(processorID + " : ");
		sb.append("Burst time : " + burstTime + " , ");
		sb.append("Arrival time : " + arrivalTime + " , ");
		sb.append("Priority : " + priority + "\n");
		return sb.toString();
	}

	@Override
	public int compareTo(BaseTask o) {
		if((priority > 0 && o.priority > 0 )) {
			if(arrivalTime < o.arrivalTime) {
				System.out.println("Priority at < 0.at");
				return -( arrivalTime - o.arrivalTime );
			}
			else if(arrivalTime >= o.arrivalTime) {
				System.out.println("Priority at >= 0.at");
				return - (priority - o.priority);
			}
		}
		else if((burstTime > 0 && o.burstTime > 0 )) {
			if(arrivalTime < o.arrivalTime) {
				System.out.println("BurstTime at < 0.at");
				return - (arrivalTime - o.arrivalTime );
			}
			else if(arrivalTime >= o.arrivalTime) {
				System.out.println("BurstTime at >= 0.at");
				return -( burstTime - o.burstTime );
			}
		}
		else if(arrivalTime > 0 && o.arrivalTime > 0) {
			System.out.println("AT at < 0.at");
			return this.arrivalTime - o.arrivalTime;
		}
		else { 
			System.out.println("Else");

			return 0;
		}
		return 0;
	}

}
