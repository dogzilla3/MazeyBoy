package mazebot.behaviors;

import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;

public class TestAlwaysTurnRight extends Behavior {

	public TestAlwaysTurnRight(Robot robot) {
		super(robot);
	}

	@Override
	public void run() {
		switch (robot.getCurrentOrientation()) {
			case NORTH: robot.changeBehavior(new TurnTo(robot, Orientation.EAST)); break;
			case SOUTH: robot.changeBehavior(new TurnTo(robot, Orientation.WEST)); break;
			case EAST: robot.changeBehavior(new TurnTo(robot, Orientation.SOUTH)); break;
			case WEST: robot.changeBehavior(new TurnTo(robot, Orientation.NORTH)); break;
		}
	}
}
