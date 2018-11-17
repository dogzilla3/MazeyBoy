package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class RunMapScan extends Behavior {

	public RunMapScan(Robot robot) {
		super(robot);
		Robot.say("Run map scan here" );
	}

	@Override
	public void run() {
		//robot.map();
		//robot.getNextOrientation();
		Delay.msDelay(3000);
		//robot.changeBehavior(new TurnTo(robot));
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
