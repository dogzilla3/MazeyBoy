package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class TravelToNextSquare extends Behavior {


	public TravelToNextSquare(Robot robot) {
		super(robot);
	}

	@Override
	public void run() {
		robot.forward();
		if(robot.getColorId() == Robot.RED) {
			robot.halt();
			
			/*
			 * ******************
			 * ADD MAPPING HERE *
			 * ******************
			 * robot.map();
			 * robot.getNextOrientation();
			 */
			
			robot.forward();
			Delay.msDelay(1000);
			robot.halt();
			robot.changeBehavior(new TestAlwaysTurnRight(robot));
		}
	}
}
