package org.usfirst.frc.team5821.robot.isaiah;

import java.util.ArrayList;

public class Event {
	private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
	public void trigger() {
		for (int i = 0; i < runnables.size(); i++) 
			 runnables.get(i).run();
	}
	
	public void connect(Runnable runnable) {
		runnables.add(runnable);
	}
	public void disconnect(Runnable runnable){
		runnables.remove(runnable);
	}
}
