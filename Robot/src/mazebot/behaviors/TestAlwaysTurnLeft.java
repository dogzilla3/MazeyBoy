package mazebot.behaviors;

import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;

public class TestAlwaysTurnLeft extends Behavior {
	
	public TestAlwaysTurnLeft(Robot robot) {
		super(robot);
	}

	@Override
	protected void initialize() {
		//Halt the robot if we were moving previously
		robot.halt();
	}

	@Override
	protected void execute() {
		switch (robot.getCurrentOrientation()) {
			case NORTH: robot.changeBehavior(new TurnTo(robot, Orientation.WEST)); break;
			case SOUTH: robot.changeBehavior(new TurnTo(robot, Orientation.EAST)); break;
			case EAST: robot.changeBehavior(new TurnTo(robot, Orientation.NORTH)); break;
			case WEST: robot.changeBehavior(new TurnTo(robot, Orientation.SOUTH)); break;
		}
	}
}
