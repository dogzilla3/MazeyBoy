package mazebot.behaviors;

import mazebot.robot.Robot;

public class RunMapScan extends Behavior {

	public RunMapScan(Robot robot) {
		super(robot);
		Robot.say("Run map scan here" );
	}

	@Override
	public void run() {
		robot.map();
		//robot.getNextOrientation();
		
		robot.changeBehavior(new TurnLeft(robot));
	}

}
