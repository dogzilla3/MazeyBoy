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

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

}
