package mazebot.main;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import mazebot.behaviors.Behavior;
import mazebot.behaviors.TravelToNextSquare;
import mazebot.robot.Robot;

public class Main {
	
	//Initialize the robot
	public static Robot robot = new Robot();

	public static boolean running = true;
	
	public static void main(String[] args) {
		
		robot.halt();
		
		//Setup the screen to start on button press
		displayStartScreen();
		
		//Add escape button listener to exit the program
		addEscapeListener();

		Behavior travelToNextSquare = new TravelToNextSquare(robot);
		//Behavior testing1 = new TurnLeft(robot);
		
		robot.changeBehavior(travelToNextSquare);
		robot.setOrientation(Robot.Orientation.NORTH);
		boolean running = true;
		
		//Main loop of program
		while(running == true) {
			Robot.say(robot.getCurrentOrientation().name());
			robot.runBehavior();
		}
		displayEndScreen();
	}
		
	/*
	 *  Requires user input to start the program
	 *  This is to make sure the robot is ready
	 *  to begin
	 */  
	private static void displayStartScreen() {
		LCD.clear();	
		Button.LEDPattern(1);
		Sound.beepSequenceUp();
		LCD.drawString("Program Start!", 1, 1);
		LCD.drawString("Press any key to begin", 1, 2);	
		Button.waitForAnyPress();
		LCD.clearDisplay();
	}
	
	/*
	 *  Signals the end of the program
	 */  
	private static void displayEndScreen() {
		Button.LEDPattern(2);
		Sound.beepSequence();
		Button.waitForAnyPress();
	}
	
	private static void addEscapeListener() {
		Button.ESCAPE.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(Key k) {
				robot.halt();
				running = false;
			}

			@Override
			public void keyReleased(Key k) {
				robot.halt();
				running = false;
			}		
		});
	}
}
