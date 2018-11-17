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
	}

	@Override
	protected void initialize() {
		// Halt the robot if it was moving previously
		robot.halt();
		
		//Find out which direction we should turn using a little math: Thanks Geordie Dosher!
		int currentDirection = robot.getCurrentOrientation().ordinal();
		int desiredDirection = robot.getCurrentOrientation().ordinal();
		int turn = desiredDirection - currentDirection;
	}

	/**
	 * After this execution the robot should be pointing in the new direction
	 */
	@Override
	protected void execute() {
		if(turn == 3 || turn == -1) { 		// The desired direction is to the left
			while(color != Robot.WHITE) { 		// Keep turning left until we see white
				robot.pivotLeft();
				color = robot.getColorId();
			}									// After we see white
			while(color == Robot.WHITE) {		// Keep turning left until we don't see white
				robot.pivotLeft();
				color = robot.getColorId();
			}
			robot.halt();
			robot.setOrientation(newOrientation);
			robot.changeBehavior(new TravelToNextSquare(robot));	
		} 
		
		else if (turn == -1) {// The robot is currently on the desired direction
			robot.changeBehavior(new TravelToNextSquare(robot));
		} 
		
		else if (turn == 2) {				// The desired direction is behind the robot
			while(color != Robot.WHITE) {		// Keep turning left until we see white
				robot.pivotLeft();
				color = robot.getColorId();
			}									// After we see white
			while(color == Robot.WHITE) {		// Keep turning left until we don't see white
				robot.pivotLeft();
				color = robot.getColorId();
			}									// After we don't see white
			while(color != Robot.WHITE) {		// Keep turning left until we see white
				robot.pivotLeft();
				color = robot.getColorId();
			}									// After we see white
			while(color == Robot.WHITE) {		// Keep turning left until we don't see white
				robot.pivotLeft();
				color = robot.getColorId();
			}
			robot.halt();
			robot.setOrientation(newOrientation);
			robot.changeBehavior(new TravelToNextSquare(robot));	
		} 
		
		else { 								// The desired direction is to the right
			while(color != Robot.WHITE) {		// Keep turning right until we see white
				robot.pivotRight();
				color = robot.getColorId();
			}									// After we see white
			while(color == Robot.WHITE) {		// Keep turning right until we don't see white
				robot.pivotRight();
				color = robot.getColorId();
			}
			robot.halt();
			robot.setOrientation(newOrientation);
			robot.changeBehavior(new TravelToNextSquare(robot));	
		}
	}
}
