package mazebot.behaviors;

import mazebot.robot.Robot;

public class FinishBehavior extends Behavior {

	public FinishBehavior(Robot robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		robot.halt();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		robot.halt();
	}

}
