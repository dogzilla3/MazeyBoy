package mazebot.behaviors;

import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.utility.Delay;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;
import mazebot.robot.Robot.Wav;

public class FinishBehavior extends Behavior {
	
	public FinishBehavior(Robot robot) {
		super(robot);
	}

	@Override
	protected void initialize() {
		robot.halt();
		robot.mapMaker.setSolvedPath();
		//Robot.playSound(Wav.COMPLETED);
	}

	@Override
	protected void execute() {
		Button.waitForAnyPress();
		//Robot.playSound(Wav.RESOLVING);
		robot.setOrientation(Orientation.NORTH);
		robot.changeBehavior(new TravelToNextSquare(robot));
	}
}