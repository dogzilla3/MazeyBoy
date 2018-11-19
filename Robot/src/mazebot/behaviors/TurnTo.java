package mazebot.behaviors;

import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;


public class TurnTo extends Behavior {

	private Orientation newOrientation;
	private int turn;
	private int color = -1;

	public TurnTo(Robot robot, Robot.Orientation orientation) {
		super(robot);
		newOrientation = orientation;

		//Find out which direction we should turn using a little math: Thanks Geordie Dosher!
		int currentDirection = robot.getCurrentOrientation().ordinal();
		int desiredDirection = newOrientation.ordinal();
		turn = desiredDirection - currentDirection;
	}

	@Override
	protected void initialize() {
		// Halt the robot if it was moving previously
		robot.halt();
	}

	/**
	 * After this execution the robot should be pointing in the new direction
	 */
	@Override
	protected void execute() {
		if(turn == 3 || turn == -1) { 		// The desired direction is to the left
			turnLeft();
		} 
		
		else if (turn == 0) {// The robot is currently on the desired direction
			
		} 
		
		else if (Math.abs(turn) == 2) {				// The desired direction is behind the robot
			turnRight();
			turnRight();
		} 
		
		else { 								
			turnRight();
		}
		robot.halt();
		robot.setOrientation(newOrientation);
		robot.changeBehavior(new TravelToNextSquare(robot));	
	}
	
	private void turnLeft() {
		while(color != Robot.WHITE) {
			robot.pivotLeft();
			color = robot.getColorId();
		}
		while (color != Robot.BLACK) {
			robot.pivotLeft();
			color = robot.getColorId();
		}
		while(color != Robot.WHITE) {
			robot.pivotLeft();
			color = robot.getColorId();
		}
	}
	
	private void turnRight(){				// The desired direction is to the right
		while(color != Robot.BLACK) {		// Keep turning right until we see white
			robot.pivotRight();
			color = robot.getColorId();
		}									// After we see white
		while(color != Robot.WHITE) {		// Keep turning right until we don't see white
			robot.pivotRight();
			color = robot.getColorId();
		}
		while(color != Robot.BLACK) {		// Keep turning right until we don't see white
			robot.pivotRight();
			color = robot.getColorId();
		}
	}
}
