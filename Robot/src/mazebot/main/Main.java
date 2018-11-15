package mazebot.main;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import mazebot.robot.Robot;

public class Main {
	
	public static void main(String[] args) {
		
		//Setup the screen to start on button press
		displayStartScreen();
		
		//Add escape button listener to exit the program
		addEscapeListener();

	
		//Initialize the robot
		Robot robot = new Robot();
		
		//Behavior testing = new TravelToNextSquare(robot);
		//Behavior testing1 = new TurnLeft(robot);
		
		//Initialize the behavior of the robot
		//robot.changeBehavior(testing);

		//Main loop of program
		while(!Button.ESCAPE.isDown()) {
			Robot.say("" + robot.getColorId());
			System.gc();
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
				System.exit(0);
			}

			@Override
			public void keyReleased(Key k) {}		
		});
	}
}
