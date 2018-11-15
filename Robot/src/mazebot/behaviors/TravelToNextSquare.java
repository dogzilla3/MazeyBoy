package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class TravelToNextSquare extends Behavior {


	public TravelToNextSquare(Robot robot) {
		super(robot);
		robot.forward();
	}

	@Override
	public void run() {
		if(robot.getColorId() == 0) {
			robot.halt();
		}
	}
	
	
	
}
