package mazebot.behaviors;

import lejos.hardware.Button;
import lejos.utility.Delay;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;
import mazebot.robot.Robot.Wav;

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
		//Robot.playSound(Wav.TRAVELING);
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
			if(Button.ENTER.isDown()) {
				robot.changeBehavior(new FinishBehavior(robot));
				return;
			}
		}
		robot.resetSpeed();
		Delay.msDelay(1000);				//Move forward then stop
		robot.halt();	
		
		//Map our current square
		Orientation nextO = robot.mapMaker.traverseMap();
//		if(robot.mapMaker.backtracking)
//			Robot.playSound(Wav.BACKTRACKING);
//		else
//			Robot.playSound(Wav.MAPPING);
		
		robot.changeBehavior(new TurnTo(robot, nextO));
	}
}
