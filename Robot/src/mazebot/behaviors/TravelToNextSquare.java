package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;

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
		while(color != Robot.RED /* && color != Robot.BLUE*/) {			// If we don't see red follow the line
			color = robot.getColorId();
			robot.lineFollow();
		}
		robot.resetSpeed();
		Delay.msDelay(1000);				//Move forward then stop
		robot.halt();	
		
		//Map our current square
		if(color == Robot.RED) {
			Orientation nextO = robot.mapMaker.traverseMap();
			Robot.say(nextO.name());
			Robot.say(robot.mapMaker.mapToASCIIString());
			robot.changeBehavior(new TurnTo(robot, nextO));
		}
		else
		{
			robot.changeBehavior(new FinishBehavior(robot));
		}
	}
}
