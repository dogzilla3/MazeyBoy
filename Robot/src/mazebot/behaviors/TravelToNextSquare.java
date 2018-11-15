package mazebot.behaviors;

import lejos.utility.Delay;
import mazebot.robot.Robot;

public class TravelToNextSquare extends Behavior {

	private int lastColor;
	private boolean firstRed = true;

	public TravelToNextSquare(Robot robot) {
		super(robot);
		robot.forward();
		lastColor = 2;
	}

	@Override
	public void run() {
		
//		int color = robot.getColorId();
//		if(color == 0) {
//			Robot.say("foundRed");
//		}
//		if (color == 7) {
//			robot.setLeftMotorSpeed(150f);
//			robot.setRightMotorSpeed(125f);
//		} else if (color == 2) {
//			robot.setLeftMotorSpeed(125f);
//			robot.setRightMotorSpeed(200f);
//		} else if (color == 6) {
//			if (lastColor == 7) {
//				robot.setLeftMotorSpeed(125f);
//				robot.setRightMotorSpeed(150f);
//			} else if (lastColor == 2) {
//				robot.setLeftMotorSpeed(150f);
//				robot.setRightMotorSpeed(125f);
//			}
//		} else if (color == 0) {
//			Delay.msDelay(1000);
//			robot.halt();
//			robot.setLeftMotorSpeed(200);
//			robot.setLeftMotorSpeed(200);
//			Delay.msDelay(500);
//			robot.changeBehavior(new mapcurrentsquare);
//		} 
//		else {
//			robot.setLeftMotorSpeed(150f);
//			robot.setRightMotorSpeed(150f);
//		}
//
//		lastColor = color;
		
		
		
	}
	
	
	
}
