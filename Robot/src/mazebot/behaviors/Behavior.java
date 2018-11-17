
package mazebot.behaviors;

import mazebot.robot.Robot;


/*
 *  The super class for behaviors, this is the implementation
 *  of the strategy design pattern. All behaviors are 
 *  interchangeable. They are swapped at run time using
 *  robot.changeBehavior()
 */  
public abstract class Behavior {
	
	private boolean isInitialized = false;
	
	//Keep an instance variable for the robot
	public Robot robot;
	
	/**
	 *  Constructor for a behavior ensures the robot instance
	 *  is initialized correctly. Will halt the program not initialized.
	 */  
	public Behavior(Robot robot) {
		this.robot = robot;
		initializeBehavior();
	}
	
	/**
	 * This method executes the behavior. Call it in the main loop.
	*/
	public void run() {
		if(isInitialized == false){
			Robot.say("Behavior Not Initialized!");
			robot.changeBehavior(null);
			robot.halt();
		}
		execute();
	}
	
	private void initializeBehavior() {
		isInitialized = true;
		initialize();
	}
	
	/**
	 * Use this method to setup the inital state of the robot before
	 * executing the behavior.
	*/
	protected abstract void initialize();
	
	/**
	 * Use this method to describe what the robot does when this behavior
	 * is executed. It is called in the main loop.
	*/
	protected abstract void execute();
}
