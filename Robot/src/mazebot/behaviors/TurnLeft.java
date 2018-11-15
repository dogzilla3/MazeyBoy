package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class TurnLeft extends Behavior{

	private int lastColor;
	
	
	public TurnLeft(Robot robot) {
		super(robot);
		lastColor = 0;
	}

	@Override
	public void run() {
		
		int color = robot.getColorId();

		if (color != 6) {
			robot.halt();
			robot.changeBehavior(new TravelToNextSquare(robot));
		}
		robot.say("" + color);
		lastColor = color;
	}

}
