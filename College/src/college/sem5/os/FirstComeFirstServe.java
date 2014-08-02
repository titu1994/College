package college.sem5.os;

import college.sem5.os.BaseScheduler.ScheduleResult;

public class FirstComeFirstServe {
	
	public static void main(String[] args) {
		BaseTask t1 = new BaseTask(8, 0);
		BaseTask t2 = new BaseTask(9, 1);
		BaseTask t3 = new BaseTask(3, 2);
		
		BaseScheduler scheduler = new BaseScheduler();
		scheduler.addTask(t1);
		scheduler.addTask(t2);
		scheduler.addTask(t3);
		
		ScheduleResult result = scheduler.executeTasks(true);
		System.out.println(result);

	}

}
