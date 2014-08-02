package college.sem5.os;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BaseScheduler {
	private int nRequiredCycles;
	private boolean isFCFS = false;
	private int quantum;
	private Queue<BaseTask> schedule;

	public BaseScheduler() {
		this(4, false);
	}
	
	public BaseScheduler(boolean isFCFS) {
		this(4, isFCFS);
	}

	public BaseScheduler(int quantum, boolean isFCFS) {
		this.isFCFS = isFCFS;
		this.quantum = quantum;

		if(!isFCFS)
			schedule = new PriorityQueue<BaseTask>();
		else
			schedule = new LinkedBlockingQueue<BaseTask>();
	}

	public void addTask(BaseTask task) {
		if(task.getBurstTime() > 0) {
			schedule.add(task);
			nRequiredCycles += task.getBurstTime();
		}
	}

	public ScheduleResult executeTasks(boolean isPreemptive) {
		int executionTime = 0;
		double atat = 0, awt = 0;
		int count = schedule.size();
		BaseTask task;
		if(!isPreemptive) {

			for(int i = 0; i < count; i++) {
				task = schedule.remove();

				awt += executionTime - task.getArrivalTime();
				atat += executionTime + task.getBurstTime() - task.getArrivalTime();
				executionTime += task.getBurstTime();
			}


		}
		else {
			if(isFCFS) {
				//FCFS with preemption is Round Robin Method. Thus, special care is taken
				int remainder = 0;
				for(int i = 0; i < nRequiredCycles; ) {
					task = schedule.remove();
					awt += i - task.getArrivalTime() - task.getLastExecutedTime();


					if(task.getBurstTime() >= quantum) {
						task.decrementBurstTime(quantum);
						task.setLastExecutedTime(i);
						schedule.add(task);
						i += quantum;
					}
					else {
						remainder = quantum - task.getBurstTime();
						atat += i - task.getArrivalTime();
						task.decrementBurstTime(remainder);
						i += remainder;
					}
				}
			}
			else {
				for(int i = 0; i < nRequiredCycles; i++) {
					task = schedule.remove();
					awt += i - task.getArrivalTime() - task.getLastExecutedTime();
					
					if(task.getBurstTime() > 0) {
						task.setLastExecutedTime(i);
						task.decrementBurstTime();
						schedule.add(task);
						System.out.println(task);
					}
					else {
						task.decrementBurstTime();
						atat += i - task.getArrivalTime();
						
					}
				}
			}
		}
		awt /= count;
		atat /= count;
		return new ScheduleResult(executionTime, atat, awt);
	}

	public static class ScheduleResult {
		private final int executionTime;
		private final double atat;
		private final double awt;

		public ScheduleResult(int executionTime, double atat, double awt) {
			this.executionTime = executionTime;
			this.awt = awt;
			this.atat = atat;
		}

		public int getExecutionTime() {
			return executionTime;
		}
		public double getAtat() {
			return atat;
		}
		public double getAwt() {
			return awt;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Execution Time : " + executionTime + " , ");
			sb.append("Average Waiting Time : " + awt + " , ");
			sb.append("Average Turn Around Time : " + atat + "\n");
			return sb.toString();
		}
	}

}
