package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;
import mazebot.robot.Robot.Orientation;

public class TurnTo extends Behavior {

	private boolean turnInitialized;
	private Orientation newOrientation;
	private long turnTime = 550;
	private Orientation[] directions = {Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST};

	public TurnTo(Robot robot, Robot.Orientation orientation) {
		super(robot);
		turnInitialized = false;
		newOrientation = orientation;
	}

	@Override
	public void run() {
		
		initalizeTurn();
		
		Robot.say("" + robot.getColorId());
		if (robot.getColorId() != 6) {
			robot.setOrientation(newOrientation);
			robot.halt();
			robot.changeBehavior(new TravelToNextSquare(robot));
		}
	}
	
	private void initalizeTurn() {
		if (turnInitialized == false) {
			
			robot.pivotTowards(newOrientation);
			
			if(robot.getColorId() == 6) {
				turnInitialized = true;
			}
				
//			switch (robot.getCurrentOrientation()) {
//				case NORTH: {
//					switch (newOrientation) {
//						case SOUTH: robot.pivotLeft(); break;
//						case EAST: robot.pivotRight(); break;
//						case WEST: robot.pivotLeft(); break;
//						default: break;
//					}
//					break;
//				}
//				case SOUTH: {
//					switch (newOrientation) {
//						case NORTH: robot.pivotLeft(); break;
//						case WEST: robot.pivotRight(); break;
//						case EAST: robot.pivotLeft(); break;
//						default: break;
//					}
//					break;
//				}
//				case EAST: {
//					switch (newOrientation) {
//						case WEST: robot.pivotLeft(); break;
//						case SOUTH: robot.pivotRight(); break;
//						case NORTH: robot.pivotLeft(); break;
//						default: break;
//					}
//					break;
//				}
//				case WEST: {
//					switch (newOrientation) {
//						case EAST: robot.pivotLeft(); break;
//						case NORTH: robot.pivotRight(); break;
//						case SOUTH: robot.pivotLeft(); break;
//						default: break;
//					}
//					break;
//				}			
//			}

		}
	}
}
