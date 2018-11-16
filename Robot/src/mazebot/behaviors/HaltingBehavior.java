package mazebot.behaviors;

import mazebot.robot.Robot;

public class HaltingBehavior extends Behavior {

	public HaltingBehavior(Robot robot) {
		super(robot);
	}

	@Override
	public void run() {
		robot.halt();
	}

}
