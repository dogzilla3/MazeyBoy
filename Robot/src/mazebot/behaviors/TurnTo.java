package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;

public class TurnTo extends Behavior {

	private boolean turnInitialized;
	private Orientation newOrientation;

	public TurnTo(Robot robot, Robot.Orientation orientation) {
		super(robot);
		turnInitialized = false;
		newOrientation = orientation;
	}

	@Override
	public void run() {
		initalizeTurn();
		if (turnInitialized == true) {
			if (robot.isTurningAround(newOrientation)) {
				if (robot.getColorId() != Robot.WHITE) {
					turnInitialized = false;
				}
			} else {
				if (robot.getColorId() != Robot.WHITE) {
					robot.halt();
					robot.setOrientation(newOrientation);
					robot.changeBehavior(new TravelToNextSquare(robot));
				}
			}
		}
	}

	private void initalizeTurn() {
		if (turnInitialized == false) {
			robot.pivotTowards(newOrientation);
			if (robot.getColorId() == Robot.WHITE)
				turnInitialized = true;
		}
	}
}
