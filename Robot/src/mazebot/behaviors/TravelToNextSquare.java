package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class TravelToNextSquare extends Behavior {

	private int color = -1;

	public TravelToNextSquare(Robot robot) {
		super(robot);
	}

	@Override
	protected void initialize() {
		//Clear the last color before line following
		robot.setLastColor(-1);
		robot.forward();
	}

	/**
	 * This method instructs the robot to follow the line
	 * until it sees red
	 */
	@Override
	protected void execute() {
		while(color != Robot.RED) {			// If we don't see red follow the line
			color = robot.getColorId();
			robot.lineFollow();
		}
		robot.resetSpeed();
		
		/*									// Map our current square
		 * ******************
		 * ADD MAPPING HERE *
		 * ******************
		 * robot.map();
		 * robot.getNextOrientation();
		 */
		
						
		Delay.msDelay(1000);				//Move forward then stop
		robot.halt();
		
		//This method will change to something similar
		//robot.changeBehavior(new TurnTo(robot.getNextOrientation());
		robot.changeBehavior(new TestAlwaysTurnLeft(robot)); // <--for testing purposes
	}
}
